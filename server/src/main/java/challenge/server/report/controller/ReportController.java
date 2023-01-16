package challenge.server.report.controller;

import challenge.server.report.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

//    TODO 전체 신고 조회 기능 관리자 페이지 구현시 필요.
//    @ApiOperation(value = "모든 신고 조회", notes = "관리자가 모든 신고 목록을 조회합니다.")
//    @GetMapping
//    public ResponseEntity getReports() {
//        List<ReportDto.Response> responses = List.of(createHabitReportDto(),createAuthReportDto(),createReviewReportDto());
//        return new ResponseEntity(responses, HttpStatus.OK);
//    }
//
//    public ReportDto.Response createHabitReportDto() {
//        return ReportDto.Response.builder().reportId(1L)
//                .postType("Habit").postId(1L)
//                .reporter(1L).reportType("ABUSE")
//                .build();
//    }
//
//    public ReportDto.Response createReviewReportDto() {
//        return ReportDto.Response.builder().reportId(1L)
//                .postType("Review").postId(1L)
//                .reporter(1L).reportType("ABUSE")
//                .build();
//    }
//
//    public ReportDto.Response createAuthReportDto() {
//        return ReportDto.Response.builder().reportId(1L)
//                .postType("Auth").postId(1L)
//                .reporter(1L).reportType("ABUSE")
//                .build();
//    }

}
