package challenge.server.report.controller;

import challenge.server.report.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {
    // 신고 수정, 삭제 기능 X
    @GetMapping
    public ResponseEntity getReports() {
        List<ReportDto.Response> responses = List.of(createReportDto(),createReportDto(),createReportDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    public ReportDto.Response createReportDto() {
        return ReportDto.Response.builder()
                .reportId(1L).reporter("신고자")
                .postType("Review").postId(2L)
                .type("ADVERTISEMENT").build();
    }

}
