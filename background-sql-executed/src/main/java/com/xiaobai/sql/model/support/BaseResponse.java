package com.xiaobai.sql.model.support;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * Global response entity.
 *
 * @author johnniang
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    /**
     * Response status.
     */
    private Integer status;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response development message
     */
    private String devMessage;

    /**
     * Response data
     */
    private T data;

    public Long time;

    public BaseResponse(Integer status, String message, T data, Long time) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.time = time;
    }

    /**
     * Creates an ok result with message and data. (Default status is 200)
     *
     * @param data    result data
     * @param message result message
     * @return ok result with message and data
     */
    public static <T> BaseResponse<T> ok(String message, T data, Long time) {
        return new BaseResponse<T>(HttpStatus.OK.value(), message, data, time);
    }

    /**
     * Creates an ok result with message only. (Default status is 200)
     *
     * @param message result message
     * @return ok result with message only
     */
    public static <T> BaseResponse<T> ok(String message, Long time) {
        return ok(message, null, time);
    }

    /**
     * Creates an ok result with data only. (Default message is OK, status is 200)
     *
     * @param data data to response
     * @param <T>  data type
     * @return base response with data
     */
    public static <T> BaseResponse<T> ok(T data, Long time) {
        return new BaseResponse<T>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data, time);
    }
}
