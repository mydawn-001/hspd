package com.lankun.hspd.module.system.framework.rpc.config;

import com.lankun.hspd.module.infra.api.config.ConfigApi;
import com.lankun.hspd.module.infra.api.file.FileApi;
import com.lankun.hspd.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "systemRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
