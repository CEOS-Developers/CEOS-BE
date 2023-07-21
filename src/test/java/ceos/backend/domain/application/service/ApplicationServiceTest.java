package ceos.backend.domain.application.service;


import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired private ApplicationService applicationService;

    @Test
    void createExcelFile() throws IOException {
        applicationService.createExcelFile();
    }
}
