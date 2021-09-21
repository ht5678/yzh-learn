package com.learn.common.http;

import java.util.Objects;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang3.StringUtils;

public class HttpClientResponse {

    private HttpMethod method;

    private String body;

    private byte[] byteBody;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getByteBody() {
        return byteBody;
    }

    public void setByteBody(byte[] byteBody) {
        this.byteBody = byteBody;
    }

    public String getHeader(String name) {
        if (this.method == null) {
            return StringUtils.EMPTY;
        }
        Header responseHeader = this.method.getResponseHeader(name);
        if (Objects.isNull(responseHeader)) {
            return StringUtils.EMPTY;
        }
        return responseHeader.getValue();
    }

}
