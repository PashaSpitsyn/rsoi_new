package rsoi.bean;

import bean.enumeration.Color;
import bean.enumeration.FrameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarOrder {

    private Long id;
    private FrameType type;
    private Color color;

}
