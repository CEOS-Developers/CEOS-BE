package ceos.backend.domain.subscriber.dto.request;


import ceos.backend.global.common.annotation.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SubscribeRequest {

    @Schema(defaultValue = "ceos@ceos-sinchon.com", description = "이메일")
    @ValidEmail
    private String email;

    @Schema(defaultValue = "010-1234-1234", description = "전화번호")
    private String phoneNum;
}
