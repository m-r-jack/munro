package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryParamValidatorTest {

    QueryParamValidator validator = new QueryParamValidator();

    @Test
    void shouldThrowValidationException_whenMinHeightIsGreaterThanMaxHeight() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(0,
                "name_asc", MUNRO.toString(), 70.11, 70.0));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(exception.getMessage(), is("min-height must be less than or equal to max-height."));
    }

    @Test
    void shouldThrowValidationException_whenLimitIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(-1,
                "name_asc", MUNRO.toString(), 10.0, 20.0));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(exception.getMessage(), is("limit must not be negative."));
    }

    @Test
    void shouldNotThrowException_whenLimitIsZero_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(0, "name_asc", MUNRO.toString(), 10.0, 20.0));
    }

    @Test
    void shouldNotThrowException_whenLimitIsGreaterThanZero_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225, "name_asc", MUNRO.toString(), 10.0, 20.0));
    }

    @Test
    void shouldNotThrowException_whenMinHeightLessThanMaxHeight_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225, "name_asc", MUNRO.toString(), 999.0, 1234.0));
    }

    @Test
    void shouldNotThrowException_whenMinHeightEqualsMaxHeight_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225,"name_asc,height_desc", MUNRO.toString(), 999.05, 999.05));
    }

    @Test
    void shouldThrowException_whenSortIsEmpty_AndOtherParametersAreValid() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(7,
                "", MUNRO.toString(), 10.0, 20.0));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    void shouldThrowException_whenSortContainsAnInvalidValue_AndOtherParametersAreValid() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(7,
                "name_asc,invalid,height_desc", MUNRO.toString(), 10.0, 20.0));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    void shouldThrowException_whenCategoryContainsAnInvalidValue_AndOtherParametersAreValid() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(7,
                "name_asc", "invalid_category", 10.0, 20.0));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    void shouldNotThrowException_whenCategoryContainsMUNRO_AndOtherParametersAreValid() {
        assertDoesNotThrow(() ->  validator.validate(7,
                "name_asc", "MUNRO", 10.0, 20.0));
    }

    @Test
    void shouldNotThrowException_whenCategoryContainsMUNRO_TOP_AndOtherParametersAreValid() {
        assertDoesNotThrow(() ->  validator.validate(7,
                "name_asc", "MUNRO_TOP", 10.0, 20.0));
    }

    @Test
    void shouldNotThrowException_whenCategoryContainsEITHER_AndOtherParametersAreValid() {
        assertDoesNotThrow(() ->  validator.validate(7,
                "name_asc", "EITHER", 10.0, 20.0));
    }
}