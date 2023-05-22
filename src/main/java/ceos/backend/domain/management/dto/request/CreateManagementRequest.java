package ceos.backend.domain.management.dto.request;

import ceos.backend.domain.management.vo.ManagementVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class CreateManagementRequest {

    @JsonUnwrapped
    private ManagementVo managementVo;
}
