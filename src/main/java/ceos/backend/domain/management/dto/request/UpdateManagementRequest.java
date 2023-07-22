package ceos.backend.domain.management.dto.request;


import ceos.backend.domain.management.Vo.ManagementVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class UpdateManagementRequest {

    @JsonUnwrapped private ManagementVo managementVo;
}
