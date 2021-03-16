package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SummitServiceImpl implements SummitService {

    @Override
    public Collection<Summit> listAll() {
        // TODO add proper implementation
        return null;
    }
}
