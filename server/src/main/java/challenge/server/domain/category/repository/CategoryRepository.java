package challenge.server.domain.category.repository;

import challenge.server.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByType(String type);
}
