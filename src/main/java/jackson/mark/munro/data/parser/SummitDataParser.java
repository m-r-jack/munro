package jackson.mark.munro.data.parser;

import jackson.mark.munro.model.Summit;

import java.util.List;

/**
 * Creates a List of summits using the data from the specied data source.
 */
public interface SummitDataParser {

    /**
     * Create a List of summits using the data from the specied data source.
     * @param dataSource A string represning a datasource.
     * @return A list of summits.
     */
    List<Summit> parseSummitData(String dataSource);
}
