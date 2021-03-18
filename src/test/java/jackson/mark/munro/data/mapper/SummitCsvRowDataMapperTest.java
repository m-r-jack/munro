package jackson.mark.munro.data.mapper;

import jackson.mark.munro.exception.MappingException;
import jackson.mark.munro.model.Summit;
import org.junit.jupiter.api.Test;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static jackson.mark.munro.model.SummitCategory.MUNRO_TOP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SummitCsvRowDataMapperTest {

    private SummitCsvRowDataMapper mapper = new SummitCsvRowDataMapper();

    private static final String[] MUNRO_SUMMIT_FIELDS = new String[] {
            "1", "",
            "http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y",
            "http://www.geograph.org.uk/gridref/NN7732430857",
            "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1",
            "Ben Chonzie",
            "1", "01A", "1.1",
            "931",
            "3054", "51 52", "OL47W 368W 379W",
            "NN773308",
            "NN7732430857", "277324", "730857", "MUN1",	"MUN2",	"MUN3",	"MUN4",	"MUN5",	"MUN6",	"MUN7",	"MUN8",	"MUN9",	"MUN0",
            "MUN",
            "A comment"};

    private static final String[] MUNRO_TOP_SUMMIT_FIELDS = new String[] {
            "1", "",
            "http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y",
            "http://www.geograph.org.uk/gridref/NN7732430857",
            "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1",
            "Chonzie Top",
            "1", "01A", "1.1",
            "927",
            "3054", "51 52", "OL47W 368W 379W",
            "NN773304",
            "NN7732430857", "277324", "730857", "MUN1",	"MUN2",	"MUN3",	"MUN4",	"MUN5",	"MUN6",	"MUN7",	"MUN8",	"MUN9",	"MUN0",
            "TOP",
            "A comment"};


    private static final String[] SUMMIT_FIELDS_WITH_INVALID_CATEGORY = new String[] {
            "1", "",
            "http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y",
            "http://www.geograph.org.uk/gridref/NN7732430857",
            "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1",
            "Chonzie Top",
            "1", "01A", "1.1",
            "927",
            "3054", "51 52", "OL47W 368W 379W",
            "NN773304",
            "NN7732430857", "277324", "730857", "MUN1",	"MUN2",	"MUN3",	"MUN4",	"MUN5",	"MUN6",	"MUN7",	"MUN8",	"MUN9",	"MUN0",
            "INVALID CATEGORY",
            "A comment"};

    private static final String[] SUMMIT_FIELDS_WITH_INVALID_HEIGHT = new String[] {
            "1", "",
            "http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y",
            "http://www.geograph.org.uk/gridref/NN7732430857",
            "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1",
            "Chonzie Top",
            "1", "01A", "1.1",
            "",
            "3054", "51 52", "OL47W 368W 379W",
            "NN773304",
            "NN7732430857", "277324", "730857", "MUN1",	"MUN2",	"MUN3",	"MUN4",	"MUN5",	"MUN6",	"MUN7",	"MUN8",	"MUN9",	"MUN0",
            "MUN",
            "A comment"};


    @Test
    void shouldMapArrayOfFieldsToSummit_whenIsMunro() {
        Summit summit = mapper.map(MUNRO_SUMMIT_FIELDS);

        assertThat(summit.getName(), is("Ben Chonzie"));
        assertThat(summit.getHeightInMetres(), is(931));
        assertThat(summit.getSummitCategory(), is(MUNRO));
        assertThat(summit.getGridReference(), is("NN773308"));
    }

    @Test
    void shouldMapArrayOfFieldsToSummit_whenIsMunroTop() {
        Summit summit = mapper.map(MUNRO_TOP_SUMMIT_FIELDS);

        assertThat(summit.getName(), is("Chonzie Top"));
        assertThat(summit.getHeightInMetres(), is(927));
        assertThat(summit.getSummitCategory(), is(MUNRO_TOP));
        assertThat(summit.getGridReference(), is("NN773304"));
    }

    @Test
    void shouldThrowMappingException_whenIsNeitherMunroOrMunroTop() {

        MappingException exception = assertThrows(MappingException.class, () ->
                mapper.map(SUMMIT_FIELDS_WITH_INVALID_CATEGORY));

        assertThat(exception.getMessage(), is("Could not map category field [INVALID CATEGORY] in column 27 to a " +
                "SummitCategory"));
    }

    @Test
    void shouldThrowMappingException_whenHeightCannotBeParsedToInt() {

        MappingException exception = assertThrows(MappingException.class, () ->
                mapper.map(SUMMIT_FIELDS_WITH_INVALID_HEIGHT));

        assertThat(exception.getMessage(), is("Could not map height in metres field [] in column 9 to an integer"));
    }

}