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
 * height_asc : sort by heightInMeters ascending
 * height_desc : sort by heightInMeters descending
 * name_asc : sort by name ascending
 * name_desc : sort by name descending
 *
 * For example "name_desc,height_asc" specifies that summits should be sorted by name descending, then by height
 * ascending.
 */
@Component
@Slf4j
public class SortParamParser {
    protected static final String HEIGHT_DESC ="height_desc";
    protected static final String HEIGHT_ASC ="height_asc";
    protected static final String NAME_DESC ="name_desc";
    protected static final String NAME_ASC ="name_asc";

    protected static final Map<String, Comparator<Summit>> COMPARATORS_BY_SORT_PARAM = Map.of(
            HEIGHT_DESC, Comparator.comparing(Summit::getHeightInMetres).reversed(),
            HEIGHT_ASC, Comparator.comparing(Summit::getHeightInMetres),
            NAME_DESC, Comparator.comparing(Summit::getName).reversed(),
            NAME_ASC, Comparator.comparing(Summit::getName)
    );

    /**
     * returns a comparator that will sort by the fields specified in the given sort parameter
     * @param sort a comma separated list of strings that specify sorts. Each string is in the format
     *             [height|name]_[asc|desc] where height represents a sort by maxHeightInMetres, name represents a
     *             sort my name, asc represents an ascending sort and desc represents a descending sort.
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