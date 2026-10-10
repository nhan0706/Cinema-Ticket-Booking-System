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
import java.util.List;

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

    @Override
    @Transactional
    public void run(String... args) {
        if (genreRepository.count() > 0) {
            log.info("Dữ liệu mẫu đã tồn tại, bỏ qua DataSeeder.");
            return;
        }

        log.info("Bắt đầu khởi tạo dữ liệu mẫu cho hệ thống đặt vé xem phim...");

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

        Movie avatar3 = movieRepository.save(Movie.builder()
                .title("Avatar: Fire and Ash")
                .description("Chuyến phiêu lưu tiếp theo của Jake Sully và Neytiri khám phá bộ tộc lửa Ash People trên hành tinh Pandora.")
                .durationMinutes(190)
                .ageRating("C13")
                .language("Tiếng Anh - Phụ đề tiếng Việt")
                .director("James Cameron")
                .status("coming_soon")
                .releaseDate(LocalDate.of(2025, 12, 19))
                .endDate(LocalDate.of(2027, 2, 28))
                .posterUrl("https://images.unsplash.com/photo-1478760329108-5c3ed9d495a0")
                .trailerUrl("https://www.youtube.com/watch?v=example4")
                .build());

        movieGenreRepository.save(MovieGenre.builder().movie(avatar3).genre(sciFi).build());
        movieGenreRepository.save(MovieGenre.builder().movie(avatar3).genre(phieuLuu).build());

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

        // 6. Tài khoản mẫu (Admin & Customer)
        userRepository.save(User.builder()
                .fullName("Quản Trị Viên")
                .email("admin@cinema.com")
                .phone("0901234567")
                .passwordHash("$2a$10$dummyHashAdminPassword123") // placeholder hash
                .role("ADMIN")
                .loyaltyPoints(1000)
                .memberTier("platinum")
                .status("active")
                .build());

        userRepository.save(User.builder()
                .fullName("Nguyễn Văn A")
                .email("khachhang@gmail.com")
                .phone("0912345678")
                .passwordHash("$2a$10$dummyHashCustomerPassword123") // placeholder hash
                .role("CUSTOMER")
                .loyaltyPoints(50)
                .memberTier("bronze")
                .status("active")
                .build());

        log.info("Khởi tạo dữ liệu mẫu thành công!");
    }
}
