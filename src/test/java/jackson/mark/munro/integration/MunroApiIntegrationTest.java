package jackson.mark.munro.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MunroApiIntegrationTest {

        // rows in csv file that have a valid category (MUNRO or TOP) in the “post 1997" column.
        private static final int TOTAL_VALID_ROWS_IN_CSV = 509;

        private static final String EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_ASC_NAME_DESC =
                "[{\"name\":\"Aonach Eagach - Meall Dearg\",\"heightInMetres\":952.2,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN161583\"}," +
                "{\"name\":\"Sgurr nan Coireachan\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NM933958\"}," +
                "{\"name\":\"Beinn Mhanach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN373411\"}," +
                "{\"name\":\"Am Faochagach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NH303793\"}," +
                "{\"name\":\"Buachaille Etive Mor - Stob na Broige\",\"heightInMetres\":953.4,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN190525\"}]";

        private static final String EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_ASC_NAME_ASC =
                "[{\"name\":\"Aonach Eagach - Meall Dearg\",\"heightInMetres\":952.2,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN161583\"}," +
                        "{\"name\":\"Am Faochagach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NH303793\"}," +
                        "{\"name\":\"Beinn Mhanach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN373411\"}," +
                        "{\"name\":\"Sgurr nan Coireachan\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NM933958\"}," +
                        "{\"name\":\"Buachaille Etive Mor - Stob na Broige\",\"heightInMetres\":953.4,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN190525\"}]";

        private static final String EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_DESC_NAME_DESC =
                "[{\"name\":\"Buachaille Etive Mor - Stob na Broige\",\"heightInMetres\":953.4,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN190525\"},"  +
                        "{\"name\":\"Sgurr nan Coireachan\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NM933958\"}," +
                        "{\"name\":\"Beinn Mhanach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN373411\"}," +
                        "{\"name\":\"Am Faochagach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NH303793\"}," +
                        "{\"name\":\"Aonach Eagach - Meall Dearg\",\"heightInMetres\":952.2," +
                        "\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN161583\"}]";

        private static final String EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_DESC_NAME_ASC =
                "[{\"name\":\"Buachaille Etive Mor - Stob na Broige\",\"heightInMetres\":953.4,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN190525\"},"  +
                        "{\"name\":\"Am Faochagach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NH303793\"}," +
                        "{\"name\":\"Beinn Mhanach\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN373411\"}," +
                        "{\"name\":\"Sgurr nan Coireachan\",\"heightInMetres\":953.0,\"summitCategory\":\"MUNRO\",\"gridReference\":\"NM933958\"}," +
                        "{\"name\":\"Aonach Eagach - Meall Dearg\",\"heightInMetres\":952.2," +
                        "\"summitCategory\":\"MUNRO\",\"gridReference\":\"NN161583\"}]";

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void findEndpoint_shouldReturnAllValidSummits_whenNoFiltersOrLimitSupplied() throws Exception {
                mockMvc.perform(get("/summits"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(TOTAL_VALID_ROWS_IN_CSV)));
        }

        @Test
        public void findEndpoint_shouldReturnCorrectSummits_whenFiltersSortAndLimitSupplied_andSortByHeightAscNameDesc() throws Exception {
                mockMvc.perform(
                        get("/summits?limit=5&sort=height_asc,name_desc&category=MUNRO&min-height=952" +
                                ".1&max-height=953.5"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_ASC_NAME_DESC, true));
        }

        @Test
        public void findEndpoint_shouldReturnCorrectSummits_whenFiltersSortAndLimitSupplied_andSortByHeightAscNameAsc() throws Exception {
                mockMvc.perform(
                        get("/summits?limit=5&sort=height_asc,name_asc&category=MUNRO&min-height=952" +
                                ".1&max-height=953.5"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_ASC_NAME_ASC, true));
        }

        @Test
        public void findEndpoint_shouldReturnCorrectSummits_whenFiltersSortAndLimitSupplied_andSortByHeightDescNameDesc() throws Exception {
                mockMvc.perform(
                        get("/summits?limit=5&sort=height_desc,name_desc&category=MUNRO&min-height=952" +
                                ".1&max-height=953.5"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_DESC_NAME_DESC, true));
        }

        @Test
        public void findEndpoint_shouldReturnCorrectSummits_whenFiltersSortAndLimitSupplied_andSortByHeightDescNameAsc() throws Exception {
                mockMvc.perform(
                        get("/summits?limit=5&sort=height_desc,name_asc&category=MUNRO&min-height=952" +
                                ".1&max-height=953.5"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(EXPECTED_RESPONSE_MUNROS_LIMITED_AND_FILTERED_SORTED_BY_HEIGHT_DESC_NAME_ASC, true));
        }

        @Test
        public void findEndpoint_shouldReturnUnprocessableEntityError_whenMinHightGreaterThanMaxHeight() throws Exception {
                mockMvc.perform(get("/summits?limit=999&category=MUNRO&min-height=1150&max-height=1100")).andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void findEndpoint_shouldReturnUnprocessableEntityError_whenLimitIsNegative() throws Exception {
                mockMvc.perform(get("/summits?limit=-1&category=MUNRO&min-height=950&max-height=1100")).andExpect(status().isUnprocessableEntity());
        }
}
