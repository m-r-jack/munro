package jackson.mark.munro.data.parser;

import jackson.mark.munro.data.mapper.SummitDataMapper;
import jackson.mark.munro.exception.DataParserException;
import jackson.mark.munro.exception.MappingException;
import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummitCsvDataParserTest {

    private static final String[] SUMMIT_DATA_1 = {"1", "", "Ben Chonzie", "not used", "931", "MUN", "NN773308"};
    private static final String[] SUMMIT_DATA_2 = {"2", "", "Ben Mark", "not used", "999", "MUN_TOP", "NW704407"};

    private static final Summit SUMMIT_1 = Summit.builder()
            .name("Ben Chonzie").heightInMetres(931)
            .summitCategory(SummitCategory.MUNRO)
            .gridReference("NN773308")
            .build();

    private static final Summit SUMMIT_2 = Summit.builder()
            .name("Ben Mark").heightInMetres(999)
            .summitCategory(SummitCategory.MUNRO_TOP)
            .gridReference("NW704407")
            .build();

    private static final String SUMMIT_DATA_CSV = "src/test/resources/summit_data.csv";
    private static final String INVALID_CSV_FILE = "src/test/resources/summit_data_invalid_format.csv";

    @Mock
    private SummitDataMapper mapper;

    @InjectMocks
    private SummitCsvDataParser parser;

    @Test
    void shouldMapSummitsFromCsv() {
        when(mapper.map(SUMMIT_DATA_1)).thenReturn(SUMMIT_1);
        when(mapper.map(SUMMIT_DATA_2)).thenReturn(SUMMIT_2);

        List<Summit> summmits = parser.parseSummitData(SUMMIT_DATA_CSV);

        assertThat(summmits.size(), is(2));
        assertThat(summmits.get(0), is(SUMMIT_1));
        assertThat(summmits.get(1), is(SUMMIT_2));
    }

    @Test
    void shouldExcludeSummit_whenMapperThrowsException() {
        when(mapper.map(SUMMIT_DATA_1)).thenThrow(new MappingException("A mapping exception."));
        when(mapper.map(SUMMIT_DATA_2)).thenReturn(SUMMIT_2);

        List<Summit> summmits = parser.parseSummitData(SUMMIT_DATA_CSV);

        assertThat(summmits.size(), is(1));
        assertThat(summmits.get(0), is(SUMMIT_2));
    }

    @Test
    void shouldThrowDataParserException_whenCannotReadCsvFile() {
        assertThrows(DataParserException.class, () -> parser.parseSummitData("non_existent_file.csv"));
    }

    @Test
    void shouldThrowDataParserException_whenCsvFileIsInInvalidFormat() {
        assertThrows(DataParserException.class, () -> parser.parseSummitData(INVALID_CSV_FILE));
    }
}