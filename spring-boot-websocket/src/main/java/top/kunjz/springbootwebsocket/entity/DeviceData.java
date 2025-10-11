package top.kunjz.springbootwebsocket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("device_data")
public class DeviceData {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 指标名称英文描述 （如temperature、humidity、pressure、co2、pm25、pm10等）
     */
    private String metricName;

    /**
     * 指标值 （如 32.2、46.5、10、400 等）
     */
    private BigDecimal metricValue;

    /**
     * 单位 （如 °C、%、ppm、ppb、μg/m3 等）
     */
    private String unit;

    /**
     * 状态 （如 normal、warning、error、high、low、critical 等）
     */
    private String status;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTime;

}