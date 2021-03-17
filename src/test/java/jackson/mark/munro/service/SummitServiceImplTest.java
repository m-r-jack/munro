package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.store.SummitStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static jackson.mark.munro.model.SummitCategory.MUNRO_TOP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummitServiceImplTest {

    private static final int NO_LIMIT = 0;
    private static final Summit MUNRO_1 = Summit.builder()
            .name("Ben Mark").heightInMetres(1000)
            .summitCategory(MUNRO)
            .gridReference("NN16697127")
            .build();

    private static final Summit MUNRO_2 = Summit.builder()
            .name("Ben Nevis").heightInMetres(1345)
            .summitCategory(MUNRO)
            .gridReference("NN16697127")
            .build();

    private static final Summit MUNRO_TOP_1 = Summit.builder()
            .name("Little Mark").heightInMetres(990)
            .summitCategory(MUNRO_TOP)
            .gridReference("NN119965")
            .build();

    private static final List<Summit> ALL_SUMMITS = List.of(MUNRO_1, MUNRO_2, MUNRO_TOP_1);
    private static final List<Summit> ALL_MUNROS = List.of(MUNRO_1, MUNRO_2);


    @Mock
    private SummitStore summitStore;

    @InjectMocks
    private SummitServiceImpl summitService;

    @Test
    void find_shouldReturnAllSummits_whenAllFilterParamsAreNull_AndNoLimitIsApplied() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(0, null, null, null);

        assertThat("Did not return all summits.", result, containsInAnyOrder(ALL_SUMMITS.toArray()));
    }

    @Test
    void find_shouldFilterBySummitCategory() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(NO_LIMIT, MUNRO, null, null);

        assertThat("Did not return all munros.", result, containsInAnyOrder(ALL_MUNROS.toArray()));
    }

    @Test
    void find_shouldFilterByMaxHeight() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(NO_LIMIT, null, null, 1000);

        assertThat("Did not return all summits equal to or below max height", result, containsInAnyOrder(MUNRO_1, MUNRO_TOP_1));
    }

    @Test
    void find_shouldFilterByMinHeight() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(NO_LIMIT, null, 1000, null);

        assertThat("Did not return all summits equal to or above min height.", result, containsInAnyOrder(MUNRO_1, MUNRO_2));
    }

    @Test
    void find_shouldFilterByMinHeightAndMaxHeight() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        assertThat("Did not return all summits with of height 1000m.", summitService.find(NO_LIMIT, null, 1000, 1000),
                containsInAnyOrder(MUNRO_1));

        assertThat("Did not return all summits between 0 and 1100m.", summitService.find(NO_LIMIT, null, 0, 1100),
                containsInAnyOrder(MUNRO_TOP_1, MUNRO_1));

        assertThat("Did not return all summits between 995 and 9999m.", summitService.find(NO_LIMIT, null, 995, 9999),
                containsInAnyOrder(MUNRO_1, MUNRO_2));

    }

    @Test
    void find_shouldFilterByCategoryAndMinHeightAndMaxHeight() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        assertThat("Did not return all munros with of height 1000m.", summitService.find(NO_LIMIT, MUNRO, 1000, 1000),
                containsInAnyOrder(MUNRO_1));
        assertThat("Did not return expected empty collectiion.", summitService.find(NO_LIMIT, MUNRO_TOP, 1000, 1000),
                emptyCollectionOf(Summit.class));

        assertThat("Did not return all munros between 0 and 1100m.", summitService.find(NO_LIMIT, MUNRO, 0, 1100),
                containsInAnyOrder(MUNRO_1));
        assertThat("Did not return all munro tops between 0 and 1100m.", summitService.find(NO_LIMIT, MUNRO_TOP, 0,
                1100),
                containsInAnyOrder(MUNRO_TOP_1));

        assertThat("Did not return all munros between 995 and 9999m.", summitService.find(NO_LIMIT, MUNRO, 995, 9999),
                containsInAnyOrder(MUNRO_1, MUNRO_2));
        assertThat("Did not return all munro tops between 995 and 9999m.", summitService.find(NO_LIMIT, MUNRO_TOP, 995,
                9999),
                emptyCollectionOf(Summit.class));

    }

    @Test
    void find_shouldLimitNumberOfResults_whenAllFilterParamsAreNull_AndLimitGreaterThanZeroIsSpecified() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(2, null, null, null);

        assertThat("Number of results not equal to limit.", result.size(), is(2));
        assertThat("Did not return expected summits.", result, everyItem(in(ALL_MUNROS.toArray())));
    }

    @Test
    void find_shouldLimitNumberOfResultsReturned_whenFiltersAreApplied_AndLimitGreaterThanZeroIsSpecified() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.find(1, MUNRO, 995, 9999);

        assertThat("Number of results not equal to limit.", result.size(), is(1));
        assertThat("Did not return expected summits.", result, everyItem(in(ALL_MUNROS.toArray())));
    }
}