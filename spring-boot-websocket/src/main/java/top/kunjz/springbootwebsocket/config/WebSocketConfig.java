package top.kunjz.springbootwebsocket.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import top.kunjz.springbootwebsocket.handler.DeviceWebSocketHandler;


@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final DeviceWebSocketHandler deviceWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 测试的 WebSocket 端点
        //registry.addHandler(new TestWebSocketHandler(), "/ws/test").setAllowedOrigins("*");

        // 时间推送的 WebSocket 端点
        //registry.addHandler(new SimpleTimeWebSocketHandler(), "/ws/time").setAllowedOrigins("*");

        // 设备监控的 WebSocket 端点
        registry.addHandler(deviceWebSocketHandler, "/ws/device")
                .setAllowedOrigins("*");
    }
}