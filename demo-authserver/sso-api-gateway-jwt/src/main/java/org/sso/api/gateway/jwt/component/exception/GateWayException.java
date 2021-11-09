package org.sso.api.gateway.jwt.component.exception;

import org.ms.cloud.common.component.exception.SystemErrorType;

/**
 * 网关异常
 * Created by smlz on 2019/12/26.
 */
public class GateWayException extends RuntimeException {

    private String code;

    private String msg;

    public GateWayException(SystemErrorType systemErrorType) {
        this.code = systemErrorType.getCode();
        this.msg = systemErrorType.getMsg();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    
}
