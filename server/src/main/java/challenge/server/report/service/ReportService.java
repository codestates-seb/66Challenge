package challenge.server.report.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.report.entity.Report;
import challenge.server.report.repository.ReportRepository;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
//  private final CustomBeanUtils<Report> beanUtils;

    @Transactional
    public Report createReport(Report report) {

        // 사용자 하루 신고 내역 조회 - reportingUserId로 조회, 3회 이상이면 신고 등록 거절
        countDailyReports(report.getReporter());

        // 동일 사용자 동일 게시물 신고 내역 조회 - 중복 신고 방지
        findExistsReport(report.getReporter(), report.getPostId(), report.getPostType());

        // 사용자 누적 신고당한 횟수 조회 - reportedUserId로 조회, 5회 누적 시 유저 상태 중지
        findByReportedUserId(report.getReported().getUserId());

        // 게시물 누적 신고당한 조회 - postId와 postType으로 조회, n회 이상이면 조회 비활성화
        // findByPostIdAndPostType(report.getPostId(),report.getPostType());

        return reportRepository.save(report);
    }

    public void countDailyReports(Long reporterUserId) {
        List<Report> reports = reportRepository.findByReporterAndCreatedAtIsGreaterThanEqual(reporterUserId,
                LocalDateTime.now().toLocalDate().atTime(LocalTime.MIN)); // 오늘 날짜의 0시
        if(reports.size()>=3) throw new BusinessLogicException(ExceptionCode.TOO_MANY_REPORTS);
    }

    public void findExistsReport(Long reporterUserId, Long postId, Report.PostType postType) {
        reportRepository.findByReporterAndPostIdAndPostType(reporterUserId, postId, postType)
                .ifPresent(a -> {
                    throw new BusinessLogicException(ExceptionCode.DUPLICATED_REPORT);
                });
    }

    public void findByReportedUserId(Long reportedUserId) {
        List<Report> reports = reportRepository.findByReportedUserId(reportedUserId);
        if(reports.size() >= 4) { // 이번에 신고하면 총 누적 5회가 되므로 banned으로 상태를 바꾼다.
            User findUser = userRepository.findById(reportedUserId).get();
            findUser.setStatus(User.Status.BANNED);
            userRepository.save(findUser);
        }
    }

//    게시물 누적 신고 n회 이상 시 조회 불가능으로 변경 기능
//    public void findByPostIdAndPostType(Long postId, String postType) {
//        List<Report> reports = reportRepository.findByPostIdAndPostType(postId, postType);
//        reports.size() > n 이면, 해당 게시물 상태 조회 불가능으로 변경
//    }

    public List<Report> findAll(int page, int size) {
        return reportRepository.findAll(PageRequest.of(page,size, Sort.by("reportId").descending())).getContent();
    }


//    신고 상세 조회
//    public Report findReport(Long reportId) {
//        return findVerifiedReport(reportId);
//    }

//    신고 수정
//    public Report updateReport(Report report) {
//        Report findReport = findVerifiedReport(report.getReportId());
//        Report updatingReport = beanUtils.copyNonNullProperties(report, findReport);
//        return reportRepository.save(updatingReport);
//    }

//    신고 삭제
//    public void deleteReport(Long reportId) {
//        Report report = findVerifiedReport(reportId);
//        reportRepository.delete(report);
//    }

//    public Report findVerifiedReport(Long reportId) {
//        return reportRepository.findById(reportId)
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
//    }
}

