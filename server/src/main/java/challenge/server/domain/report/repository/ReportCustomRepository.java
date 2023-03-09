package challenge.server.domain.report.repository;

import challenge.server.domain.report.entity.Report;

import java.util.List;

public interface ReportCustomRepository {
    List<Report> findAllNoOffset(Long lastReportId, int size);
}
