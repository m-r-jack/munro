package jackson.mark.munro;

import jackson.mark.munro.data.parser.SummitDataParser;
import jackson.mark.munro.store.SummitStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Setup {

    private SummitDataParser summitDataParser;
    private SummitStore summitStore;
    private String summitDataFile;

    @Autowired
    public Setup(SummitDataParser summitDataParser, SummitStore summitStore, @Value("${summit.data.file}") String
            summitDataFile) {
        this.summitDataParser = summitDataParser;
        this.summitStore = summitStore;
        this.summitDataFile = summitDataFile;
    }

    @PostConstruct
    private void setupData() {
        log.info("Loading summit data from file [{}]", summitDataFile);
        summitStore.setSummits(summitDataParser.parseSummitData(summitDataFile));
        log.info("Finished loading summit data.");
    }
}
