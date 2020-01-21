package com.im.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author WD
 * @Description 配置类
 * @Date 14:54 2020/1/21
*/
@Component
@ConfigurationProperties(prefix="diy")
public class BaseConfig {
    private String uploadPath;
    private String multipartUrl;
    private String payUrl;
    private String payCodeUrl;
    private String platformUrl;
    private String psychologicalTestUrl;

    public String getPayCodeUrl() {
        return payCodeUrl;
    }

    public void setPayCodeUrl(String payCodeUrl) {
        this.payCodeUrl = payCodeUrl;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getMultipartUrl() {
        return multipartUrl;
    }

    public void setMultipartUrl(String multipartUrl) {
        this.multipartUrl = multipartUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public String getPsychologicalTestUrl() {
        return psychologicalTestUrl;
    }

    public void setPsychologicalTestUrl(String psychologicalTestUrl) {
        this.psychologicalTestUrl = psychologicalTestUrl;
    }
}
