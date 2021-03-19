package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static jackson.mark.munro.controller.SummitController.EITHER_CATEGORY;
import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static jackson.mark.munro.model.SummitCategory.MUNRO_TOP;

@Slf4j
@Component
public class QueryParamValidator {

    public void validate(int limit, String sort, String category, Double minHeightInMetres,
                              Double maxHeightInMetres) {
        validateLimit(limit);
        validateSort(sort);
        validateCategory(category);
        validateHeightParams(minHeightInMetres, maxHeightInMetres);
    }

    private void validateLimit(int limit) {
        if (limit < 0) {
            log.debug("Invalid request as limit [{}] is negative", limit);
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY, "limit must not be negative.");
        }
    }

    private void validateSort(String sort) {
        if (sort != null && (sort.isEmpty() ||
                !Arrays.stream(sort.split(",")).allMatch(SortParamParser.COMPARATORS_BY_SORT_PARAM::containsKey))) {
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("%s is not a valid sort param. sort must contain a comma-separated list of any of %s",
                                    sort,
                            SortParamParser.COMPARATORS_BY_SORT_PARAM.keySet().stream().map(Object::toString)
                            .collect(Collectors.joining(", "))));
        }
    }

    private void validateCategory(String category) {
        if (!(category == null || category.equals(EITHER_CATEGORY)
                || category.equals(MUNRO.toString()) || category.equals(MUNRO_TOP.toString()))
        ) {
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("%s is not a valid category." +
                    " category must be one of %s, %s or %s", category, MUNRO, MUNRO_TOP, EITHER_CATEGORY));
        }
    }

    private void validateHeightParams(Double minHeightInMetres, Double maxHeightInMetres) {
        if (minHeightInMetres != null && maxHeightInMetres != null && minHeightInMetres > maxHeightInMetres) {
            log.debug("Invalid request as minHeightInMetres [{}] is greater than maxHeightInMetres [{}]",
                    maxHeightInMetres, maxHeightInMetres);
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "min-height must be less than or equal to max-height.");
        }
    }
}
