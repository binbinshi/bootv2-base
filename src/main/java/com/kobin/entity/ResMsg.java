package com.kobin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kobin.entity.enums.CodeEnum;

import org.apache.commons.lang.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * Created by @author shibinbin on 2017/11/6.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResMsg<T> {
    private final static String SUCC_MSG = "success";
    private final static String SUCC_CODE = "0";

    private String code;
    private String sub;
    private String msg;
    private T data;

    public static ResMsg succ() {
        return ResMsg.builder().code(CodeEnum.SUCC.toString()).sub(SUCC_CODE).msg(SUCC_MSG).build();
    }

    public static ResMsg succWithData(Object data) {
        return ResMsg.builder().code(CodeEnum.SUCC.toString()).sub(SUCC_CODE).msg(SUCC_MSG).data(data).build();
    }

    public static ResMsg fail(CodeEnum code, String sub, String msg) {
        return ResMsg.builder().code(code.toString()).sub(sub).msg(msg).build();
    }

    public static ResMsg fail(String code, String sub, String msg) {
        return ResMsg.builder().code(code).sub(sub).msg(msg).build();
    }

    public static ResMsg unknowWithMsg(String msg) {
        return ResMsg.builder().code(CodeEnum.UNKNOWN.toString())
                .sub(StringUtils.EMPTY).msg(msg).build();
    }

}
