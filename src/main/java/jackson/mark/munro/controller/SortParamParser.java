package jackson.mark.munro.controller;

import jackson.mark.munro.model.Summit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

/**
 * Parses a comma-separated list of Summit fields to sort by to produce a comparator that
 * will sort a collection of summits by each specified field in order.
 * The valid sort fields in the comma-separated list are as follows:
 * +height : sort by heightInMeters ascending
 * -height : sort by heightInMeters descending
 * +name : sort by name ascending
 * -name : sort by name descending
 *
 * For example "-name,+height" specifies that Summit's should be sorted by name descending, then by height
 * ascending.
 */
@Component
@Slf4j
public class SortParamParser {

    protected static final Map<String, Comparator<Summit>> COMPARATORS_BY_SORT_PARAM = Map.of(
            "-height", Comparator.comparing(Summit::getHeightInMetres).reversed(),
            "+height", Comparator.comparing(Summit::getHeightInMetres),
            "-name", Comparator.comparing(Summit::getName).reversed(),
            "+name", Comparator.comparing(Summit::getName)
    );

    /**
     * returns a comparator that will sort by the fields specified in the given sort parameter
     * @param sort a comma separated list of strings that specify the type of sort (ascending or descending) and the
     *             field to sort by. Each string is in the format [+|-][height|name] where + represents a descending
     *             sort, - represents a descending sort, height represents a sort by maxHeightInMetres and name
     *             represents a sort my name.
     * @return a Comparator that will sort summits by the fields specified in order.
     */
    public Comparator<Summit> parseSortParam(String sort) {
        if (sort == null) {
            return null;
        }
        String[] sortFields = sort.split(",");
        Comparator<Summit> summitComparator = null;
        for (String sortField : sortFields) {
            if (COMPARATORS_BY_SORT_PARAM.containsKey(sortField)) {
                log.debug("Adding sort comparator for {}", sortField);
                if (summitComparator == null) {
                    summitComparator = COMPARATORS_BY_SORT_PARAM.get(sortField);
                } else {
                    summitComparator = summitComparator.thenComparing(COMPARATORS_BY_SORT_PARAM.get(sortField));
                }
            }
        }

        return summitComparator;
    }
}