package jackson.mark.munro.controller;

import jackson.mark.munro.model.Summit;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static jackson.mark.munro.model.SummitCategory.MUNRO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SortParamParserTest {

    private static final Summit SUMMIT_1 = aSummitWithNameAndHeight("Ben Nevis", 1345);
    private static final Summit SUMMIT_2 = aSummitWithNameAndHeight("Ben Mark", 1246);
    private static final Summit SUMMIT_3 = aSummitWithNameAndHeight("Ben Lomond", 974);
    private static final Summit SUMMIT_4 = aSummitWithNameAndHeight("Cairn Gorm", 1245);
    private static final Summit SUMMIT_5 = aSummitWithNameAndHeight("Ben Mark", 1245);
    private static final List<Summit> ALL_SUMMITS = List.of(SUMMIT_1, SUMMIT_2, SUMMIT_3, SUMMIT_4, SUMMIT_5);


    private static final String HEIGHT_DESCENDING_SPECIFIER = "height_desc";
    private static final String HEIGHT_ASCENDING_SPECIFIER = "height_asc";
    private static final String NAME_DESCENDING_SPECIFIER = "name_desc";
    private static final String NAME_ASCENDING_SPECIFIER = "name_asc";
    private static final String HEIGHT_DESC_THEN_NAME_DESC_SPECIFIER = "height_desc,name_desc";
    private static final String HEIGHT_DESC_THEN_NAME_ASC_SPECIFIER = "height_desc,name_asc";
    private static final String NAME_ASC_THEN_HEIGHT_DESC_SPECIFIER = "name_asc,height_desc";
    private static final String NAME_ASC_THEN_HEIGHT_ASC_SPECIFIER = "name_asc,height_asc";

    SortParamParser sortParamParser = new SortParamParser();

    @Test
    void shouldReturnByHeightAscendingComparator_whenSortParamContainsHeightAscendingSpecifier() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(HEIGHT_ASCENDING_SPECIFIER);

        assertSorted(List.of(SUMMIT_1, SUMMIT_2, SUMMIT_3), List.of(SUMMIT_3, SUMMIT_2, SUMMIT_1), comparator);
    }

    @Test
    void shouldReturnByHeightDescendingComparator_whenSortParamContainsHeightDescendingSpecifier() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(HEIGHT_DESCENDING_SPECIFIER);

        assertSorted(List.of(SUMMIT_2, SUMMIT_1, SUMMIT_3), List.of(SUMMIT_1, SUMMIT_2, SUMMIT_3), comparator);
    }

    @Test
    void shouldReturnByNameAscendingComparator_whenSortParamContainsNameAscendingSpecifier() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(NAME_ASCENDING_SPECIFIER);

        assertSorted(List.of(SUMMIT_3, SUMMIT_1, SUMMIT_2), List.of(SUMMIT_3, SUMMIT_2, SUMMIT_1), comparator);
    }

    @Test
    void shouldReturnByNameDescendingComparator_whenSortParamContainsNameDescendingSpecifier() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(NAME_DESCENDING_SPECIFIER);

        assertSorted(List.of(SUMMIT_2, SUMMIT_3, SUMMIT_1), List.of(SUMMIT_1, SUMMIT_2, SUMMIT_3), comparator);
    }

    @Test
    void shouldReturnHeightDescendingThenNameDescendingComparator_whenSortParamContainsHeightDescendingThenNameDescending() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(HEIGHT_DESC_THEN_NAME_DESC_SPECIFIER);

        assertSorted(ALL_SUMMITS, List.of(SUMMIT_1, SUMMIT_2, SUMMIT_4, SUMMIT_5, SUMMIT_3), comparator);
    }

    @Test
    void shouldReturnHeightDescendingThenNameAscendingComparator_whenSortParamContainsHeightDescendingThenNameAscending() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(HEIGHT_DESC_THEN_NAME_ASC_SPECIFIER);

        assertSorted(ALL_SUMMITS, List.of(SUMMIT_1, SUMMIT_2, SUMMIT_5, SUMMIT_4, SUMMIT_3), comparator);
    }

    @Test
    void shouldReturnNameAscendingThenHeightDescendingComparator_whenSortParamContainsNameAscendingThenHeightDescending() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(NAME_ASC_THEN_HEIGHT_DESC_SPECIFIER);

        assertSorted(ALL_SUMMITS, List.of(SUMMIT_3, SUMMIT_2, SUMMIT_5, SUMMIT_1, SUMMIT_4), comparator);
    }

    @Test
    void shouldReturnNameAscendingThenHeightAscendingComparator_whenSortParamContainsNameAscendingThenHeightAscending() {
        Comparator<Summit> comparator = sortParamParser.parseSortParam(NAME_ASC_THEN_HEIGHT_ASC_SPECIFIER);

        assertSorted(ALL_SUMMITS, List.of(SUMMIT_3, SUMMIT_5, SUMMIT_2, SUMMIT_1, SUMMIT_4), comparator);
    }

    private void assertSorted(List<Summit> orig, List<Summit> expectedSorted, Comparator<Summit> comparator) {
        assertThat(orig.stream().sorted(comparator).collect(Collectors.toList()), is(expectedSorted));
    }



    private static Summit aSummitWithNameAndHeight(String name, int height) {
        return Summit.builder()
                .summitCategory(MUNRO)
                .gridReference("aGridRef")
                .name(name)
                .heightInMetres(height)
                .build();
    }
}