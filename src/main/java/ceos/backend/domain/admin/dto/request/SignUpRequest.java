package ceos.backend.domain.admin.dto.request;


import ceos.backend.domain.admin.vo.AdminVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @JsonUnwrapped private AdminVo adminVo;
}
