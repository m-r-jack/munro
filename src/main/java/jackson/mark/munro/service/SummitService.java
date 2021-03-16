package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;

import java.util.Collection;

/**
 * Service that provides summit information.
 */

public interface SummitService {

    Collection<Summit> listAll();
}
