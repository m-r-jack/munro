package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;

import java.util.Collection;

/**
 * Service that provides summit information.
 */

public interface SummitService {

    /**
     * Returns collection of all summits
     * @return all summits
     */
    Collection<Summit> getAll();


    /**
     * Returns a collection of summits filtered category and height
     * @param category returned summits will only contain summits of this category
     * @param minHeightInMetres returned summits will only include summits greater than or equal to this height
     * @param maxHeightInMetres returned summits will only include summits less than or equal to this height
     * @return A collection of summits filtered by the given parameter values
     */
    Collection<Summit> find(SummitCategory category, Integer minHeightInMetres, Integer maxHeightInMetres);

}
