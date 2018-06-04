package com.kobin.entity.exception;


import com.kobin.entity.ResMsg;

/**
 * Created by @author shibinbin on 2017/11/8.
 */

public class IVException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private ResMsg resMsg;

    public IVException() {
        super();
    }

    public IVException(ResMsg resMsg) {
        super(resMsg.getMsg());
        this.resMsg = resMsg;
    }

    public ResMsg getIVResponse() {
        return resMsg;
    }
}
