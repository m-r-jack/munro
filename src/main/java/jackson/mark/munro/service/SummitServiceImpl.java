package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.store.SummitStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SummitServiceImpl implements SummitService {

    private SummitStore summitStore;

    @Autowired
    public SummitServiceImpl(SummitStore summitStore) {
        this.summitStore = summitStore;
    }

    @Override
    public Collection<Summit> listAll() {
        return summitStore.getAll();
    }
}
