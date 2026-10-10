# BÁO CÁO TIẾN ĐỘ DỰ ÁN - TUẦN 1

* **Đề tài:** XÂY DỰNG HỆ THỐNG ĐẶT VÉ XEM PHIM TRỰC TUYẾN (CINEMA BOOKING SYSTEM)
* **Sinh viên thực hiện:** Nguyễn Thành Nhân
* **Mã số sinh viên (MSSV):** 2124110299
* **Công nghệ:** Spring Boot 4.x, Java 21, MySQL 8.x, Spring Data JPA, Hibernate ORM, Lombok, Jakarta Validation, SpringDoc OpenAPI (Swagger UI).
* **Thời gian thực hiện:** Tuần 1 / 4 tuần

---

## 1. MỤC TIÊU TUẦN 1 ĐÃ ĐỀ RA

1. **Khởi tạo kiến trúc dự án:** Thiết lập cấu hình Spring Boot, kết nối cơ sở dữ liệu MySQL (`nguyenthanhnhan_2124110299`), tích hợp Swagger UI và thư viện Bean Validation.
2. **Hiện thực hóa sơ đồ ERD:** Ánh xạ 100% sơ đồ thực thể liên kết (ERD gồm ~24 bảng) thành các **JPA Entities** với đầy đủ các mối quan hệ `@OneToMany`, `@ManyToOne`, `@JoinColumn`, cascade và index.
3. **Chuẩn hóa kiến trúc phân tầng (Layered MVC):** Tách bạch rõ ràng giữa các tầng `Controller` ➔ `Service` (`interface` + `impl`) ➔ `Repository` ➔ `Entity`, sử dụng mô hình `DTO` và `Mapper`.
4. **Xây dựng cơ chế xử lý ngoại lệ tập trung (Global Exception Handling):** Bắt và chuẩn hóa toàn bộ lỗi hệ thống, lỗi dữ liệu đầu vào và lỗi nghiệp vụ thông qua định dạng `ApiResponse<T>`.
5. **Triển khai các API Master Data (Dữ liệu nền tảng):**
   - Quản lý Thể loại phim (`genres`).
   - Quản lý Phim (`movies`) và Diễn viên/Đoàn phim (`movie_casts`).
   - Quản lý Cụm rạp (`cinemas`) và Phòng chiếu (`cinema_rooms`).
   - Quản lý Banner quảng cáo (`banners`).
   - Quản lý Ngày lễ ảnh hưởng giá vé (`holidays`).
6. **Xây dựng Data Seeder:** Tự động nạp dữ liệu mẫu ban đầu để phục vụ việc kiểm thử và báo cáo trực quan.

---

## 2. KẾT QUẢ ĐẠT ĐƯỢC (DELIVERABLES)

### 2.1. Thiết kế Cơ sở dữ liệu & JPA Entities
Đã xây dựng hoàn chỉnh **25 JPA Entities** ánh xạ chính xác từ sơ đồ ERD:

| STT | Tên Entity | Tên bảng CSDL | Ý nghĩa nghiệp vụ |
|:---:|------------|---------------|-------------------|
| 1 | `Cinema` | `cinemas` | Thông tin cụm rạp chiếu phim |
| 2 | `CinemaRoom` | `cinema_rooms` | Phòng chiếu thuộc rạp (2D, 3D, IMAX) |
| 3 | `Seat` | `seats` | Ghế ngồi trong từng phòng chiếu |
| 4 | `TicketPriceRule`| `ticket_price_rules` | Quy tắc giá vé theo suất/phòng/loại ghế/ngày lễ |
| 5 | `Movie` | `movies` | Thông tin phim (thời lượng, poster, đạo diễn,...) |
| 6 | `Genre` | `genres` | Thể loại phim (Hành động, Kinh dị,...) |
| 7 | `MovieGenre` | `movie_genres` | Bảng liên kết Phim - Thể loại (quan hệ N-N) |
| 8 | `MovieCast` | `movie_casts` | Diễn viên, đạo diễn, nhà sản xuất của phim |
| 9 | `User` | `users` | Tài khoản khách hàng và quản trị viên |
| 10 | `UserDevice` | `user_devices` | Thiết bị đăng nhập của người dùng |
| 11 | `Favorite` | `favorites` | Danh sách phim yêu thích của người dùng |
| 12 | `Review` | `reviews` | Đánh giá và bình luận phim |
| 13 | `Notification` | `notifications` | Thông báo gửi tới khách hàng |
| 14 | `LoyaltyTransaction`| `loyalty_transactions`| Lịch sử tích lũy/sử dụng điểm thưởng |
| 15 | `Showtime` | `showtimes` | Lịch chiếu phim theo phòng và khung giờ |
| 16 | `Booking` | `bookings` | Đơn đặt vé của khách hàng |
| 17 | `Ticket` | `tickets` | Chi tiết vé xem phim (gắn với từng ghế cụ thể) |
| 18 | `BookingStatusHistory`| `booking_status_history`| Lịch sử thay đổi trạng thái đơn đặt vé |
| 19 | `FnbItem` | `fnb_items` | Danh mục bắp, nước, combo |
| 20 | `BookingFnb` | `booking_fnb` | Chi tiết bắp nước trong đơn hàng |
| 21 | `Coupon` | `coupons` | Mã giảm giá và khuyến mãi |
| 22 | `PaymentAttempt` | `payment_attempts` | Lịch sử các giao dịch thanh toán |
| 23 | `Refund` | `refunds` | Yêu cầu hoàn tiền và hủy vé |
| 24 | `Banner` | `banners` | Banner quảng cáo hiển thị trang chủ |
| 25 | `Holiday` | `holidays` | Lịch các ngày lễ trong năm |

* **Đặc điểm kỹ thuật tầng Entity:**
  - Khóa chính tự tăng với `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
  - Mọi liên kết `@ManyToOne` và `@OneToMany` đều cấu hình `fetch = FetchType.LAZY` để tối ưu hiệu năng truy vấn, tránh N+1 Query.
  - Tự động ghi nhận thời gian với `@CreationTimestamp` và `@UpdateTimestamp`.

---

### 2.2. Cấu trúc mã nguồn theo chuẩn mô hình MVC
Dự án được phân chia thành **83 source files**, tổ chức theo chuẩn kiến trúc:

```
com.example.demo2
├── config/
│   └── OpenApiConfig.java               # Cấu hình Swagger UI / OpenAPI 3.0
├── controller/
│   ├── BannerController.java            # API Banner quảng cáo
│   ├── CinemaController.java            # API Cụm rạp & Phòng chiếu
│   ├── GenreController.java             # API Thể loại phim
│   ├── HolidayController.java           # API Ngày lễ
│   └── MovieController.java             # API Phim & Diễn viên
├── dto/
│   ├── ApiResponse.java                 # Định dạng dữ liệu phản hồi chung
│   ├── banner/                          # DTO Request & Response cho Banner
│   ├── cinema/                          # DTO Request & Response cho Cinema
│   ├── cinemaroom/                      # DTO Request & Response cho CinemaRoom
│   ├── genre/                           # DTO Request & Response cho Genre
│   ├── holiday/                         # DTO Request & Response cho Holiday
│   ├── movie/                           # DTO Request & Response cho Movie
│   └── moviecast/                       # DTO Request & Response cho MovieCast
├── entity/                              # 25 Thực thể ánh xạ CSDL JPA
├── exception/
│   ├── BadRequestException.java         # Mã lỗi 400
│   ├── DuplicateResourceException.java  # Mã lỗi 409
│   ├── GlobalExceptionHandler.java      # Bắt lỗi toàn cục @RestControllerAdvice
│   └── ResourceNotFoundException.java   # Mã lỗi 404
├── mapper/                              # Lớp chuyển đổi giữa Entity và DTO
├── repository/                          # 9 Giao diện truy vấn Spring Data JPA
├── seeder/
│   └── DataSeeder.java                  # Tự động nạp dữ liệu mẫu khi khởi động
└── service/                             # Service Interfaces
    └── impl/                            # Service Implementations (Chứa Business Logic)
```

---

### 2.3. Cơ chế Xử lý Ngoại lệ & Chuẩn hóa Response
Mọi kết quả trả về từ hệ thống đều tuân thủ cấu trúc đồng nhất:

```json
{
  "status": 200,
  "message": "Thông báo kết quả xử lý",
  "data": { ... },
  "timestamp": "2026-10-09T15:50:01"
}
```

Khi có lỗi Validation từ người dùng (`@Valid`), `GlobalExceptionHandler` bắt lỗi và trả về chi tiết từng trường vi phạm:
```json
{
  "status": 400,
  "message": "Dữ liệu đầu vào không hợp lệ",
  "data": {
    "title": "Tiêu đề phim không được để trống",
    "durationMinutes": "Thời lượng phim phải lớn hơn 0"
  },
  "timestamp": "2026-10-09T15:50:01"
}
```

---

### 2.4. Danh mục RESTful API đã hoàn thành trong Tuần 1

| Nhóm chức năng | Phương thức | Endpoint | Mô tả |
|---|:---:|---|---|
| **Thể loại (Genre)** | `GET` | `/api/v1/genres` | Lấy danh sách tất cả thể loại |
| | `GET` | `/api/v1/genres/{id}` | Xem chi tiết thể loại |
| | `POST` | `/api/v1/genres` | Tạo mới thể loại |
| | `PUT` | `/api/v1/genres/{id}` | Cập nhật thể loại |
| | `DELETE` | `/api/v1/genres/{id}` | Xóa thể loại |
| **Phim (Movie)** | `GET` | `/api/v1/movies` | Tìm kiếm phim theo `keyword` & `status` |
| | `GET` | `/api/v1/movies/now-showing` | Lấy danh sách phim đang chiếu |
| | `GET` | `/api/v1/movies/coming-soon` | Lấy danh sách phim sắp chiếu |
| | `GET` | `/api/v1/movies/{id}` | Xem chi tiết phim (kèm thể loại & diễn viên) |
| | `POST` | `/api/v1/movies` | Thêm mới phim (gắn danh sách ID thể loại) |
| | `PUT` | `/api/v1/movies/{id}` | Cập nhật thông tin phim |
| | `DELETE` | `/api/v1/movies/{id}` | Xóa phim |
| **Diễn viên (Cast)** | `GET` | `/api/v1/movies/{movieId}/casts` | Lấy danh sách diễn viên của phim |
| | `POST` | `/api/v1/movies/{movieId}/casts` | Thêm diễn viên/đạo diễn vào phim |
| | `PUT` | `/api/v1/movies/{movieId}/casts/{castId}` | Cập nhật thông tin diễn viên |
| | `DELETE` | `/api/v1/movies/{movieId}/casts/{castId}` | Xóa diễn viên khỏi phim |
| **Cụm rạp (Cinema)** | `GET` | `/api/v1/cinemas` | Lấy danh sách rạp (lọc theo `city`) |
| | `GET` | `/api/v1/cinemas/{id}` | Xem chi tiết rạp (kèm danh sách phòng) |
| | `POST` | `/api/v1/cinemas` | Thêm cụm rạp mới |
| | `PUT` | `/api/v1/cinemas/{id}` | Sửa thông tin cụm rạp |
| | `DELETE` | `/api/v1/cinemas/{id}` | Xóa cụm rạp |
| **Phòng chiếu (Room)**| `GET` | `/api/v1/cinemas/{cinemaId}/rooms` | Lấy danh sách phòng theo cụm rạp |
| | `POST` | `/api/v1/cinemas/{cinemaId}/rooms` | Thêm phòng chiếu (2D, 3D, IMAX) |
| | `PUT` | `/api/v1/cinemas/{cinemaId}/rooms/{roomId}`| Cập nhật phòng chiếu |
| | `DELETE` | `/api/v1/cinemas/{cinemaId}/rooms/{roomId}`| Xóa phòng chiếu |
| **Banner quảng cáo** | `GET` | `/api/v1/banners` | Lấy danh sách banner |
| | `GET` | `/api/v1/banners/active` | Lấy danh sách banner kích hoạt cho trang chủ |
| | `POST` | `/api/v1/banners` | Thêm mới banner quảng cáo |
| | `PUT` | `/api/v1/banners/{id}` | Cập nhật banner |
| | `DELETE` | `/api/v1/banners/{id}` | Xóa banner |
| **Ngày lễ (Holiday)**| `GET` | `/api/v1/holidays` | Lấy danh sách ngày lễ (lọc theo `year`) |
| | `POST` | `/api/v1/holidays` | Tạo mới ngày lễ |
| | `PUT` | `/api/v1/holidays/{id}` | Cập nhật ngày lễ |
| | `DELETE` | `/api/v1/holidays/{id}` | Xóa ngày lễ |

---

## 3. KẾT QUẢ KIỂM THỬ THỰC TẾ (DEMO EVIDENCE)

### 3.1. Trạng thái khởi chạy & Tự động tạo bảng (Database Migration)
- Kết nối thành công đến database MySQL: `nguyenthanhnhan_2124110299`.
- Hibernate tự động sinh và ánh xạ toàn bộ 25 bảng cùng các ràng buộc khóa ngoại (Foreign Keys).
- Lớp `DataSeeder` tự động kiểm tra và chèn thành công:
  - **8 thể loại phim**: Hành Động, Kinh Dị, Hài Hước, Tình Cảm, Hoạt Hình, Khoa Học Viễn Tưởng, Phiêu Lưu, Tâm Lý.
  - **4 bộ phim tiêu biểu**: *Mai*, *Dune 2*, *Kung Fu Panda 4*, *Avatar 3* cùng danh sách đạo diễn/diễn viên.
  - **3 cụm rạp lớn**: *CGV Vincom Đồng Khởi*, *Lotte Cinema Cần Thơ*, *Galaxy Nguyễn Du* cùng các phòng chiếu chuẩn IMAX, Gold Class, 2D, 3D.
  - Banner quảng cáo trang chủ và các ngày lễ quốc gia.

### 3.2. Minh chứng gọi API thực tế (Real HTTP Requests)

#### Minh chứng 1: API Lấy danh sách phim đang chiếu
* **Request:** `GET http://localhost:8080/api/v1/movies/now-showing`
* **Response (HTTP 200 OK):**
```json
{
  "status": 200,
  "message": "Lấy danh sách phim đang chiếu thành công",
  "data": [
    {
      "id": 1,
      "title": "Mai",
      "durationMinutes": 131,
      "ageRating": "C18",
      "director": "Trấn Thành",
      "status": "now_showing",
      "genres": [
        { "id": 4, "name": "Tình Cảm", "slug": "tinh-cam" },
        { "id": 8, "name": "Tâm Lý", "slug": "tam-ly" }
      ]
    },
    {
      "id": 2,
      "title": "Dune: Hành Tinh Cát - Phần 2",
      "durationMinutes": 166,
      "ageRating": "C16",
      "director": "Denis Villeneuve",
      "status": "now_showing",
      "genres": [
        { "id": 1, "name": "Hành Động", "slug": "hanh-dong" },
        { "id": 6, "name": "Khoa Học Viễn Tưởng", "slug": "khoa-hoc-vien-tuong" },
        { "id": 7, "name": "Phiêu Lưu", "slug": "phieu-luu" }
      ]
    }
  ],
  "timestamp": "2026-10-09T15:49:48"
}
```

#### Minh chứng 2: API Chi tiết phim kèm Diễn viên & Thể loại
* **Request:** `GET http://localhost:8080/api/v1/movies/1`
* **Response (HTTP 200 OK):**
```json
{
  "status": 200,
  "message": "Lấy chi tiết phim thành công",
  "data": {
    "id": 1,
    "title": "Mai",
    "durationMinutes": 131,
    "ageRating": "C18",
    "genres": [
      { "id": 4, "name": "Tình Cảm" },
      { "id": 8, "name": "Tâm Lý" }
    ],
    "casts": [
      { "id": 1, "roleType": "director", "personName": "Trấn Thành" },
      { "id": 2, "roleType": "actor", "personName": "Phương Anh Đào", "characterName": "Mai" },
      { "id": 3, "roleType": "actor", "personName": "Tuấn Trần", "characterName": "Dương" }
    ]
  },
  "timestamp": "2026-10-09T15:50:01"
}
```

#### Minh chứng 3: API Cụm rạp kèm danh sách phòng chiếu
* **Request:** `GET http://localhost:8080/api/v1/cinemas/1`
* **Response (HTTP 200 OK):**
```json
{
  "status": 200,
  "message": "Lấy thông tin cụm rạp thành công",
  "data": {
    "id": 1,
    "name": "CGV Vincom Đồng Khởi",
    "city": "Hồ Chí Minh",
    "district": "Quận 1",
    "rooms": [
      { "id": 1, "name": "Phòng 1 (IMAX)", "roomType": "IMAX", "totalSeats": 120 },
      { "id": 2, "name": "Phòng 2 (Gold Class)", "roomType": "2D", "totalSeats": 48 },
      { "id": 3, "name": "Phòng 3 (Standard)", "roomType": "2D", "totalSeats": 90 }
    ]
  },
  "timestamp": "2026-10-09T15:51:28"
}
```

---

## 4. TÀI LIỆU API TRỰC QUAN (SWAGGER UI)
Hệ thống tích hợp công cụ tự động sinh tài liệu Swagger / OpenAPI:
* **Địa chỉ truy cập:** `http://localhost:8080/swagger-ui/index.html`
* **Lợi ích:** Cho phép Giảng viên và người kiểm thử trực tiếp trải nghiệm và gửi request thử nghiệm ngay trên giao diện web mà không cần thông qua Postman.

---

## 5. KHÓ KHĂN GẶP PHẢI & GIẢI PHÁP ĐÃ ÁP DỤNG

1. **Khó khăn về ánh xạ quan hệ nhiều-nhiều (N-N) giữa Movie và Genre:**
   * *Vấn đề:* Nếu dùng `@ManyToMany` thuần túy, việc mở rộng thêm các thuộc tính trong tương lai sẽ khó khăn và dễ gây lỗi Lazy Loading hoặc vòng lặp tuần hoàn khi tuần tự hóa JSON (Infinite Recursion).
   * *Giải pháp:* Tách thành thực thể trung gian `MovieGenre` có ID độc lập. Dùng DTO Response độc lập để loại bỏ hoàn toàn việc Entity tự tham chiếu chéo.
2. **Xung đột cấu hình IPv4/IPv6 khi kết nối MySQL trên Windows:**
   * *Vấn đề:* Cấu hình `localhost:3306` ưu tiên phân giải sang IPv6 (`::1`) dẫn đến Hibernate không nhận diện được Dialect metadata.
   * *Giải pháp:* Chuyển đổi tường minh sang IPv4 `127.0.0.1:3306` và khai báo chỉ định `MySQLDialect` trong `application.properties`.
3. **Tổ chức mã nguồn chuẩn quy ước dự án lớn:**
   * *Giải pháp:* Phân tách toàn bộ các lớp `*ServiceImpl` vào package `service.impl`, tách bạch interface và implementation, đảm bảo nguyên lý Dependency Inversion trong thiết kế SOLID.

---

## 6. ĐÁNH GIÁ TIẾN ĐỘ & KẾ HOẠCH TUẦN 2

### 6.1. Đánh giá
* **Mức độ hoàn thành:** **100% mục tiêu Tuần 1.**
* Dự án chạy ổn định, mã nguồn sạch, biên dịch không lỗi (`BUILD SUCCESS`). Toàn bộ nền móng CSDL và Master Data API đã sẵn sàng cho các nghiệp vụ đặt vé nâng cao.

### 6.2. Kế hoạch cụ thể cho Tuần 2
1. **Module Suất chiếu (`showtimes`):**
   - API Quản lý lịch chiếu theo rạp, theo ngày và theo phim.
   - Kiểm tra ràng buộc khung giờ chiếu (tránh xung đột phòng chiếu).
2. **Module Ghế ngồi & Sơ đồ ghế (`seats`):**
   - Tạo ma trận ghế tự động theo hàng/cột (Standard, VIP, Sweetbox/Couple).
   - API lấy trạng thái ghế theo từng suất chiếu cụ thể (ghế trống, ghế đang giữ, ghế đã bán).
3. **Module Bảng giá vé linh hoạt (`ticket_price_rules`):**
   - Áp dụng công thức tính giá vé dựa vào: Định dạng phòng (2D/3D/IMAX), Loại ghế (Thường/VIP), Khung giờ (Sáng/Tối), và Ngày (Thường/Cuối tuần/Ngày lễ).
4. **Module Bắp nước (`fnb_items`):**
   - Xây dựng API danh mục bắp nước đi kèm khi đặt vé.
