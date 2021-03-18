package jackson.mark.munro.data.parser;

import jackson.mark.munro.model.Summit;

import java.util.List;

public interface SummitDataParser {

    List<Summit> parseSummitData(String dataSource);
}
