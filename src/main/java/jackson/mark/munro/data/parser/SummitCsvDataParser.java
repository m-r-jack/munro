package jackson.mark.munro.data.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jackson.mark.munro.data.mapper.SummitDataMapper;
import jackson.mark.munro.exception.DataParserException;
import jackson.mark.munro.exception.MappingException;
import jackson.mark.munro.model.Summit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SummitCsvDataParser implements SummitDataParser {

    private SummitDataMapper mapper;

    @Autowired
    public SummitCsvDataParser(SummitDataMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Summit> parseSummitData(String csvFile) {
        List<Summit> summits = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                try {
                    summits.add(buildSummit(row));
                } catch (MappingException ex) {
                    log.warn("Could not map row {} because {}. Skipping.", (Object) row, ex.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new DataParserException(String.format("CSV file [%s] not found.", csvFile), e);
        } catch (IOException e) {
            throw new DataParserException(String.format("Could not read CSV file [%s].", csvFile), e);
        } catch (CsvValidationException e) {
            throw new DataParserException(String.format("File [%s] is not in valid CSV format.", csvFile), e);
        }
        return summits;
    }

    private Summit buildSummit(String[] summitData) {
        return mapper.map(summitData);
    }
}
