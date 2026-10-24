# BÁO CÁO TIẾN ĐỘ DỰ ÁN - TUẦN 3

* **Đề tài:** XÂY DỰNG HỆ THỐNG ĐẶT VÉ XEM PHIM TRỰC TUYẾN (CINEMA BOOKING SYSTEM)
* **Sinh viên thực hiện:** Nguyễn Thành Nhân
* **Mã số sinh viên (MSSV):** 2124110299
* **Công nghệ:** Spring Boot 4.x, Java 21, MySQL 8.x, Spring Data JPA, Hibernate, Lombok, Jakarta Validation, SpringDoc OpenAPI (Swagger UI), ZaloPay OpenAPI v2 (HMAC-SHA256).
* **Thời gian thực hiện:** Tuần 3 / 4 tuần

---

## 1. MỤC TIÊU TUẦN 3 ĐÃ HOÀN THÀNH

Trong Tuần 3, hệ thống đã hoàn thiện toàn bộ **Luồng Giao dịch Đặt vé, Giữ ghế 5 phút, Mã giảm giá, Tích hợp Cổng thanh toán ZaloPay Sandbox thật (OpenAPI v2), Hoàn tiền và Tích điểm thành viên**:

1. **Module Khuyến mãi & Mã giảm giá (`Coupons`):**
   - Quản lý CRUD mã giảm giá theo 2 hình thức: Giảm theo phần trăm (`percentage` kèm trần giảm tối đa `maxDiscount`) và Giảm số tiền cố định (`fixed_amount`).
   - Thuật toán kiểm tra tính hợp lệ của mã giảm giá (`/api/v1/coupons/apply`): Kiểm tra trạng thái hoạt động, thời hạn hiệu lực (`validFrom` – `validTo`), tổng số lượt dùng tối đa (`usageLimit`), giới hạn số lần dùng trên mỗi khách hàng (`perUserLimit`), và giá trị đơn hàng tối thiểu (`minOrderAmount`).
2. **Module Đặt vé, Khóa ghế 5 phút & Vé điện tử QR (`Bookings`, `Tickets`, `BookingFnb`, `BookingStatusHistory`):**
   - **Tạo đơn đặt vé tổng hợp (`POST /api/v1/bookings`):**
     - **Thời hạn giữ ghế 5 phút (`holdExpiresAt = now + 5 phút`):** Tự động giải phóng các đơn giữ ghế đã quá hạn 5 phút trước khi kiểm tra ghế trống và tự động loại bỏ các ghế hết hạn giữ khỏi sơ đồ ghế.
     - **Kiểm tra chống trùng ghế & chống lệch phòng/rạp chiếu:**
       - Chặn gửi trùng ID ghế ngay trong cùng một yêu cầu đặt vé (ví dụ `seatIds: [1, 1]`).
       - Chặn đặt ghế thuộc phòng chiếu khác hoặc cụm rạp khác với suất chiếu đã chọn (kiểm tra chéo `cinemaId`, `roomId` của ghế và suất chiếu).
       - Chặn tuyệt đối việc 2 khách hàng đặt trùng cùng 1 ghế đã bán hoặc đang trong 5 phút giữ chỗ.
     - Tự động tính giá từng ghế qua `TicketPriceRuleService`, cộng tiền combo bắp nước (`BookingFnb`) và trừ tiền khuyến mãi (`Coupon`).
     - Sinh mã đơn hàng duy nhất (`bookingCode`), mã vé điện tử (`ticketCode`) và đường dẫn mã QR (`qrCodeUrl`) cho từng ghế.
   - **Quản lý vòng đời đơn hàng & Soát vé an toàn tại rạp (`POST /api/v1/bookings/tickets/check-in`):**
     - **Chặn vé giả:** Báo lỗi ngay nếu mã vé (`ticketCode`) không tồn tại trong cơ sở dữ liệu.
     - **Chặn vé đã sử dụng (đã Check-in rồi):** Báo rõ thời điểm vé đã được quét trước đó, ngăn chặn việc dùng 1 vé vào cửa nhiều lần.
     - **Chặn vé chưa thanh toán / đã hủy / sai rạp hoặc sai suất chiếu.**
     - Lưu vết toàn bộ lịch sử chuyển đổi trạng thái đơn hàng vào bảng `booking_status_history`.
3. **Module Tích hợp Cổng Thanh toán ZaloPay Sandbox Thật (`OpenAPI v2`), Hoàn tiền & Tích điểm (`PaymentAttempts`, `Refunds`, `LoyaltyTransactions`):**
   - **Tích hợp ZaloPay OpenAPI v2 (`POST /api/v1/payments/zalopay/create/{bookingId}`):**
     - Tạo chữ ký điện tử bảo mật `HMAC-SHA256` bằng `key1` theo đúng chuẩn ZaloPay (`app_id|app_trans_id|app_user|amount|app_time|embed_data|item`).
     - Gọi HTTP POST trực tiếp tới máy chủ `https://sb-openapi.zalopay.vn/v2/create` và nhận về đường dẫn thanh toán thật `orderUrl` (`https://qcgateway.zalopay.vn/...`).
   - **Truy vấn trạng thái & Trang nhận kết quả ZaloPay (`/api/v1/payments/zalopay/query/{appTransId}` & `/api/v1/payments/zalopay/return`):**
     - Tự động kiểm tra trạng thái đơn hàng với máy chủ ZaloPay (`https://sb-openapi.zalopay.vn/v2/query`).
     - Khi thanh toán thành công (`return_code = 1`): Tự động chuyển trạng thái đơn sang `confirmed` / `paid`, **tự động cộng điểm thưởng (`LoyaltyTransaction` - 1 điểm / 1.000 VNĐ)**, nâng hạng thành viên (`bronze` ➔ `silver` ➔ `gold` ➔ `platinum`) và hiển thị trang Hóa đơn Vé điện tử QR trực quan.
   - **Xử lý hủy vé đã thanh toán & Hoàn tiền (`/api/v1/refunds`):**
     - Khách hàng gửi yêu cầu hoàn tiền (`POST /api/v1/refunds`).
     - Quản trị viên phê duyệt hoặc từ chối (`PUT /api/v1/refunds/{id}/process`). Khi duyệt hoàn tiền, hệ thống tự động hủy đơn, giải phóng ghế ngồi, hoàn lại lượt dùng Coupon và thu hồi số điểm thưởng đã cộng.

---

## 2. DANH SÁCH ENDPOINTS API TUẦN 3

### 2.1. Module Mã giảm giá (`/api/v1/coupons`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/coupons` | Tạo mã giảm giá (Coupon) mới |
| `GET` | `/api/v1/coupons` | Lấy danh sách mã giảm giá (hỗ trợ lọc `activeOnly=true`) |
| `GET` | `/api/v1/coupons/{id}` | Xem chi tiết mã giảm giá theo ID |
| `GET` | `/api/v1/coupons/code/{code}` | Tra cứu mã giảm giá theo mã Code |
| `PUT` | `/api/v1/coupons/{id}` | Cập nhật mã giảm giá |
| `DELETE` | `/api/v1/coupons/{id}` | Xóa mã giảm giá |
| `POST` | `/api/v1/coupons/apply` | **Kiểm tra điều kiện & tính số tiền giảm giá cho đơn hàng** |

### 2.2. Module Đặt vé & Soát vé QR (`/api/v1/bookings`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/bookings` | **Đặt vé mới** (chọn ghế + bắp nước + áp coupon, khóa ghế **5 phút**, chống trùng ghế/rạp) |
| `GET` | `/api/v1/bookings` | Lấy danh sách đơn đặt vé (lọc theo `userId`, `showtimeId`, `status`) |
| `GET` | `/api/v1/bookings/{id}` | Xem chi tiết đơn đặt vé (kèm vé QR, F&B và lịch sử trạng thái) |
| `GET` | `/api/v1/bookings/code/{bookingCode}` | Tra cứu đơn đặt vé theo mã `bookingCode` |
| `GET` | `/api/v1/bookings/{id}/history` | Xem lịch sử trạng thái đơn hàng (`BookingStatusHistory`) |
| `POST` | `/api/v1/bookings/{id}/cancel` | Hủy đơn đặt vé chưa thanh toán & giải phóng ghế |
| `POST` | `/api/v1/bookings/tickets/check-in` | **Soát vé QR Code (Check-in)** (Chặn vé giả, vé chưa trả tiền, vé sai rạp hoặc vé đã check-in rồi) |
| `POST` | `/api/v1/bookings/{id}/fnb/claim` | Xác nhận giao bắp nước tại quầy F&B |
| `POST` | `/api/v1/bookings/release-expired` | Tự động quét và giải phóng các đơn giữ ghế quá hạn **5 phút** |

### 2.3. Module Thanh toán ZaloPay Sandbox, Hoàn tiền & Điểm thưởng (`/api/v1/payments`, `/api/v1/refunds`, `/api/v1/loyalty-transactions`)
| Method | Endpoint | Mô tả |
|:---:|---|---|
| `POST` | `/api/v1/payments/zalopay/create/{bookingId}` | **Tạo đơn thanh toán thật trên cổng ZaloPay Sandbox** (trả về `orderUrl`) |
| `POST` | `/api/v1/payments/zalopay/query/{appTransId}` | **Truy vấn trạng thái giao dịch từ ZaloPay Server** (`/v2/query`) & tự động kích hoạt vé |
| `POST` | `/api/v1/payments/zalopay/callback` | Webhook nhận thông báo kết quả thanh toán từ ZaloPay Server |
| `GET` | `/api/v1/payments/zalopay/return` | Trang đích (Redirect URL) hiển thị hóa đơn & mã QR vé ngay sau khi thanh toán trên ZaloPay |
| `POST` | `/api/v1/payments/process` | Thanh toán giả lập nhanh (không qua ZaloPay) & cộng điểm `Loyalty` |
| `GET` | `/api/v1/payments/booking/{bookingId}` | Xem lịch sử các lần giao dịch thanh toán của đơn hàng |
| `POST` | `/api/v1/refunds` | Gửi yêu cầu hủy vé & hoàn tiền cho đơn đã thanh toán |
| `PUT` | `/api/v1/refunds/{id}/process` | **Admin phê duyệt / từ chối hoàn tiền** (tự động giải phóng ghế & thu hồi điểm) |
| `GET` | `/api/v1/refunds` | Lấy danh sách yêu cầu hoàn tiền (lọc theo `status`) |
| `GET` | `/api/v1/refunds/booking/{bookingId}` | Xem thông tin hoàn tiền theo đơn đặt vé |
| `GET` | `/api/v1/loyalty-transactions/user/{userId}` | Xem lịch sử tích điểm / trừ điểm thưởng của khách hàng |
