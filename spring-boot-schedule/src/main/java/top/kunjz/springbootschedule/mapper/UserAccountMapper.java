package top.kunjz.springbootschedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kunjz.springbootschedule.entity.UserAccount;

@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {

}