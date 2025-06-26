package coder.himakara.Set_my_goals.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse {
    private int code;
    private String message;
    private Object data;

    public StandardResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}