package top.zjk.boot.exception.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.zjk.boot.exception.common.Result;
import top.zjk.boot.exception.entity.User;
import top.zjk.boot.exception.service.ExceptionService;


@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private ExceptionService exceptionService;

    @GetMapping("/{id}")
    public Result<String>getUserById(@PathVariable Integer id){
        if(id == 0){
            exceptionService.unAuthorizedError();
        }else if (id == 1){
            exceptionService.systemError();
        }else{
            return Result.ok("查询成功");
        }
        return Result.ok("查询成功");
    }
    @PostMapping("/user")
    public Result<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return Result.ok(user);
    }


}
