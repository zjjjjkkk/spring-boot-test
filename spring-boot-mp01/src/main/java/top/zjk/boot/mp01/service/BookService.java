package top.zjk.boot.mp01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zjk.boot.mp01.entity.UserAccount;

import java.util.List;

/**
 * @author zjk
 */
public interface UserAccountService extends IService<UserAccount> {

    /** 创建单个用户（自动加密密码） */
    boolean createUser(UserAccount user);

    /** 批量创建用户（自动加密密码） */
    boolean createUsers(List<UserAccount> users);
}