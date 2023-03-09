package challenge.server.domain.report.repository;

import challenge.server.domain.report.entity.Report;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.report.entity.QReport.report;

@Repository
@RequiredArgsConstructor
public class ReportCustomRepositoryImpl implements ReportCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Report> findAllNoOffset(Long lastReportId, int size) {
        return jpaQueryFactory
                .selectFrom(report)
                .where(ltReportId(lastReportId))
                .orderBy(report.reportId.desc())
                .limit(size)
                .fetch();
    }

    public BooleanExpression ltReportId(Long lastReportId) {
        if (lastReportId == null) {
            return null;
        }
        return report.reportId.lt(lastReportId);
    }
}
