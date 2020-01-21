package com.im.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author WD
 * @title: WebSocketConfig
 * @projectName IM
 * @description: 如果使用外部容器Tomcat启动项目,请将此注释,并将在SocketServer类上的@Component注解删掉
 * @date 2020/1/21--19:16
 */
@Configuration
public class WebSocketConfig {

    /**
     * 服务器节点
     *
     * 如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
