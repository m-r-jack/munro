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
                MUNRO, 71, 70));

        assertThat(exception.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(exception.getMessage(), is("min-height must be less than or equal to max-height."));
    }

    @Test
    void shouldThrowValidationException_whenLimitIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate(-1,
                MUNRO, 10, 20));

        assertThat(exception.getHttpStatus(), is(HttpStatus.BAD_REQUEST));
        assertThat(exception.getMessage(), is("limit must not be negative."));
    }

    @Test
    void shouldNotThrowException_whenLimitIsZero_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(0, MUNRO, 10, 20));
    }

    @Test
    void shouldNotThrowException_whenLimitIsGreaterThanZero_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225, MUNRO, 10, 20));
    }

    @Test
    void shouldNotThrowException_whenMinHeightLessThanMaxHeight_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225, MUNRO, 999, 1234));
    }

    @Test
    void shouldNotThrowException_whenMinHeightEqualsMaxHeight_AndOtherParametersAreValid() {
        assertDoesNotThrow( () -> validator.validate(1225, MUNRO, 999, 999));
    }
}