package com.example.demo2.seeder;

import com.example.demo2.entity.*;
import com.example.demo2.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MovieCastRepository movieCastRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;
    private final BannerRepository bannerRepository;
    private final HolidayRepository holidayRepository;
    private final UserRepository userRepository;

    // Repositories cho Tuần 2
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final TicketPriceRuleRepository ticketPriceRuleRepository;
    private final FnbItemRepository fnbItemRepository;
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedWeek1Data();
        seedWeek2Data();
    }

    private void seedWeek1Data() {
        if (genreRepository.count() > 0) {
            log.info("Dữ liệu mẫu Tuần 1 đã tồn tại.");
            return;
        }

        log.info("Bắt đầu khởi tạo dữ liệu mẫu Tuần 1...");

        // 1. Thể loại phim (Genres)
        Genre hanhDong = genreRepository.save(Genre.builder().name("Hành Động").slug("hanh-dong").build());
        Genre kinhDi = genreRepository.save(Genre.builder().name("Kinh Dị").slug("kinh-di").build());
        Genre haiHuoc = genreRepository.save(Genre.builder().name("Hài Hước").slug("hai-huoc").build());
        Genre tinhCam = genreRepository.save(Genre.builder().name("Tình Cảm").slug("tinh-cam").build());
        Genre hoatHinh = genreRepository.save(Genre.builder().name("Hoạt Hình").slug("hoat-hinh").build());
        Genre sciFi = genreRepository.save(Genre.builder().name("Khoa Học Viễn Tưởng").slug("khoa-hoc-vien-tuong").build());
        Genre phieuLuu = genreRepository.save(Genre.builder().name("Phiêu Lưu").slug("phieu-luu").build());
        Genre tamLy = genreRepository.save(Genre.builder().name("Tâm Lý").slug("tam-ly").build());

        // 2. Phim (Movies)
        Movie mai = movieRepository.save(Movie.builder()
                .title("Mai")
                .description("Câu chuyện về cuộc đời của Mai, một phụ nữ làm nghề massage trị liệu với nhiều uẩn khúc trong quá khứ.")
                .durationMinutes(131)
                .ageRating("C18")
                .language("Tiếng Việt")
                .director("Trấn Thành")
                .status("now_showing")
                .releaseDate(LocalDate.of(2024, 2, 10))
                .endDate(LocalDate.of(2026, 12, 31))
                .posterUrl("https://images.unsplash.com/photo-1489599849927-2ee91cede3ba")
                .trailerUrl("https://www.youtube.com/watch?v=example1")
                .build());

        movieGenreRepository.save(MovieGenre.builder().movie(mai).genre(tamLy).build());
        movieGenreRepository.save(MovieGenre.builder().movie(mai).genre(tinhCam).build());

        movieCastRepository.save(MovieCast.builder().movie(mai).roleType("director").personName("Trấn Thành").displayOrder(1).build());
        movieCastRepository.save(MovieCast.builder().movie(mai).roleType("actor").personName("Phương Anh Đào").characterName("Mai").displayOrder(2).build());
        movieCastRepository.save(MovieCast.builder().movie(mai).roleType("actor").personName("Tuấn Trần").characterName("Dương").displayOrder(3).build());

        Movie dune2 = movieRepository.save(Movie.builder()
                .title("Dune: Hành Tinh Cát - Phần 2")
                .description("Paul Atreides hợp tác cùng Chani và người Fremen trong cuộc báo thù chống lại những kẻ đã hủy hoại gia tộc mình.")
                .durationMinutes(166)
                .ageRating("C16")
                .language("Tiếng Anh - Phụ đề tiếng Việt")
                .director("Denis Villeneuve")
                .status("now_showing")
                .releaseDate(LocalDate.of(2024, 3, 1))
                .endDate(LocalDate.of(2026, 12, 31))
                .posterUrl("https://images.unsplash.com/photo-1536440136628-849c177e76a1")
                .trailerUrl("https://www.youtube.com/watch?v=example2")
                .build());

        movieGenreRepository.save(MovieGenre.builder().movie(dune2).genre(sciFi).build());
        movieGenreRepository.save(MovieGenre.builder().movie(dune2).genre(phieuLuu).build());
        movieGenreRepository.save(MovieGenre.builder().movie(dune2).genre(hanhDong).build());

        movieCastRepository.save(MovieCast.builder().movie(dune2).roleType("director").personName("Denis Villeneuve").displayOrder(1).build());
        movieCastRepository.save(MovieCast.builder().movie(dune2).roleType("actor").personName("Timothée Chalamet").characterName("Paul Atreides").displayOrder(2).build());
        movieCastRepository.save(MovieCast.builder().movie(dune2).roleType("actor").personName("Zendaya").characterName("Chani").displayOrder(3).build());

        Movie kungfuPanda = movieRepository.save(Movie.builder()
                .title("Kung Fu Panda 4")
                .description("Po chuẩn bị trở thành Thủ lĩnh Tinh thần của Thung lũng Bình Yên và phải tìm kiếm một Thần Long Đại Hiệp mới.")
                .durationMinutes(94)
                .ageRating("P")
                .language("Lồng tiếng Việt / Phụ đề")
                .director("Mike Mitchell")
                .status("now_showing")
                .releaseDate(LocalDate.of(2024, 3, 8))
                .endDate(LocalDate.of(2026, 12, 31))
                .posterUrl("https://images.unsplash.com/photo-1518709268805-4e9042af9f23")
                .trailerUrl("https://www.youtube.com/watch?v=example3")
                .build());

        movieGenreRepository.save(MovieGenre.builder().movie(kungfuPanda).genre(hoatHinh).build());
        movieGenreRepository.save(MovieGenre.builder().movie(kungfuPanda).genre(haiHuoc).build());
        movieGenreRepository.save(MovieGenre.builder().movie(kungfuPanda).genre(phieuLuu).build());

        // 3. Cụm rạp & Phòng chiếu (Cinemas & CinemaRooms)
        Cinema cgvVincom = cinemaRepository.save(Cinema.builder()
                .name("CGV Vincom Đồng Khởi")
                .address("Tầng 3, TTTM Vincom Center, 72 Lê Thánh Tôn, Bến Nghé")
                .city("Hồ Chí Minh")
                .district("Quận 1")
                .phone("1900 6017")
                .email("cgv_dongkhoi@cgv.vn")
                .latitude(new BigDecimal("10.7779"))
                .longitude(new BigDecimal("106.7018"))
                .build());

        cinemaRoomRepository.save(CinemaRoom.builder().cinema(cgvVincom).name("Phòng 1 (IMAX)").roomType("IMAX").totalSeats(120).status("active").build());
        cinemaRoomRepository.save(CinemaRoom.builder().cinema(cgvVincom).name("Phòng 2 (Gold Class)").roomType("2D").totalSeats(48).status("active").build());
        cinemaRoomRepository.save(CinemaRoom.builder().cinema(cgvVincom).name("Phòng 3 (Standard)").roomType("2D").totalSeats(90).status("active").build());

        Cinema lotteCanTho = cinemaRepository.save(Cinema.builder()
                .name("Lotte Cinema Cần Thơ")
                .address("Tầng 3 Lotte Mart, 84 Mậu Thân, An Hòa")
                .city("Cần Thơ")
                .district("Ninh Kiều")
                .phone("0292 3696 868")
                .email("lotte_cantho@lotte.vn")
                .latitude(new BigDecimal("10.0452"))
                .longitude(new BigDecimal("105.7469"))
                .build());

        cinemaRoomRepository.save(CinemaRoom.builder().cinema(lotteCanTho).name("Cinema 1").roomType("2D").totalSeats(80).status("active").build());
        cinemaRoomRepository.save(CinemaRoom.builder().cinema(lotteCanTho).name("Cinema 2 (3D)").roomType("3D").totalSeats(75).status("active").build());

        Cinema galaxyNguyenDu = cinemaRepository.save(Cinema.builder()
                .name("Galaxy Nguyễn Du")
                .address("116 Nguyễn Du, Phường Bến Thành")
                .city("Hồ Chí Minh")
                .district("Quận 1")
                .phone("1900 2224")
                .email("galaxy_nguyendu@galaxy.vn")
                .latitude(new BigDecimal("10.7735"))
                .longitude(new BigDecimal("106.6934"))
                .build());

        cinemaRoomRepository.save(CinemaRoom.builder().cinema(galaxyNguyenDu).name("Screen 1").roomType("2D").totalSeats(100).status("active").build());
        cinemaRoomRepository.save(CinemaRoom.builder().cinema(galaxyNguyenDu).name("Screen 2").roomType("2D").totalSeats(85).status("active").build());

        // 4. Banner quảng cáo
        bannerRepository.save(Banner.builder()
                .title("Dune 2 - Bom tấn viễn tưởng không thể bỏ lỡ")
                .imageUrl("https://images.unsplash.com/photo-1536440136628-849c177e76a1")
                .linkUrl("/movies/" + dune2.getId())
                .targetType("movie")
                .targetId(dune2.getId())
                .displayOrder(1)
                .isActive(true)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2026, 12, 31))
                .build());

        bannerRepository.save(Banner.builder()
                .title("Ưu đãi thành viên - Giảm 20% bắp nước mỗi thứ Tư")
                .imageUrl("https://images.unsplash.com/photo-1572177812156-58036aae439c")
                .linkUrl("/promotions/member-wednesday")
                .targetType("event")
                .displayOrder(2)
                .isActive(true)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2026, 12, 31))
                .build());

        // 5. Ngày lễ (Holidays)
        holidayRepository.save(Holiday.builder().holidayDate(LocalDate.of(2026, 1, 1)).name("Tết Dương Lịch").type("national").build());
        holidayRepository.save(Holiday.builder().holidayDate(LocalDate.of(2026, 4, 30)).name("Ngày Giải phóng Miền Nam").type("national").build());
        holidayRepository.save(Holiday.builder().holidayDate(LocalDate.of(2026, 5, 1)).name("Quốc tế Lao động").type("national").build());
        holidayRepository.save(Holiday.builder().holidayDate(LocalDate.of(2026, 9, 2)).name("Quốc Khánh").type("national").build());

        // 6. Tài khoản mẫu
        userRepository.save(User.builder()
                .fullName("Quản Trị Viên")
                .email("admin@cinema.com")
                .phone("0901234567")
                .passwordHash("$2a$10$dummyHashAdminPassword123")
                .role("ADMIN")
                .loyaltyPoints(1000)
                .memberTier("platinum")
                .status("active")
                .build());

        userRepository.save(User.builder()
                .fullName("Nguyễn Văn A")
                .email("khachhang@gmail.com")
                .phone("0912345678")
                .passwordHash("$2a$10$dummyHashCustomerPassword123")
                .role("CUSTOMER")
                .loyaltyPoints(50)
                .memberTier("bronze")
                .status("active")
                .build());

        log.info("Khởi tạo dữ liệu mẫu Tuần 1 hoàn tất!");
    }

    private void seedWeek2Data() {
        seedSeats();
        seedTicketPriceRules();
        seedShowtimes();
        seedFnbItems();
        seedSampleBookingsAndTickets();
    }

    private void seedSeats() {
        if (seatRepository.count() > 0) {
            log.info("Dữ liệu ghế đã tồn tại.");
            return;
        }

        log.info("Tự động sinh ma trận ghế cho tất cả phòng chiếu...");
        List<CinemaRoom> rooms = cinemaRoomRepository.findAll();

        for (CinemaRoom room : rooms) {
            int rows = 8;
            int cols = 10;
            Set<String> vipRows = new HashSet<>(Arrays.asList("D", "E", "F"));
            Set<String> coupleRows = new HashSet<>(Collections.singletonList("H"));

            if ("IMAX".equalsIgnoreCase(room.getRoomType())) {
                rows = 10;
                cols = 12;
                vipRows = new HashSet<>(Arrays.asList("D", "E", "F", "G"));
                coupleRows = new HashSet<>(Collections.singletonList("J"));
            } else if ("Phòng 2 (Gold Class)".equalsIgnoreCase(room.getName())) {
                rows = 6;
                cols = 8;
                vipRows = new HashSet<>(Arrays.asList("C", "D"));
                coupleRows = new HashSet<>(Collections.singletonList("F"));
            }

            List<Seat> seats = new ArrayList<>();
            for (int r = 1; r <= rows; r++) {
                char rowChar = (char) ('A' + r - 1);
                String rowName = String.valueOf(rowChar);

                String seatType = "standard";
                if (coupleRows.contains(rowName)) {
                    seatType = "couple";
                } else if (vipRows.contains(rowName)) {
                    seatType = "vip";
                }

                for (int c = 1; c <= cols; c++) {
                    String seatNumber = String.format("%s%02d", rowName, c);
                    seats.add(Seat.builder()
                            .cinemaRoom(room)
                            .seatNumber(seatNumber)
                            .seatType(seatType)
                            .rowIndex(r)
                            .colIndex(c)
                            .build());
                }
            }

            seatRepository.saveAll(seats);
            room.setTotalSeats(seats.size());
            cinemaRoomRepository.save(room);
        }
        log.info("Sinh ma trận ghế thành công!");
    }

    private void seedTicketPriceRules() {
        if (ticketPriceRuleRepository.count() > 0) {
            log.info("Dữ liệu bảng giá vé đã tồn tại.");
            return;
        }

        log.info("Khởi tạo danh mục quy tắc tính giá vé (Ticket Price Rules)...");

        // Quy tắc chuẩn 2D
        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("standard").dayType("weekday").timeSlot("morning")
                .customerType("adult").price(new BigDecimal("65000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("standard").dayType("weekday").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("75000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("standard").dayType("weekend").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("95000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("standard").dayType("holiday").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("110000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("vip").dayType("weekday").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("90000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("vip").dayType("weekend").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("115000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("2D").seatType("couple").dayType("weekend").timeSlot("evening")
                .customerType("adult").price(new BigDecimal("190000.00")).isActive(true).build());

        // Quy tắc 3D
        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("3D").seatType("standard").dayType("weekday").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("110000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("3D").seatType("standard").dayType("weekend").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("135000.00")).isActive(true).build());

        // Quy tắc IMAX
        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("IMAX").seatType("standard").dayType("weekday").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("150000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("IMAX").seatType("standard").dayType("weekend").timeSlot("regular")
                .customerType("adult").price(new BigDecimal("180000.00")).isActive(true).build());

        ticketPriceRuleRepository.save(TicketPriceRule.builder()
                .screenType("IMAX").seatType("vip").dayType("weekend").timeSlot("evening")
                .customerType("adult").price(new BigDecimal("220000.00")).isActive(true).build());

        log.info("Khởi tạo bảng giá vé hoàn tất!");
    }

    private void seedShowtimes() {
        if (showtimeRepository.count() > 0) {
            log.info("Dữ liệu suất chiếu đã tồn tại.");
            return;
        }

        log.info("Khởi tạo lịch chiếu mẫu (Showtimes)...");
        List<Movie> movies = movieRepository.findAll();
        List<CinemaRoom> rooms = cinemaRoomRepository.findAll();

        if (movies.isEmpty() || rooms.isEmpty()) return;

        Movie mai = movies.stream().filter(m -> m.getTitle().contains("Mai")).findFirst().orElse(movies.get(0));
        Movie dune2 = movies.stream().filter(m -> m.getTitle().contains("Dune")).findFirst().orElse(movies.get(0));
        Movie kungfu = movies.stream().filter(m -> m.getTitle().contains("Panda")).findFirst().orElse(movies.get(0));

        CinemaRoom imaxRoom = rooms.stream().filter(r -> "IMAX".equalsIgnoreCase(r.getRoomType())).findFirst().orElse(rooms.get(0));
        CinemaRoom room2D_1 = rooms.stream().filter(r -> "2D".equalsIgnoreCase(r.getRoomType())).findFirst().orElse(rooms.get(0));
        CinemaRoom room2D_2 = rooms.size() > 2 ? rooms.get(2) : room2D_1;

        LocalDate today = LocalDate.now();

        // Lịch chiếu hôm nay & ngày mai
        for (int dayOffset = 0; dayOffset <= 2; dayOffset++) {
            LocalDate date = today.plusDays(dayOffset);

            // IMAX Room: chiếu Dune 2
            showtimeRepository.save(Showtime.builder()
                    .movie(dune2)
                    .cinemaRoom(imaxRoom)
                    .showDate(date)
                    .startTime(LocalTime.of(10, 0))
                    .screenFormat("IMAX")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(dune2)
                    .cinemaRoom(imaxRoom)
                    .showDate(date)
                    .startTime(LocalTime.of(14, 0))
                    .screenFormat("IMAX")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(dune2)
                    .cinemaRoom(imaxRoom)
                    .showDate(date)
                    .startTime(LocalTime.of(18, 0))
                    .screenFormat("IMAX")
                    .status("open")
                    .build());

            // 2D Room 1: chiếu phim Mai
            showtimeRepository.save(Showtime.builder()
                    .movie(mai)
                    .cinemaRoom(room2D_1)
                    .showDate(date)
                    .startTime(LocalTime.of(9, 30))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(mai)
                    .cinemaRoom(room2D_1)
                    .showDate(date)
                    .startTime(LocalTime.of(13, 0))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(mai)
                    .cinemaRoom(room2D_1)
                    .showDate(date)
                    .startTime(LocalTime.of(16, 30))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(mai)
                    .cinemaRoom(room2D_1)
                    .showDate(date)
                    .startTime(LocalTime.of(20, 0))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            // 2D Room 2: chiếu Kung Fu Panda
            showtimeRepository.save(Showtime.builder()
                    .movie(kungfu)
                    .cinemaRoom(room2D_2)
                    .showDate(date)
                    .startTime(LocalTime.of(10, 30))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(kungfu)
                    .cinemaRoom(room2D_2)
                    .showDate(date)
                    .startTime(LocalTime.of(14, 30))
                    .screenFormat("2D")
                    .status("open")
                    .build());

            showtimeRepository.save(Showtime.builder()
                    .movie(kungfu)
                    .cinemaRoom(room2D_2)
                    .showDate(date)
                    .startTime(LocalTime.of(17, 30))
                    .screenFormat("2D")
                    .status("open")
                    .build());
        }

        log.info("Khởi tạo suất chiếu hoàn tất!");
    }

    private void seedFnbItems() {
        if (fnbItemRepository.count() > 0) {
            log.info("Dữ liệu bắp nước đã tồn tại.");
            return;
        }

        log.info("Khởi tạo danh mục bắp nước & combo F&B...");

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Bắp Rang Bơ Truyền Thống (L)")
                .category("popcorn")
                .description("Bắp rang thơm béo vị bơ ngọt tự nhiên cỡ lớn")
                .imageUrl("https://images.unsplash.com/photo-1578849278619-e73505e9610f")
                .basePrice(new BigDecimal("49000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Bắp Phô Mai Thượng Hạng (L)")
                .category("popcorn")
                .description("Bắp giòn phủ lớp bột phô mai hảo hạng đậm đà")
                .imageUrl("https://images.unsplash.com/photo-1585647347483-22b66260dfff")
                .basePrice(new BigDecimal("55000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Bắp Caramel Cháy (L)")
                .category("popcorn")
                .description("Bắp caramel giòn rụm thơm lừng ngọt ngào")
                .imageUrl("https://images.unsplash.com/photo-1505686994434-e3cc5abf1330")
                .basePrice(new BigDecimal("55000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Coca-Cola Tươi 32oz")
                .category("drink")
                .description("Nước ngọt Coca-Cola tươi mát lạnh giải khát")
                .imageUrl("https://images.unsplash.com/photo-1622483767028-3f66f32aef97")
                .basePrice(new BigDecimal("35000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Sprite Chanh Tươi 32oz")
                .category("drink")
                .description("Nước ngọt vị chanh Sprite sảng khoái mát lạnh")
                .imageUrl("https://images.unsplash.com/photo-1551024709-8f23befc6f87")
                .basePrice(new BigDecimal("35000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Combo Solo (1 Bắp L + 1 Nước 32oz)")
                .category("combo")
                .description("Tiết kiệm 15% - Thích hợp cho 1 người xem phim")
                .imageUrl("https://images.unsplash.com/photo-1572177812156-58036aae439c")
                .basePrice(new BigDecimal("79000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Combo Couple (1 Bắp L + 2 Nước 32oz)")
                .category("combo")
                .description("Tiết kiệm 20% - Dành cho các cặp đôi hẹn hò rạp chiếu")
                .imageUrl("https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c")
                .basePrice(new BigDecimal("109000.00"))
                .isActive(true)
                .build());

        fnbItemRepository.save(FnbItem.builder()
                .itemName("Combo Family (2 Bắp L + 4 Nước 22oz)")
                .category("combo")
                .description("Tiết kiệm 25% - Hoàn hảo cho gia đình hoặc nhóm bạn 4 người")
                .imageUrl("https://images.unsplash.com/photo-1489599849927-2ee91cede3ba")
                .basePrice(new BigDecimal("189000.00"))
                .isActive(true)
                .build());

        log.info("Khởi tạo danh mục bắp nước hoàn tất!");
    }

    private void seedSampleBookingsAndTickets() {
        if (ticketRepository.count() > 0) return;

        List<Showtime> showtimes = showtimeRepository.findAll();
        List<User> users = userRepository.findAll();
        if (showtimes.isEmpty() || users.isEmpty()) return;

        Showtime firstShowtime = showtimes.get(0);
        User customer = users.stream().filter(u -> "CUSTOMER".equals(u.getRole())).findFirst().orElse(users.get(0));

        List<Seat> roomSeats = seatRepository.findByCinemaRoomIdOrderByRowIndexAscColIndexAsc(firstShowtime.getCinemaRoom().getId());
        if (roomSeats.size() < 2) return;

        Seat seat1 = roomSeats.get(14); // Ví dụ ghế B05
        Seat seat2 = roomSeats.get(15); // Ví dụ ghế B06

        Booking booking = bookingRepository.save(Booking.builder()
                .bookingCode("BK" + System.currentTimeMillis())
                .user(customer)
                .showtime(firstShowtime)
                .contactEmail(customer.getEmail())
                .contactPhone(customer.getPhone())
                .paymentMethod("VNPAY")
                .paymentStatus("paid")
                .subtotal(new BigDecimal("180000.00"))
                .discountAmount(BigDecimal.ZERO)
                .totalAmount(new BigDecimal("180000.00"))
                .status("confirmed")
                .build());

        ticketRepository.save(Ticket.builder()
                .booking(booking)
                .showtime(firstShowtime)
                .seat(seat1)
                .seatCode(seat1.getSeatNumber())
                .seatType(seat1.getSeatType())
                .finalPrice(new BigDecimal("90000.00"))
                .ticketCode("TK-" + seat1.getSeatNumber())
                .checkinStatus("unchecked")
                .build());

        ticketRepository.save(Ticket.builder()
                .booking(booking)
                .showtime(firstShowtime)
                .seat(seat2)
                .seatCode(seat2.getSeatNumber())
                .seatType(seat2.getSeatType())
                .finalPrice(new BigDecimal("90000.00"))
                .ticketCode("TK-" + seat2.getSeatNumber())
                .checkinStatus("unchecked")
                .build());

        log.info("Đã tạo mẫu 1 đơn đặt vé và 2 ghế đã đặt cho suất chiếu #{} để kiểm thử giao diện sơ đồ ghế!", firstShowtime.getId());
    }
}
