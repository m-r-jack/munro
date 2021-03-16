package jackson.mark.munro.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MunroApiIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void listEndpoint_shouldReturnOK_whenValidRequest() throws Exception {
            this.mockMvc.perform(get("/munros")).andExpect(status().isOk());
        }
}
