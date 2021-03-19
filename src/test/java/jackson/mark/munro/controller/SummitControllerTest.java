package jackson.mark.munro.controller;

import jackson.mark.munro.exception.ValidationException;
import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.service.SummitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummitControllerTest {

    private static final Summit MUNRO_1 = Summit.builder()
            .name("Ben Mark").heightInMetres(1100)
            .summitCategory(MUNRO)
            .gridReference("NN773308")
            .build();

    private static final Summit MUNRO_TOP_1 = Summit.builder()
            .name("Little Mark").heightInMetres(975.25)
            .summitCategory(SummitCategory.MUNRO_TOP)
            .gridReference("NN119965")
            .build();

    private static final List<Summit> SUMMITS = List.of(MUNRO_1, MUNRO_TOP_1);

    private static final ValidationException VALIDATION_EXCEPTION =
            new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY, "An error message");

    @Mock
    private SummitService summitService;
    @Mock
    private QueryParamValidator paramValidator;
    @Mock
    private SortParamParser sortParamParser;

    @InjectMocks
    private SummitController summitController;

    @Test
    void find_shouldReturnSummitsFromService_whenLimitZeroAndSortAndAllFiltersAreNull() {
        when(summitService.find(0, null,null, null, null)).thenReturn(SUMMITS);

        Collection<Summit> result = summitController.find(0, null, null, null, null);

        assertThat("Did not return all summits.", result, is(SUMMITS));
    }

    @Test
    void find_shouldReturnSummitsFromService_whenNonZeroLimitAndValidSortAndValidFiltersAreProvided() {
        when(summitService.find(7, null, MUNRO, 950.51, 1400.0)).thenReturn(SUMMITS);

        Collection<Summit> result = summitController.find(7,"+height", MUNRO.toString(), 950.51, 1400.0);

        assertThat("Did not return all summits.", result, is(SUMMITS));
    }

    @Test
    void find_shouldThrowResponseStatusException_whenQueryParamValidatorThrowsException() {
        doThrow(VALIDATION_EXCEPTION).when(paramValidator).validate(7, "+height", MUNRO.toString(), 950.51, 1400.0);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> summitController.find(7
                , "+height", MUNRO.toString(), 950.51, 1400.0));

        assertThat(exception.getStatus(), is(VALIDATION_EXCEPTION.getHttpStatus()));
        assertThat(exception.getReason(), is(VALIDATION_EXCEPTION.getMessage()));
    }

    @Test
    void find_shouldReturnSummitsFromService_whenEITHERcategoryAndNonZeroLimitAndValidSortAndValidFiltersAreProvided() {
        when(summitService.find(7, null, null, 950.51, 1400.0)).thenReturn(SUMMITS);

        Collection<Summit> result = summitController.find(7,"+height", "EITHER", 950.51, 1400.0);

        assertThat("Did not return all summits.", result, is(SUMMITS));
    }
}