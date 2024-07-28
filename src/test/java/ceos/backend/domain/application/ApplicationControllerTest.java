package ceos.backend.domain.application;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired private MockMvc mockMvc;

    @DisplayName("지원서 엑셀 파일 생성 시각 API - 권한 X")
    @Test
    void getApplicationExcelCreationTime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/file/creationtime"))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @DisplayName("지원서 목록 보기 API - 필수 아닌 파라미터들 길이 0인 문자열로 처리")
    @Test
    void getApplicationsWithZeroStrings() throws Exception {
        Authentication authentication = new TestingAuthenticationToken(null, null, "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/applications?part=&docPass=&finalPass=&applicantName=&pageNum=0&limit=7")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("지원서 목록 보기 API - 필수 아닌 파라미터들 제외")
    @Test
    void getApplicationsWithoutRequiredFalse() throws Exception {
        Authentication authentication = new TestingAuthenticationToken(null, null, "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/applications?pageNum=0&limit=7")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("지원서 목록 보기 API - enum 타입 영어로 처리")
    @Test
    void successGetApplications() throws Exception {
        Authentication authentication = new TestingAuthenticationToken(null, null, "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/applications?part=PRODUCT&docPass=PASS&finalPass=FAIL&applicantName=&pageNum=0&limit=7")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("지원서 목록 보기 API - enum 타입 한글로 처리 시 예외 발생")
    @Test
    void failGetApplications() throws Exception {
        Authentication authentication = new TestingAuthenticationToken(null, null, "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/applications?part=기획&docPass=합격&finalPass=불합격&applicantName=&pageNum=0&limit=7")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
