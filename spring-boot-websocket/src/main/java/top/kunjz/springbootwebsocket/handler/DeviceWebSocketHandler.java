package top.kunjz.springbootwebsocket.handler;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import top.kunjz.springbootwebsocket.entity.DeviceData;
import top.kunjz.springbootwebsocket.mapper.DeviceDataMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
public class DeviceWebSocketHandler implements WebSocketHandler {

    // 存储所有连接的 session
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();
    // 存储用户 ID 和 session 的映射
    private static final Map<String, String> USER_SESSION_MAP = new ConcurrentHashMap<>();
    // 设备数据Mapper
    private final DeviceDataMapper deviceDataMapper;

    // 构造函数, 注入设备数据Mapper
    public DeviceWebSocketHandler(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }

    /**
     * 获取当前连接数
     */
    public static int getConnectionCount() {
        return SESSIONS.size();
    }

    /**
     * 连接建立成功时调用, 发送连接成功消息
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            SESSIONS.put(session.getId(), session);
            USER_SESSION_MAP.put(userId, session.getId());
            log.info("用户 {} 连接成功，当前连接数: {}", userId, SESSIONS.size());
            // 发送连接成功消息
            sendMessage(session, createMessage("CONNECTION", "连接成功"));
        }
    }

    /**
     * 处理消息, 根据消息类型进行处理
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String userId = getUserIdFromSession(session);
        String payload = message.getPayload().toString();
        log.info("收到用户 {} 的消息: {}", userId, payload);
        try {
            JSONObject jsonMessage = JSON.parseObject(payload);
            String type = jsonMessage.getString("type");
            switch (type) {
                case "GET_LATEST_DATA":
                    // 发送最新数据
                    sendLatestData(session);
                    break;
                case "HEARTBEAT":
                    // 心跳响应
                    sendMessage(session, createMessage("HEARTBEAT", "pong"));
                    break;
                default:
                    // 发送错误消息
                    sendMessage(session, createMessage("ERROR", "未知消息类型"));
            }
        } catch (Exception e) {
            // 发送错误消息
            sendMessage(session, createMessage("ERROR", "消息解析失败"));
        }
    }

    /**
     * 处理错误
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String userId = getUserIdFromSession(session);
        log.error("用户 {} 连接出错: {}", userId, exception.getMessage());
        removeSession(session);
    }

    /**
     * 连接关闭时调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId = getUserIdFromSession(session);
        log.info("用户 {} 连接关闭，原因: {}", userId, closeStatus);
        removeSession(session);
    }

    /**
     * 是否支持部分消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 向所有会话广播设备数据
     */
    public void broadcastDeviceData(DeviceData data) {
        try {
            String message = createDeviceDataMessage(data);
            for (WebSocketSession session : SESSIONS.values()) {
                if (session.isOpen()) {
                    sendMessage(session, message);
                }
            }
        } catch (Exception e) {
            log.error("广播设备数据失败: {}", e.getMessage());
        }
    }


    /**
     * 发送最新数据给指定的 session
     */
    private void sendLatestData(WebSocketSession session) {
        try {
            List<DeviceData> latestData = deviceDataMapper.selectLatestDataForEachDevice();
            String message = createMessage("LATEST_DATA", latestData);
            sendMessage(session, message);
        } catch (Exception e) {
            System.err.println("发送最新数据失败: " + e.getMessage());
            try {
                sendMessage(session, createMessage("ERROR", "获取数据失败"));
            } catch (IOException ex) {
                log.error("发送错误消息失败: {}", ex.getMessage());
            }
        }
    }


    /**
     * 创建设备数据消息
     */
    private String createDeviceDataMessage(DeviceData data) {
        JSONObject message = new JSONObject();
        message.put("type", "DEVICE_DATA");
        message.put("timestamp", System.currentTimeMillis());
        message.put("payload", data);
        return message.toJSONString();
    }


    /**
     * 创建普通消息
     */
    private String createMessage(String type, Object data) {
        JSONObject message = new JSONObject();
        message.put("type", type);
        message.put("timestamp", System.currentTimeMillis());
        message.put("payload", data);
        return message.toJSONString();
    }

    /**
     * 发送消息
     */
    private void sendMessage(WebSocketSession session, String message) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    /**
     * 从session中获取用户ID
     */
    private String getUserIdFromSession(WebSocketSession session) {
        // 从URI参数中获取userId
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        if (query != null && query.contains("userId=")) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("userId=")) {
                    return param.substring(7);
                }
            }
        }
        return session.getId();
    }

    /**
     * 移除session
     */
    private void removeSession(WebSocketSession session) {
        String sessionId = session.getId();
        SESSIONS.remove(sessionId);
        // 移除用户映射
        USER_SESSION_MAP.entrySet().removeIf(entry -> sessionId.equals(entry.getValue()));
    }
}
