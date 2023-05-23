package ceos.backend.domain.settings.domain;

import ceos.backend.domain.admin.exception.NotAllowedToModify;
import ceos.backend.domain.settings.helper.SettingsHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SettingsTest {

    @Autowired
    private SettingsHelper settingsHelper;

    @Test
    @DisplayName("지원 기간에는 수정할 수 없음")
    void validAmenablePeriod_X() {
        //given
        LocalDate date = LocalDate.of(2023, 5, 17);

        //when
        Settings settings = settingsHelper.takeSetting();

        //then
        assertThrows(NotAllowedToModify.class, () -> {
            settings.validAmenablePeriod(date);
            throw new NotAllowedToModify();
        });
    }

    @Test
    @DisplayName("지원 기간이 아닐 때는 수정할 수 있음")
    void validAmenablePeriod_O() {
        //given
        LocalDate date = LocalDate.of(2023, 5, 16);

        //when
        Settings settings = settingsHelper.takeSetting();

        //then
        settings.validAmenablePeriod(date);
    }
}
