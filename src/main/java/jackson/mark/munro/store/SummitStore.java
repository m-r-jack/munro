package jackson.mark.munro.store;

import jackson.mark.munro.model.Summit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SummitStore {

    private List<Summit> summits = new ArrayList<>();

    public void setSummits(List<Summit> summits) {
        this.summits = summits;
    }

    public List<Summit> getAll() {
        return summits;
    }
}
