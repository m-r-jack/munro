package jackson.mark.munro.data.mapper;

import jackson.mark.munro.exception.MappingException;
import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import org.springframework.stereotype.Component;

import static jackson.mark.munro.model.SummitCategory.MUNRO_TOP;


/**
 * Creates a Summit from an array of Strings representing a row from a summit data csv file.
 */
@Component
public class SummitCsvRowDataMapper implements SummitDataMapper {

    private static final int NAME_FIELD_COLUMN = 5;
    private static final int HEIGHT_IN_METRES_FIELD_COLUMN = 9;
    private static final int GRIDREF_FIELD_COLUMN = 13;
    private static final int CATEGORY_FIELD_COLUMN = 27;

    private static final String MUNRO_CATEGORY = "MUN";
    private static final String MUNRO_TOP_CATEGORY = "TOP";

    @Override
    public Summit map(String[] data) {
        return Summit.builder().name(getName(data[NAME_FIELD_COLUMN]))
                .heightInMetres(getHeight(data[HEIGHT_IN_METRES_FIELD_COLUMN]))
                .summitCategory(getCategory(data[CATEGORY_FIELD_COLUMN]))
                .gridReference(getGridRef(data[GRIDREF_FIELD_COLUMN])).build();
    }

    private String getName(String nameField) {
        return nameField;
    }

    private int getHeight(String heightField) {
        try {
            return Integer.parseInt(heightField);
        } catch (NumberFormatException ex) {
            throw new MappingException(String.format("Could not map height in metres field [%s] in column %d to an " +
                            "integer",
                    heightField, HEIGHT_IN_METRES_FIELD_COLUMN));
        }
    }

    private SummitCategory getCategory(String categoryField) {
        if (categoryField.equals(MUNRO_CATEGORY)) {
            return SummitCategory.MUNRO;
        } else if (categoryField.equals(MUNRO_TOP_CATEGORY)) {
            return MUNRO_TOP;
        } else {
            throw new MappingException(String.format("Could not map category field [%s] in column %d to a " +
                            "SummitCategory",
                    categoryField, CATEGORY_FIELD_COLUMN));
        }
    }

    private String getGridRef(String gridRef) {
        return gridRef;
    }
}
