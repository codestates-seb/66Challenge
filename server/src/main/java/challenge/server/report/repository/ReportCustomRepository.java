package challenge.server.report.repository;

import challenge.server.report.entity.Report;

import java.util.List;

public interface ReportCustomRepository {
    List<Report> findAllNoOffset(Long lastReportId, int size);
}
