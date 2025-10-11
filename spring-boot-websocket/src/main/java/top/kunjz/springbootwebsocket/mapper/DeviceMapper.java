package top.kunjz.springbootwebsocket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kunjz.springbootwebsocket.entity.Device;


@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

}