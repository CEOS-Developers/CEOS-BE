package ceos.backend.global.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/** api 공통 응답 형식입니다 */
@Getter
public class SuccessResponse<T> {
    @JsonProperty("status")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @Builder
    private SuccessResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponse<T> onSuccess(int code) {
        return SuccessResponse.<T>builder().code(code).message("요청에 성공하였습니다.").data(null).build();
    }

    public static <T> SuccessResponse<T> onSuccess(int code, T data) {
        return SuccessResponse.<T>builder().code(code).message("요청에 성공하였습니다.").data(data).build();
    }
}
