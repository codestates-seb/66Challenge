package challenge.server.review.repository;

public interface ReviewCustomRepository {
    Double findAverage(Long habitId);
}
