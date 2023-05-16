package ceos.backend.domain.application.dto.request;

import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.University;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class CreateApplicationRequest {
//    @Schema(defaultValue = "김영한", description = "지원자 이름")
//    @NotEmpty(message = "지원자 이름을 입력해주세요")
//    private String name;

//    @Schema(defaultValue = "남성", description = "지원자 이름")
////    @Enum(target = Gender.class)
//    private Gender gender;

//    @Schema(type = "string",
//            pattern = "yyyy.MM.dd",
//            defaultValue = "2023.03.20",
//            description = "공연 생년월일")
//    @NotEmpty(message = "지원자 생년월일을 입력해주세요")
//    @DateFormat
//    private LocalDate birth;

//    @Schema(defaultValue = "ceos@ceos-sinchon.com", description = "지원자 이메일")
//    @NotEmpty(message = "지원자 이메일을 입력해주세요")
//    private String email;

//    @Schema(defaultValue = "010-1234-5678", description = "지원자 전화번호")
//    @NotEmpty(message = "지원자 전화번호를 입력해주세요")
//    // TODO: phone 번호 validation
//    private String phoneNumber;

    @Schema(defaultValue = "홍익대학교", description = "지원자 대학교")
    @ValidEnum(target = University.class)
    private University university;
//
//    @Schema(defaultValue = "컴퓨터 공학과", description = "지원자 전공")
//    @NotEmpty(message = "지원자 전공을 입력해주세요")
//    private String major;

//    @Schema(defaultValue = "백엔드", description = "지원자 파트")
////    @Enum(target = Part.class)
//    private Part part;

//    @Schema(defaultValue = "99999999", description = "지원자 남은 학기 수")
//    @NotNull(message = "지원자 남은 학기 수를 입력해주세요")
//    private int semestersLeftNumber;
//
//    @Schema(defaultValue = "구르기", description = "지원자 다른 활동")
//    @NotEmpty(message = "지원자 다른 활동을 입력해주세요")
//    private String otherActivities;

//    @Schema(type = "string",
//            pattern = "yyyy.MM.dd",
//            defaultValue = "2023.03.20",
//            description = "ot 날짜")
//    @NotNull(message = "ot 날짜를 입력해주세요")
//    @DateFormat
//    private LocalDate otDate;
//
//    @Schema(type = "string",
//            pattern = "yyyy.MM.dd",
//            defaultValue = "2023.03.20",
//            description = "데모데이 날짜")
//    @NotEmpty(message = "데모데이 날짜를 입력해주세요")
//    @DateFormat
//    private LocalDate demodayDate;
}
