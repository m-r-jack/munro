package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Comparator;

/**
 * Service that provides summit information.
 */

public interface SummitService {

    /**
     * Returns a collection of summits filtered by category and height
     * @param limit The number of results to be returned. No limit will be applied if the value is 0
     * @param comparator A comparator used to sort the collection returned
     * @param category returned summits will only include summits with the specified category. Can be  null, in which
     *                 case no category filtering will be applied.
     * @param minHeightInMetres returned summits will only include summits greater than or equal to this height. Can
     *                          be  null, in which case no minHeightInMetres filtering will be applied.
     * @param maxHeightInMetres returned summits will only include summits less than or equal to this height. Can be
     *                          null, in which case no minHeightInMetres filtering will be applied.
     * @return A collection of summits filtered by the given parameter values
     */
    Collection<Summit> find(int limit, @Nullable Comparator<Summit> comparator, @Nullable SummitCategory category,
                            @Nullable Integer minHeightInMetres, @Nullable Integer maxHeightInMetres);

}
