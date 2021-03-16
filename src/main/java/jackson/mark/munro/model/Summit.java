package jackson.mark.munro.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class Summit {
    @EqualsAndHashCode.Include
    private String name;
    private int heightInMetres;
    private SummitCategory summitCategory;
    private String gridReference;
}
