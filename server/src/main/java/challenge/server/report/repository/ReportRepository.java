package challenge.server.report.repository;

import challenge.server.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 사용자 하루 신고 조회
    List<Report> findByReporterAndCreatedAtIsGreaterThanEqual(Long reporterUserId, LocalDateTime localDateTime);

    // 사용자 누적 신고 조회
    List<Report> findByReportedUserId(Long reportedUserId);

    // 동일 사용자 동일 게시물 신고 조회
    Optional<Report> findByReporterAndPostIdAndPostType(Long reporterUserId, Long postId, Report.PostType postType);

    // 게시물 누적 신고 조회
    // List<Report> findByPostIdAndPostType(Long postId, String postType);
}
