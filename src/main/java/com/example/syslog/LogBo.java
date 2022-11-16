package com.example.syslog;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Oliver.liu
 * @version 1.0.0
 * @ClassName LogBo.java
 * @Description TODO
 * @createTime 2022年11月16日 14:00:00
 */
public class LogBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;

    private String id;

    private String serviceId;

    private String serviceType;

    private String requestParam;

    private LocalDateTime requestDateTime;

    private String responseResult;

    private LocalDateTime createdDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(LocalDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "BsLog{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", serviceType=" + serviceType +
                ", requestParam=" + requestParam +
                ", requestDateTime=" + requestDateTime +
                ", responseResult=" + responseResult +
                ", createdDateTime=" + createdDateTime +
                ", requestId=" + requestId +
                "}";
    }
}
