package jackson.mark.munro.service;

import jackson.mark.munro.model.Summit;
import jackson.mark.munro.model.SummitCategory;
import jackson.mark.munro.store.SummitStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummitServiceImplTest {

    private static final Summit MUNRO = Summit.builder()
            .name("Ben Mark").heightInMetres(4000)
            .summitCategory(SummitCategory.MUNRO)
            .gridReference("NN773308")
            .build();

    private static final Summit MUNRO_TOP = Summit.builder()
            .name("Little Mark").heightInMetres(3500)
            .summitCategory(SummitCategory.MUNRO_TOP)
            .gridReference("NN119965")
            .build();

    private static final List<Summit> ALL_SUMMITS = List.of(MUNRO, MUNRO_TOP);

    @Mock
    private SummitStore summitStore;

    @InjectMocks
    private SummitServiceImpl summitService;

    @Test
    void listAll_shouldReturnAllSummits() {
        when(summitStore.getAll()).thenReturn(ALL_SUMMITS);

        Collection<Summit> result = summitService.listAll();

        assertThat("Did not return all summits.", result, is(ALL_SUMMITS));
    }
}