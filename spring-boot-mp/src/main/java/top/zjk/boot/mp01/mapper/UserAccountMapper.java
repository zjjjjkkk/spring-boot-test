package top.zjk.boot.mp.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zjk.boot.mp.entity.UserAccount;
/**
 * @author zjk
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    // 继承 BaseMapper 获得基础 CRUD 能力
    // 可以在此添加自定义查询方法
}