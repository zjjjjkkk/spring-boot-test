package top.zjk.boot.mp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.zjk.boot.mp.Mapper.UserAccountMapper;
import top.zjk.boot.mp.common.ApiResponse;
import top.zjk.boot.mp.entity.UserAccount;
import top.zjk.boot.mp.exception.BusinessException;
import top.zjk.boot.mp.exception.ErrorCode;
import top.zjk.boot.mp.service.UserAccountService;

import java.util.List;

/**
 * @author zjk
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    /**
     * 创建用户（明文密码，后端 BCrypt 加密）
     */
    @PostMapping
    public ApiResponse<UserAccount> createUser(@Valid @RequestBody UserAccount userAccount) {
        boolean saved = userAccountService.createUser(userAccount);
        return saved ? ApiResponse.success("用户创建成功", userAccount)
                : ApiResponse.error("用户创建失败");
    }

    /**
     * 根据 ID 获取用户
     */
    @GetMapping("/{id}")
    public ApiResponse<UserAccount> getUserById(@PathVariable @NotNull Long id) {
        UserAccount user = userAccountService.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return ApiResponse.success("查询成功", user);
    }
    /**
     * 获取所有用户列表
     */
    @GetMapping
    public ApiResponse<List<UserAccount>> getAllUsers() {
        List<UserAccount> users = userAccountService.list();
        return ApiResponse.success("查询成功", users);
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    public ApiResponse<IPage<UserAccount>> getUsersPage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        IPage<UserAccount> result = userAccountService.page(new Page<>(current, size));
        return ApiResponse.success("分页查询成功", result);
    }

    /**
     * 根据用户名查询
     */
    @GetMapping("/username/{username}")
    public ApiResponse<UserAccount> getUserByUsername(@PathVariable String username) {
        UserAccount user = userAccountService.getOne(
                new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUsername, username));
        return user != null ? ApiResponse.success("查询成功", user)
                : ApiResponse.error("未找到用户");
    }

    /**
     * 根据邮箱查询
     */
    @GetMapping("/email/{email}")
    public ApiResponse<UserAccount> getUserByEmail(@PathVariable String email) {
        UserAccount user = userAccountService.getOne(
                new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getEmail, email));
        return user != null ? ApiResponse.success("查询成功", user)
                : ApiResponse.error("未找到用户");
    }

    /**
     * 根据状态查询用户列表
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<UserAccount>> getUsersByStatus(@PathVariable Integer status) {
        List<UserAccount> users = userAccountService.list(
                new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getStatus, status));
        return ApiResponse.success("查询成功", users);
    }

    /**
     * 模糊查询昵称
     */
    @GetMapping("/search")
    public ApiResponse<List<UserAccount>> searchUsersByNickname(@RequestParam String nickname) {
        List<UserAccount> users = userAccountService.list(
                new LambdaQueryWrapper<UserAccount>().like(UserAccount::getNickname, nickname));
        return ApiResponse.success("查询成功", users);
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public ApiResponse<UserAccount> updateUser(@PathVariable @NotNull Long id,
                                               @Valid @RequestBody UserAccount userAccount) {
        userAccount.setId(id);
        boolean updated = userAccountService.updateById(userAccount);
        return updated ? ApiResponse.success("更新成功", userAccount)
                : ApiResponse.error("更新失败");
    }

    /**
     * 逻辑删除用户
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable @NotNull Long id) {
        boolean deleted = userAccountService.removeById(id);
        return deleted ? ApiResponse.success("删除成功", null)
                : ApiResponse.error("删除失败或用户不存在");
    }

    /**
     * 批量创建用户
     */
    @PostMapping("/batch")
    public ApiResponse<List<UserAccount>> createUsers(@Valid @RequestBody List<UserAccount> userAccounts) {
        boolean saved = userAccountService.createUsers(userAccounts);
        return saved ? ApiResponse.success("批量创建成功", userAccounts)
                : ApiResponse.error("批量创建失败");
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    public ApiResponse<Void> deleteUsers(@RequestBody List<Long> ids) {
        boolean deleted = userAccountService.removeByIds(ids);
        return deleted ? ApiResponse.success("批量删除成功", null)
                : ApiResponse.error("批量删除失败");
    }

    /**
     * 获取用户总数
     */
    @GetMapping("/count")
    public ApiResponse<Long> getUserCount() {
        long count = userAccountService.count();
        return ApiResponse.success("统计成功", count);
    }

    /**
     * 获取活跃用户数量
     */
    @GetMapping("/count/active")
    public ApiResponse<Long> getActiveUserCount() {
        long count = userAccountService.count(
                new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getStatus, 1));
        return ApiResponse.success("统计成功", count);
    }

    /** 启用/禁用用户 */
    /**
     * 启用/禁用用户
     */
    /** 启用/禁用用户 */
    @PatchMapping("/{id}/status")
    public ApiResponse<UserAccount> updateUserStatus(@PathVariable @NotNull Long id,
                                                     @RequestParam Integer status) {
        UserAccount user = userAccountService.getById(id);
        if (user == null) {
            return ApiResponse.error("用户不存在");
        }
        // ✅ 只改需要变的字段，其他保持原样（version 也在）
        user.setStatus(status);
        boolean updated = userAccountService.updateById(user);
        return updated ? ApiResponse.success("状态更新成功", user)
                : ApiResponse.error("状态更新失败（可能版本冲突）");
    }
}