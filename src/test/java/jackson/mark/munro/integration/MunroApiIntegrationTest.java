package jackson.mark.munro.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MunroApiIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void listEndpoint_shouldReturnOK_whenValidRequest() throws Exception {
            this.mockMvc.perform(get("/summits?category=MUNRO&min-height=950&max-height=1100")).andExpect(status().isOk());
        }
}
