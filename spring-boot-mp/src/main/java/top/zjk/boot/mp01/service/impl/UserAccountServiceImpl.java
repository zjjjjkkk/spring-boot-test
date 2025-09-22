package top.zjk.boot.mp01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zjk.boot.mp01.mapper.UserAccountMapper;
import top.zjk.boot.mp01.entity.UserAccount;
import top.zjk.boot.mp01.service.UserAccountService;

import java.util.List;

/**
 * @author zjk
 */
@Service
@Transactional
public class UserAccountServiceImpl
        extends ServiceImpl<UserAccountMapper, UserAccount>
        implements UserAccountService {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /** 单个用户创建 */
    @Override
    public boolean createUser(UserAccount user) {
        processUserBeforeSave(user);
        return this.save(user);
    }

    /** 批量用户创建 */
    @Override
    public boolean createUsers(List<UserAccount> users) {
        users.forEach(this::processUserBeforeSave);
        // 1000 条一批插入
        return this.saveBatch(users, 1000);
    }

    /** 公共处理逻辑：密码加密 & 默认值 */
    private void processUserBeforeSave(UserAccount user) {
        // 密码加密：如果不是 BCrypt 格式($2 开头)，就加密
        if (user.getPassword() != null && !user.getPassword().startsWith("$2")) {
            user.setPassword(ENCODER.encode(user.getPassword()));
        }
        // 默认未删除
        if (user.getDeleted() == null) {
            user.setDeleted(0);
        }
        // 默认启用
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        if (user.getVersion() == null) {
            user.setVersion(0);
        }
    }
}