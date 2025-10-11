package top.kunjz.springbootwebsocket.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kunjz.springbootwebsocket.entity.Device;
import top.kunjz.springbootwebsocket.entity.DeviceData;
import top.kunjz.springbootwebsocket.handler.DeviceWebSocketHandler;
import top.kunjz.springbootwebsocket.mapper.DeviceDataMapper;
import top.kunjz.springbootwebsocket.mapper.DeviceMapper;
import top.kunjz.springbootwebsocket.result.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class DeviceController {
    private final DeviceMapper deviceMapper;
    private final DeviceDataMapper deviceDataMapper;
    private final DeviceWebSocketHandler deviceWebSocketHandler;

    /**
     * 获取所有设备列表
     */
    @GetMapping("/devices")
    public Result<List<Device>> getAllDevices() {
        try {
            List<Device> devices = deviceMapper.selectList(null);
            return Result.ok(devices);
        } catch (Exception e) {
            return Result.error("获取设备列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取设备最新数据
     */
    @GetMapping("/devices/latest-data")
    public Result<List<DeviceData>> getLatestData() {
        try {
            // 获取每个设备的最新数据
            List<DeviceData> latestData = deviceDataMapper.selectLatestDataForEachDevice();
            return Result.ok(latestData);
        } catch (Exception e) {
            return Result.error("获取最新数据失败: " + e.getMessage());
        }
    }

    /**
     * 获取指定设备的最新数据
     */
    @GetMapping("/devices/{deviceId}/latest-data")
    public Result<List<DeviceData>> getDeviceLatestData(@PathVariable Long deviceId) {
        try {
            // 获取指定设备的最新数据
            LambdaQueryWrapper<DeviceData> wrapper = new LambdaQueryWrapper<>();
            // 按设备ID和记录时间倒序排序，只取最新的10条数据
            wrapper.eq(DeviceData::getDeviceId, deviceId).orderByDesc(DeviceData::getRecordTime).last("LIMIT 10");
            List<DeviceData> data = deviceDataMapper.selectList(wrapper);
            return Result.ok(data);
        } catch (Exception e) {
            return Result.error("获取设备数据失败: " + e.getMessage());
        }
    }

    /**
     * 获取设备统计信息
     */
    @GetMapping("/devices/statistics")
    public Result<Map<String, Object>> getDeviceStatistics() {
        try {
            // 获取设备总数
            Long totalDevices = deviceMapper.selectCount(null);
            // 按状态统计设备数量
            Map<String, Long> statusCount = deviceMapper.selectList(null).stream().collect(Collectors.groupingBy(Device::getStatus, Collectors.counting()));
            // 获取各状态设备数
            Long onlineDevices = statusCount.getOrDefault("ONLINE", 0L);
            Long offlineDevices = statusCount.getOrDefault("OFFLINE", 0L);
            Long warningDevices = statusCount.getOrDefault("WARNING", 0L) + statusCount.getOrDefault("ERROR", 0L);
            // 获取设备状态统计
            Map<String, Object> statistics = Map.of(
                    "totalDevices", totalDevices,
                    "onlineDevices", onlineDevices,
                    "offlineDevices", offlineDevices,
                    "warningDevices", warningDevices,
                    "statusCount", statusCount
            );
            return Result.ok(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取历史数据
     */
    @GetMapping("/devices/{deviceId}/history")
    public Result<List<DeviceData>> getDeviceHistory(@PathVariable Long deviceId, @RequestParam(defaultValue = "24") Integer hours) {
        try {
            // 获取指定设备指定时间段的历史数据
            List<DeviceData> history = deviceDataMapper.selectHistoryData(deviceId, hours);
            return Result.ok(history);
        } catch (Exception e) {
            return Result.error("获取历史数据失败: " + e.getMessage());
        }
    }

    /**
     * 模拟设备数据上报（用于测试）
     */
    @PostMapping("/devices/{deviceId}/report")
    public Result<String> reportData(@PathVariable Long deviceId, @RequestParam String metricName, @RequestParam Double metricValue, @RequestParam(required = false) String unit, @RequestParam(defaultValue = "normal") String status) {
        try {
            // 创建设备数据对象
            DeviceData data = new DeviceData();
            data.setDeviceId(deviceId);
            data.setMetricName(metricName);
            data.setMetricValue(BigDecimal.valueOf(metricValue));
            data.setUnit(unit);
            data.setStatus(status);
            // 插入数据库
            deviceDataMapper.insert(data);
            // 通过 WebSocket 实时广播推送新数据
            deviceWebSocketHandler.broadcastDeviceData(data);
            // 如果是异常数据，额外记录日志
            if ("high".equals(status) || "low".equals(status) || "critical".equals(status)) {
                log.error("异常数据上报 - 设备ID: {}, 指标: {}, 值: {}, 状态: {}", deviceId, metricName, metricValue, status);
            }
            return Result.ok("数据上报成功");
        } catch (Exception e) {
            return Result.error("数据上报失败: " + e.getMessage());
        }
    }
}