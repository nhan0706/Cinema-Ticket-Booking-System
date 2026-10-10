# TÀI LIỆU ÔN TẬP & BẢO VỆ TIẾN ĐỘ TUẦN 1
### HỆ THỐNG ĐẶT VÉ XEM PHIM TRỰC TUYẾN (CINEMA BOOKING SYSTEM)

* **Họ và tên sinh viên:** Nguyễn Thành Nhân
* **Mã số sinh viên (MSSV):** 2124110299
* **Công nghệ sử dụng:** Java 21, Spring Boot 4.x, Spring Data JPA, Hibernate ORM, MySQL 8.x, Lombok, Jakarta Validation, Swagger UI
* **Mục đích tài liệu:** Tổng hợp toàn bộ kiến thức lý thuyết, phân tích mã nguồn và ngân hàng câu hỏi vấn đáp phục vụ buổi báo cáo tiến độ Tuần 1 với Giảng viên hướng dẫn.

---

## PHẦN 1: MÔ HÌNH KIẾN TRÚC MVC & CÁC TẦNG TRONG DỰ ÁN

### 1.1. Luồng xử lý một Request trong kiến trúc Spring Boot MVC
Dự án tuân thủ nghiêm ngặt mô hình phân tầng chuẩn công nghiệp (Layered Architecture):

```
Client (Postman / Browser)
         │  (HTTP Request JSON)
         ▼
[Controller]  ➔ Nhận Request, Validate dữ liệu (@Valid), gọi Service
         │
         ▼
 [Service]   ➔ Thực thi logic nghiệp vụ (Kiểm tra trùng lặp, tính toán, transactional)
         │
         ▼
[Repository] ➔ Giao tiếp với MySQL Database thông qua Spring Data JPA
         │
         ▼
  [Entity]   ➔ Ánh xạ trực tiếp với bảng trong cơ sở dữ liệu
```

### 1.2. Vai trò của từng Package trong mã nguồn
| Package | Ví dụ trong dự án | Vai trò & Ý nghĩa |
|---|---|---|
| `controller` | `MovieController`, `CinemaController` | Tiếp nhận HTTP Request từ client, điều hướng và trả về `ResponseEntity<ApiResponse<T>>`. Không chứa logic nghiệp vụ. |
| `service` | `MovieService`, `CinemaService` | Khai báo các **Interface** định nghĩa các chức năng nghiệp vụ của hệ thống (hợp đồng nghiệp vụ). |
| `service.impl` | `MovieServiceImpl`, `CinemaServiceImpl` | Hiện thực hóa (implements) các Interface. Đây là nơi chứa 100% logic xử lý, kiểm tra hợp lệ, quản lý transaction với `@Transactional`. |
| `repository` | `MovieRepository`, `CinemaRepository` | Kế thừa `JpaRepository<T, ID>`, cung cấp các hàm CRUD có sẵn (`save`, `findById`, `findAll`, `delete`) và custom query bằng JPQL. |
| `entity` | `Movie`, `Cinema`, `Seat`, `User` | Các lớp đối tượng ánh xạ trực tiếp 1-1 với các bảng trong MySQL thông qua Hibernate ORM. |
| `dto` | `MovieRequestDTO`, `MovieResponseDTO` | Data Transfer Object: Đóng gói dữ liệu gửi lên từ client và dữ liệu trả về. Giúp ẩn các thông tin nhạy cảm của Entity và hỗ trợ Validation. |
| `mapper` | `MovieMapper`, `CinemaMapper` | Chuyển đổi qua lại giữa Entity và DTO để dữ liệu được đóng gói an toàn trước khi ra ngoài controller. |
| `exception` | `GlobalExceptionHandler`, `ResourceNotFoundException` | Bắt mọi lỗi ngoại lệ trong hệ thống và chuẩn hóa về mã lỗi HTTP chuẩn (400, 404, 409, 500). |
| `seeder` | `DataSeeder` | Thực thi khi ứng dụng khởi động (implements `CommandLineRunner`), tự động chèn dữ liệu mẫu nếu DB trống. |

---

## PHẦN 2: CƠ SỞ DỮ LIỆU & SPRING DATA JPA / HIBERNATE

### 2.1. Bản chất của JPA và Hibernate
* **JPA (Java Persistence API / Jakarta Persistence):** Là một *chuẩn đặc tả (Specification/Interface)* quy định cách thức ánh xạ đối tượng Java vào cơ sở dữ liệu quan hệ.
* **Hibernate:** Là *bộ cài đặt thực thi cụ thể (Implementation)* của chuẩn JPA. Spring Boot sử dụng Hibernate ngầm định bên dưới.

### 2.2. Các Annotation JPA quan trọng đã sử dụng
* `@Entity` & `@Table(name = "...")`: Khai báo class là một thực thể quản lý bởi JPA và map với tên bảng cụ thể trong CSDL.
* `@Id` & `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Khai báo khóa chính và dùng chiến lược Auto-Increment của MySQL.
* `@ManyToOne(fetch = FetchType.LAZY)` & `@JoinColumn(name = "...")`: Ánh xạ quan hệ n-1 (ví dụ nhiều Phòng thuộc 1 Rạp). **FetchType.LAZY** giúp chỉ truy vấn khi cần, tiết kiệm bộ nhớ và tránh truy vấn dư thừa.
* `@OneToMany(mappedBy = "...", cascade = CascadeType.ALL, orphanRemoval = true)`: Ánh xạ quan hệ 1-n (1 Rạp có nhiều Phòng).
* `@CreationTimestamp` & `@UpdateTimestamp`: Tự động ghi lại thời gian tạo bản ghi và thời gian cập nhật bản ghi gần nhất.

### 2.3. So sánh JPA vs JDBC (Khi nào nên kết hợp?)
| Tiêu chí | Spring Data JPA (Hibernate) | Spring JDBC (JdbcTemplate) |
|---|---|---|
| **Cách tiếp cận** | Hướng đối tượng (OOP), tự động sinh SQL | Thao tác trực tiếp với SQL thuần (Native SQL) |
| **Thế mạnh** | CRUD cơ bản cực nhanh, quản lý quan hệ bảng tự động, tự động sinh DDL. | Tốc độ thực thi cực đại, kiểm soát 100% câu SQL, tối ưu chỉ mục (Index). |
| **Điểm yếu** | Thao tác hàng loạt (Batch Insert) chậm, báo cáo GROUP BY phức tạp cồng kềnh. | Phải viết SQL thủ công bằng tay, tự map dữ liệu. |
| **Ứng dụng trong dự án** | **Tuần 1:** Dùng cho 100% CRUD Master data (Phim, Rạp, Phòng chiếu, Thể loại). | **Tuần 2 & Tuần 4:** Dùng JDBC cho Batch Insert sinh 150-300 ghế một lượt và truy vấn báo cáo doanh thu. |

---

## PHẦN 3: XỬ LÝ NGOẠI LỆ & CHUẨN HÓA DỮ LIỆU ĐẦU RA

### 3.1. Cấu trúc chuẩn của `ApiResponse<T>`
Mọi API trong hệ thống đều trả về cấu trúc thống nhất:
```json
{
  "status": 200,
  "message": "Thông báo kết quả",
  "data": { ... },
  "timestamp": "2026-10-09T15:50:01"
}
```

### 3.2. Cơ chế bắt lỗi tập trung (Global Exception Handling)
Sử dụng `@RestControllerAdvice` kết hợp `@ExceptionHandler` để chặn tất cả Exception từ Service đẩy ra:
* `ResourceNotFoundException` ➔ Trả về mã **404 NOT_FOUND** khi không tìm thấy ID (Ví dụ: Phim không tồn tại).
* `DuplicateResourceException` ➔ Trả về mã **409 CONFLICT** khi dữ liệu bị trùng khóa duy nhất (Ví dụ: Tên rạp hoặc Tên thể loại đã tồn tại).
* `MethodArgumentNotValidException` ➔ Trả về mã **400 BAD_REQUEST** khi dữ liệu vi phạm Bean Validation (Ví dụ: Để trống tên phim, thời lượng âm).
* `Exception.class` ➔ Trả về mã **500 INTERNAL_SERVER_ERROR** khi xảy ra sự cố bất ngờ của hệ thống.

---

## PHẦN 4: NGÂN HÀNG CÂU HỎI VẤN ĐÁP THỰC TẾ & CÂU TRẢ LỜI MẪU

### ❓ Câu 1: Em hãy giải thích luồng hoạt động từ lúc Client gửi request lấy danh sách phim đang chiếu `GET /api/v1/movies/now-showing`?
**Trả lời:**
1. Client gửi HTTP GET đến `/api/v1/movies/now-showing`.
2. `DispatcherServlet` của Spring Boot tiếp nhận và phân phối đến `MovieController.getNowShowingMovies()`.
3. Controller gọi tiếp sang `MovieService.getNowShowingMovies()` (được thực thi bởi `MovieServiceImpl`).
4. Trong tầng Service, hàm gọi `movieRepository.findByStatus("now_showing")`.
5. Hibernate sinh câu lệnh SQL: `SELECT ... FROM movies WHERE status = 'now_showing'` và lấy dữ liệu từ MySQL.
6. Service dùng `MovieMapper.toResponseDTO()` để chuyển đổi danh sách `Movie` Entity thành `MovieResponseDTO` (kèm danh sách thể loại).
7. Controller bọc dữ liệu vào `ApiResponse.success(data, message)` và trả về Client dưới dạng JSON với HTTP Status 200 OK.

---

### ❓ Câu 2: Tại sao em lại tách riêng Interface Service và Class ServiceImpl trong package impl?
**Trả lời:**
* **Tuân thủ nguyên lý thiết kế SOLID:** Cụ thể là *Dependency Inversion Principle (DIP)* - các module cấp cao (Controller) không nên phụ thuộc trực tiếp vào module cấp thấp mà nên phụ thuộc vào abstraction (Interface).
* **Loose Coupling (Giảm sự phụ thuộc chặt chẽ):** Giúp Controller chỉ quan tâm Service làm được gì (hợp đồng nghiệp vụ), không quan tâm chi tiết bên trong được viết như thế nào.
* **Dễ dàng Unit Test:** Cho phép giả lập (Mocking) Service một cách dễ dàng khi viết kiểm thử tự động với Mockito.
* **Hỗ trợ đa cài đặt:** Sau này nếu cần đổi cách xử lý (ví dụ đổi từ JPA sang JDBC hoặc Microservices), chỉ cần tạo class Impl mới mà không làm thay đổi Controller.

---

### ❓ Câu 3: Tại sao em dùng DTO mà không trả trực tiếp JPA Entity ra ngoài Controller?
**Trả lời:** Có 4 lý do quan trọng:
1. **Bảo mật (Security):** Tránh để lộ các trường nhạy cảm của CSDL ra bên ngoài (ví dụ như trường `password_hash` trong bảng `User`).
2. **Tránh lỗi vòng lặp tuần hoàn (Infinite Recursion):** Trong JPA có quan hệ 2 chiều (ví dụ: Cinema chứa danh sách Rooms, Room lại chứa Cinema). Nếu serialize thẳng Entity thành JSON sẽ bị vòng lặp vô tận gây lỗi `StackOverflowError`.
3. **Tối ưu dữ liệu mạng:** Chỉ truyền đúng những trường Client cần, không truyền thừa dữ liệu.
4. **Hỗ trợ Validation:** Tách biệt giữa dữ liệu nhập vào (Request DTO) cần kiểm tra tính hợp lệ và dữ liệu trả về (Response DTO).

---

### ❓ Câu 4: Lỗi N+1 Query trong JPA là gì và em đã giải quyết như thế nào trong dự án?
**Trả lời:**
* **Hiện tượng:** Khi truy vấn danh sách N bộ phim, nếu cấu hình quan hệ là `EAGER` hoặc duyệt qua từng phim để lấy thể loại, Hibernate sẽ thực thi 1 câu lệnh lấy N phim và thêm N câu lệnh lấy thể loại cho từng phim ➔ Tổng cộng là **N + 1 câu lệnh SQL**, gây sụt giảm nghiêm trọng hiệu năng cơ sở dữ liệu.
* **Cách giải quyết:** Em đã cấu hình toàn bộ quan hệ `@ManyToOne` và `@OneToMany` sang chế độ `FetchType.LAZY`. Ở những nơi cần lấy thông tin kèm theo, em sử dụng DTO Mapper có kiểm soát hoặc viết custom query JOIN FETCH để lấy dữ liệu trong 1 lần truy vấn duy nhất.

---

### ❓ Câu 5: Dữ liệu mẫu (Data Seeder) được khởi tạo như thế nào trong dự án?
**Trả lời:**
Em xây dựng class `DataSeeder` thực thi interface `CommandLineRunner`. Khi ứng dụng Spring Boot khởi động hoàn tất, phương thức `run()` sẽ tự động được kích hoạt. Lớp này kiểm tra nếu trong bảng `genres` chưa có dữ liệu (`genreRepository.count() == 0`) thì mới chèn 8 thể loại, 4 bộ phim, 3 rạp chiếu, phòng chiếu, banner và ngày lễ. Nhờ vậy dữ liệu không bao giờ bị chèn lặp lại khi khởi động lại ứng dụng.

---

### ❓ Câu 6: Em đã test các API này như thế nào?
**Trả lời:**
Em đã kiểm thử toàn diện bằng 2 công cụ:
1. **Swagger UI (OpenAPI 3.0):** Truy cập tại `http://localhost:8080/swagger-ui/index.html` để xem trực quan tài liệu mô tả chi tiết của từng API và thực hiện tính năng "Try it out".
2. **Postman:** Gửi các HTTP Request (GET, POST, PUT, DELETE) với các trường hợp dữ liệu hợp lệ (trả về 200, 201) và trường hợp dữ liệu vi phạm validation (trả về 400 Bad Request) để xác nhận hệ thống bắt lỗi chuẩn xác.

---

## PHẦN 5: BẢNG TỔNG KẾT MÃ LỖI HTTP STATUS CODE
| Mã HTTP | Ý nghĩa | Tình huống sử dụng trong hệ thống |
|:---:|---|---|
| `200 OK` | Thành công | Lấy dữ liệu (GET) hoặc cập nhật thành công (PUT). |
| `201 Created` | Đã tạo mới thành công | Thêm mới bản ghi vào CSDL (POST tạo phim, rạp, thể loại). |
| `400 Bad Request` | Dữ liệu gửi lên sai | Dữ liệu vi phạm Bean Validation (bị rỗng, sai định dạng số). |
| `404 Not Found` | Không tìm thấy tài nguyên | Truy vấn theo ID không tồn tại trong CSDL. |
| `409 Conflict` | Xung đột dữ liệu | Trùng lặp tên thể loại, tên rạp hoặc ngày lễ đã có trong CSDL. |
| `500 Internal Server Error` | Lỗi hệ thống máy chủ | Lỗi cú pháp SQL, mất kết nối database hoặc lỗi runtime chưa dự liệu. |
