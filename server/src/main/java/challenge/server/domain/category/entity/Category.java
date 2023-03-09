package challenge.server.domain.category.entity;

import challenge.server.domain.BaseTimeEntity;
import challenge.server.domain.habit.entity.Habit;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Habit> habits = new ArrayList<>();

    public void changeType(String type) {
        this.type = type;
    }
}
