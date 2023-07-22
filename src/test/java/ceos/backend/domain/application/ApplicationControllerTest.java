package ceos.backend.domain.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("지원서 엑셀 파일 생성 시각 API - 권한 X")
    @Test
    void getApplicationExcelCreationTime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/file/creationtime"))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }
}
