package challenge.server.habit.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private Category.Type type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Habit> habits = new ArrayList<>();
    public enum Type {

        // 논의 후 추가 및 변경
        HEALTH(1),
        ECHO(2);

        @Getter
        private final int num;

        Type(int num) {
            this.num = num;
        }
    }
}
