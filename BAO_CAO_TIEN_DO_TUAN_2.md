# BÁO CÁO TIẾN ĐỘ DỰ ÁN - TUẦN 2

* **Đề tài:** XÂY DỰNG HỆ THỐNG ĐẶT VÉ XEM PHIM TRỰC TUYẾN (CINEMA BOOKING SYSTEM)
* **Sinh viên thực hiện:** Nguyễn Thành Nhân
* **Mã số sinh viên (MSSV):** 2124110299
* **Công nghệ:** Spring Boot 4.x, Java 21, MySQL 8.x, Spring Data JPA, Hibernate, Lombok, Jakarta Validation, SpringDoc OpenAPI (Swagger UI).
* **Thời gian thực hiện:** Tuần 2 / 4 tuần

---

## 1. MỤC TIÊU TUẦN 2 ĐÃ HOÀN THÀNH

Trong Tuần 2, hệ thống đã hoàn thiện toàn bộ tầng nghiệp vụ lõi (Core Business Modules) phục vụ cho quá trình chọn suất chiếu, chọn ghế và tính giá vé:

1. **Module Suất chiếu (`Showtimes`):**
   - Quản lý lịch chiếu theo rạp, phòng, phim và ngày chiếu.
   - Thuật toán **kiểm tra chống xung đột lịch chiếu (Schedule Conflict Detection)**: tự động ngăn chặn việc xếp 2 suất chiếu trùng phòng/giờ và tự động cộng thêm buffer 15 phút dọn dẹp vệ sinh phòng.
2. **Module Ghế ngồi & Sơ đồ ma trận ghế (`Seats & Seat Layout`):**
   - API tự động sinh hàng loạt ghế theo ma trận hàng x cột (A01, A02, B01...) với các phân loại ghế: `Standard`, `VIP`, `Couple/Sweetbox`.
   - API truy xuất sơ đồ ghế theo suất chiếu (`/api/v1/showtimes/{id}/seats`), tự động liên kết với vé đã bán để hiển thị trạng thái `AVAILABLE` (ghế trống) hoặc `BOOKED` (ghế đã đặt).
3. **Module Bảng giá vé linh hoạt & Tính giá động (`Ticket Price Rules`):**
   - CRUD các quy tắc giá vé theo: Định dạng phòng (`2D`, `3D`, `IMAX`), Loại ghế (`Standard`, `VIP`, `Couple`), Khung giờ (`Morning`, `Regular`, `Evening`), và Loại ngày (`Weekday`, `Weekend`, `Holiday`).
   - Tự động tích hợp bảng `holidays` để nhận diện ngày lễ Việt Nam.
   - API tính giá vé động (`/api/v1/ticket-price-rules/calculate`).
4. **Module Bắp nước & Combo (`F&B Items`):**
   - Quản lý danh mục bắp nước, nước ngọt và các combo ưu đãi (`popcorn`, `drink`, `combo`).
5. **Cập nhật DataSeeder tự động:**
   - Nạp tự động ma trận ghế cho tất cả phòng chiếu hiện có.
   - Nạp danh mục quy tắc giá vé.
   - Lập lịch chiếu thực tế cho các phim đang chiếu ("Mai", "Dune 2", "Kung Fu Panda 4").
   - Nạp danh mục F&B.
   - Tạo đơn đặt vé mẫu để kiểm thử trực quan trạng thái ghế đã bán trên sơ đồ.

---

## 2. DANH SÁCH ENDPOINTS API TUẦN 2

### 2.1. Module Suất chiếu (`/api/v1/showtimes`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/showtimes` | Tạo suất chiếu mới (có validate chống trùng phòng/giờ) |
| `GET` | `/api/v1/showtimes` | Lấy danh sách suất chiếu (hỗ trợ lọc theo `cinemaId`, `movieId`, `showDate`) |
| `GET` | `/api/v1/showtimes/{id}` | Lấy chi tiết suất chiếu |
| `PUT` | `/api/v1/showtimes/{id}` | Cập nhật suất chiếu |
| `DELETE` | `/api/v1/showtimes/{id}` | Xóa suất chiếu |
| `GET` | `/api/v1/showtimes/{id}/seats` | **Lấy sơ đồ ma trận ghế & trạng thái trống/đã đặt** |

### 2.2. Module Ghế ngồi (`/api/v1/rooms/.../seats` & `/api/v1/seats`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/rooms/{roomId}/seats/generate` | **Tự động sinh ma trận ghế hàng loạt** theo hàng x cột |
| `GET` | `/api/v1/rooms/{roomId}/seats` | Lấy danh sách ghế của phòng chiếu |
| `POST` | `/api/v1/rooms/{roomId}/seats` | Thêm ghế thủ công |
| `PUT` | `/api/v1/seats/{seatId}` | Cập nhật thông tin ghế |
| `DELETE` | `/api/v1/seats/{seatId}` | Xóa ghế |

### 2.3. Module Bảng giá vé linh hoạt (`/api/v1/ticket-price-rules`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/ticket-price-rules` | Tạo quy tắc giá vé mới |
| `GET` | `/api/v1/ticket-price-rules` | Lấy danh sách quy tắc giá (hỗ trợ lọc theo `cinemaId`) |
| `GET` | `/api/v1/ticket-price-rules/{id}` | Chi tiết quy tắc giá |
| `PUT` | `/api/v1/ticket-price-rules/{id}` | Cập nhật quy tắc giá |
| `DELETE` | `/api/v1/ticket-price-rules/{id}` | Xóa quy tắc giá |
| `POST` | `/api/v1/ticket-price-rules/calculate` | **Tính giá vé động** dựa trên Suất chiếu, Loại ghế, Khách hàng |

### 2.4. Module Bắp nước (`/api/v1/fnb-items`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/fnb-items` | Tạo món bắp nước / combo mới |
| `GET` | `/api/v1/fnb-items` | Lấy danh sách bắp nước (lọc theo `category`, `activeOnly`) |
| `GET` | `/api/v1/fnb-items/{id}` | Lấy chi tiết món bắp nước |
| `PUT` | `/api/v1/fnb-items/{id}` | Cập nhật món bắp nước |
| `DELETE` | `/api/v1/fnb-items/{id}` | Xóa món bắp nước |

---

## 3. CÁCH KIỂM THỬ TRỰC QUAN

1. **Khởi chạy ứng dụng:** Chạy class `Demo2Application.java` trong IDE hoặc gõ lệnh:
   ```bash
   ./mvnw spring-boot:run
   ```
2. **Truy cập Swagger UI:**
   Mở trình duyệt truy cập: `http://localhost:8080/swagger-ui.html`
3. **Các luồng kiểm thử mẫu:**
   - **Xem sơ đồ ghế của suất chiếu:** Gọi `GET /api/v1/showtimes/1/seats` -> Quan sát ma trận ghế với các ghế `AVAILABLE` và ghế mẫu `BOOKED`.
   - **Tính giá vé:** Gọi `POST /api/v1/ticket-price-rules/calculate` với body:
     ```json
     {
       "showtimeId": 1,
       "seatType": "vip",
       "customerType": "adult"
     }
     ```
   - **Kiểm tra chống trùng lịch:** Thử tạo suất chiếu mới tại cùng phòng chiếu với khoảng giờ đã tồn tại -> Hệ thống trả về `400 Bad Request` kèm thông báo chi tiết xung đột.
