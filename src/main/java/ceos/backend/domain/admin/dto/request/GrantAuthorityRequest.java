package ceos.backend.domain.admin.dto.request;

import ceos.backend.domain.admin.domain.AdminRole;
import ceos.backend.global.common.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GrantAuthorityRequest {

    @Schema(defaultValue = "id")
    @NotNull(message = "사용자 ID를 입력해주세요.")
    private Long id;

    @Schema(defaultValue = "role")
    @ValidEnum(target = AdminRole.class)
    @NotNull(message = "변경할 권한을 입력해주세요.")
    private AdminRole adminRole;
}
