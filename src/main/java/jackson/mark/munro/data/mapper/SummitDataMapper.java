package jackson.mark.munro.data.mapper;

import jackson.mark.munro.model.Summit;

/**
* Creates a Summit from an array of Strings
 */
public interface SummitDataMapper {
    /**
     *
     * @param data An array of strings containing summit data.
     * @return a Summit object built from the data.
     */
    Summit map(String[] data);
}
