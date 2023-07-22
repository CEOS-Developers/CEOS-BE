package ceos.backend.domain.application;

import ceos.backend.domain.application.dto.response.GetCreationTime;
import ceos.backend.domain.application.service.ApplicationExcelService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationExcelServiceTest {

    @Autowired
    ApplicationExcelService applicationExcelService;

    @DisplayName("지원서 엑셀 파일 생성 시각")
    @Test
    public void getApplicationExcelCreationTime() throws Exception {
        GetCreationTime creationTime = applicationExcelService.getApplicationExcelCreationTime();
        Assertions.assertThat(creationTime.getCreateAt().charAt(4)).isEqualTo('-');
        Assertions.assertThat(creationTime.getCreateAt().charAt(7)).isEqualTo('-');
    }
}
