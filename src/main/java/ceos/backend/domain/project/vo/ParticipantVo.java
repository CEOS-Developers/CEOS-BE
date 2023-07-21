package ceos.backend.domain.project.vo;


import ceos.backend.domain.project.domain.*;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ParticipantVo {

    @Schema()
    @ValidEnum(target = Part.class)
    private Part part;

    @Schema()
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Builder
    public ParticipantVo(Part part, String name) {
        this.part = part;
        this.name = name;
    }

    public static ParticipantVo from(Participant participant) {
        return ParticipantVo.builder()
                .part(participant.getPart())
                .name(participant.getName())
                .build();
    }
}
