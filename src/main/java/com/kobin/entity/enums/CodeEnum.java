package com.kobin.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by @author shibinbin on 2017/11/6.
 */

public enum CodeEnum {

    /*成功*/
    SUCC(0, "成功"),
    /*读取数据超时*/
    READ_TIME_OUT(1401, "获取数据超时 -_-"),
    /*连接超时*/
    CONNECT_TIME_OUT(1402, "获取连接超时 -_-"),
    /*输入参数错误*/
    INPUT_ERROR(1601, "输入参数错误 ^_^"),
    /*未知错误*/
    UNKNOWN(9999, "未知错误 Q_Q");
    private final int value;
    private final String msg;

    CodeEnum(int v, String m){
        value = v;
        msg = m;
    }

    @JsonCreator
    public static CodeEnum fromValue(int typeCode){
        for (CodeEnum code : CodeEnum.values()) {
            if (code.toValue() == typeCode){
                return code;
            }
        }
        throw new IllegalArgumentException("Invalid type code: " + typeCode);
    }

    @JsonValue
    public int toValue(){
        return value;
    }

    public String toMsg(){
        return msg;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
