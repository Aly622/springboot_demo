package com.example.common;


/**
 * 服务异常，由微服务内部逻辑导致的错误请抛出此异常。
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private IResponseCode responseCode;

    public ServiceException(IResponseCode response) {
        super(response.getMessage());
        this.responseCode = response;
    }

    public ServiceException(long code, String message) {
        super(message);
        this.responseCode = new IResponseCode() {
            @Override
            public long getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    public ServiceException(IResponseCode response, Throwable e) {
        super(response.getMessage(), e);
        this.responseCode = response;
    }

    /**
     * 异常堆栈增加错误代码和绑定变量
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMessage()).append("[").append(responseCode.getCode()).append("]\n");
        sb.append(super.toString());
        return sb.toString();
    }

    public IResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(IResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
