package challenge.server.category.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.habit.entity.Habit;
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
    private String type;
//    private Category.Type type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Habit> habits = new ArrayList<>();
    /*
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
     */
}
