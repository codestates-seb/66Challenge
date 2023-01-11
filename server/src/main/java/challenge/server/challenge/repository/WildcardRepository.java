package challenge.server.challenge.repository;

import challenge.server.challenge.entity.Wildcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WildcardRepository extends JpaRepository<Wildcard, Long> {
}
