package bean;

import bean.enumeration.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Bill {

    @Id
    private Long id;
    private Double ammount;
    private State state;

}
