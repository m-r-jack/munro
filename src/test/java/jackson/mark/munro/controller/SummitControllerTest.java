package jackson.mark.munro.controller;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.service.SummitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummitControllerTest {

    private static final Summit MUNRO_1 = Summit.builder()
            .name("Ben Mark").heightInMetres(4000)
            .summitCategory(MUNRO)
            .gridReference("NN773308")
            .build();

    private static final Summit MUNRO_TOP_1 = Summit.builder()
            .name("Little Mark").heightInMetres(3500)
            .summitCategory(SummitCategory.MUNRO_TOP)
            .gridReference("NN119965")
            .build();

    private static final List<Summit> SUMMITS = List.of(MUNRO_1, MUNRO_TOP_1);

    @Mock
    private SummitService summitService;

    @InjectMocks
    private SummitController summitController;

    @Test
    void find_shouldReturnSummitsFromService_whenLimitZeroAndAllFiltersAreNull() {
        when(summitService.find(0,null, null, null)).thenReturn(SUMMITS);

        Collection<Summit> result = summitController.find(0,null, null, null);

        assertThat("Did not return all summits.", result, is(SUMMITS));
    }

    @Test
    void find_shouldReturnSummitsFromService_whenLimitAndFiltersAreProvided() {
        when(summitService.find(7, MUNRO, 1000, 1400)).thenReturn(SUMMITS);

        Collection<Summit> result = summitController.find(7,MUNRO, 1000, 1400);

        assertThat("Did not return all summits.", result, is(SUMMITS));
    }
}