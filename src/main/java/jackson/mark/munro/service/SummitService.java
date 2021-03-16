package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Service that provides summit information.
 */

public interface SummitService {

    Collection<Summit> listAll();
}
