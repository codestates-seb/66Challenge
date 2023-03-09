package challenge.server.domain.report.mapper;

import challenge.server.domain.report.dto.ReportDto;
import challenge.server.domain.report.entity.Report;
import challenge.server.domain.report.entity.Report.PostType;
import challenge.server.domain.report.entity.Report.ReportType;
import challenge.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportMapperImpl {

    private final UserService userService;

    public Report reportPostDtoToReport(ReportDto.Post dto) {
        if ( dto == null ) {
            return null;
        }

        Report report = Report.builder()
                .postId(dto.getPostId())
                .postType(PostType.valueOf(dto.getPostType()))
                .reportType(ReportType.valueOf(dto.getReportType()))
                .reporter(dto.getReporterUserId())
                .reported(userService.findUser(dto.getReportedUserId()))
                .build();

        return report;
    }

    public ReportDto.Response reportToReportResponseDto(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportDto.Response reportResponseDto = ReportDto.Response.builder()
                .reportId(report.getReportId())
                .postId(report.getPostId())
                .postType(report.getPostType().name())
                .reportType(report.getReportType().name())
                .reporterUserId(report.getReporter())
                .reportedUserId(report.getReported().getUserId())
                .build();

        return reportResponseDto;
    }
}
