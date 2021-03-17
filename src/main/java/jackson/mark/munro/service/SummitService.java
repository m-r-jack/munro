package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * Service that provides summit information.
 */

public interface SummitService {

    /**
     * Returns a collection of summits filtered by category and height
     * @param category returned summits will only include summits with the specified category. Can be  null, in which
     *                 case no category filtering will be applied.
     * @param minHeightInMetres returned summits will only include summits greater than or equal to this height. Can
     *                          be  null, in which case no minHeightInMetres filtering will be applied.
     * @param maxHeightInMetres returned summits will only include summits less than or equal to this height. Can be
     *                          null, in which case no minHeightInMetres filtering will be applied.
     * @return A collection of summits filtered by the given parameter values
     */
    Collection<Summit> find(@Nullable SummitCategory category, @Nullable Integer minHeightInMetres,
                            @Nullable Integer maxHeightInMetres);

}
