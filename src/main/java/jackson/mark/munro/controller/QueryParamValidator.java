package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import jackson.mark.munro.model.SummitCategory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class QueryParamValidator {

    public void validate(int limit, SummitCategory category, Integer minHeightInMetres,
                              Integer maxHeightInMetres) {
        if (minHeightInMetres > maxHeightInMetres) {
            throw  new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "min-height must be less than or equal to max-height.");
        }

        if (limit < 0) {
            throw  new ValidationException(HttpStatus.BAD_REQUEST, "limit must not be negative.");
        }
    }
}
