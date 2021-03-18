package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import jackson.mark.munro.model.SummitCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueryParamValidator {

    public void validate(int limit, String sort, SummitCategory category, Integer minHeightInMetres,
                              Integer maxHeightInMetres) {
        if (minHeightInMetres > maxHeightInMetres) {
            log.debug("Invalid request as minHeightInMetres [{}] is greater than maxHeightInMetres [{}]",
                    maxHeightInMetres, maxHeightInMetres);
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "min-height must be less than or equal to max-height.");
        }

        if (limit < 0) {
            log.debug("Invalid request as limit [{}] is negative", limit);
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY, "limit must not be negative.");
        }

        validateSort(sort);
    }

    private void validateSort(String sort) {
        if (sort != null && (sort.isEmpty() ||
                !Arrays.stream(sort.split(",")).allMatch(SortParamParser.COMPARATORS_BY_SORT_PARAM::containsKey))) {
            log.debug("Invalid request as sort [{}] is invalid", sort);
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "sort must contain a comma separated list of any of "
                            + SortParamParser.COMPARATORS_BY_SORT_PARAM.keySet().stream().map(Object::toString)
                            .collect(Collectors.joining(",")));
        }
    }
}
