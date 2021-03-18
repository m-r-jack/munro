package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.store.SummitStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class SummitServiceImpl implements SummitService {

    private SummitStore summitStore;

    @Autowired
    public SummitServiceImpl(SummitStore summitStore) {
        this.summitStore = summitStore;
    }

    @Override
    public Collection<Summit> find(int limit, Comparator<Summit> comparator, SummitCategory category,
                                   Integer minHeightInMetres, Integer maxHeightInMetres) {
        log.debug("Finding summits with limit [{}], category [{}], minHeightInMetres [{}] and maxHeightInMetres [{}]",
               limit, category, minHeightInMetres, maxHeightInMetres);
        if (limit == 0) {
            limit = Integer.MAX_VALUE;
        }
        Stream<Summit> filterdAndLimitedSummits =  getLimitedAndFilteredSummitStream(limit, category,
                minHeightInMetres, maxHeightInMetres);
        Collection<Summit> summits = comparator == null
                ? filterdAndLimitedSummits.collect(Collectors.toList())
                : filterdAndLimitedSummits.sorted(comparator).collect(Collectors.toList());
        log.debug("Found {}", summits);
        return summits;
    }

    private Stream<Summit> getLimitedAndFilteredSummitStream(int limit, SummitCategory category,
                                                             Integer minHeightInMetres, Integer maxHeightInMetres) {
        return summitStore.getAll().stream()
                .filter(categoryPredicate(category))
                .filter(minHeightPredicate(minHeightInMetres))
                .filter(maxHeightPredicate(maxHeightInMetres))
                .limit(limit);
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