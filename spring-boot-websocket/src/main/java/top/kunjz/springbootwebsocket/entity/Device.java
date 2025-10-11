package top.kunjz.springbootwebsocket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@TableName("device")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 设备位置
     */
    private String location;

    /**
     * 设备描述
     */
    private String description;

    /**
     * 设备经度
     */
    private BigDecimal longitude;

    /**
     * 设备纬度
     */
    private BigDecimal latitude;

    /**
     * 设备状态:OFFLINE, ONLINE, WARNING, ERROR, MAINTENANCE
     */
    private String status;

    /**
     * 设备最后异常时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastWarningTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}