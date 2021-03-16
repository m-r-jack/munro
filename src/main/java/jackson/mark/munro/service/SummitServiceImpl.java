package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.store.SummitStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SummitServiceImpl implements SummitService {

    private SummitStore summitStore;

    @Autowired
    public SummitServiceImpl(SummitStore summitStore) {
        this.summitStore = summitStore;
    }

    @Override
    public Collection<Summit> getAll() {
        log.debug("Getting all summits.");
        Collection<Summit> summits = summitStore.getAll();
        log.debug("Found {}", summits);
        return summits;
    }

    @Override
    public Collection<Summit> find(SummitCategory category, Integer minHeightInMetres, Integer maxHeightInMetres) {
       log.debug("Finding summits with category [{}], minHeightInMetres [{}] and maxHeightInMetres [{}]", category,
                minHeightInMetres, maxHeightInMetres);
        Collection<Summit> summits = summitStore.getAll().stream()
                .filter(categoryPredicate(category))
                .filter(minHeightPredicate(minHeightInMetres))
                .filter(maxHeightPredicate(maxHeightInMetres))
                .collect(Collectors.toList());
        log.debug("Found {}", summits);
        return summits;
    }

    private Predicate<Summit> categoryPredicate(SummitCategory category) {
        return s -> category == null || Objects.equals(s.getSummitCategory(), category);
    }

    private Predicate<Summit> minHeightPredicate(Integer minHeight) {
        return s -> (minHeight == null || s.getHeightInMetres() >= minHeight);
    }

    private Predicate<Summit> maxHeightPredicate(Integer maxHeight) {
        return s -> (maxHeight == null || s.getHeightInMetres() <= maxHeight);
    }
}