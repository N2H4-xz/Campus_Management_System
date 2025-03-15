package com.orbithy.cms.controller;


import com.orbithy.cms.annotation.Auth;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 添加教师
     *
     * @param SDUId     教师工号
     * @param password  密码
     * @param username  用户名
     * @param permission 权限
     * @param secret    密钥
     * @return ResponseEntity<Result>
     */
    @PostMapping("/addTeacher")
    public ResponseEntity<Result> addTeacher(String SDUId, String password, String username, int permission, String secret) {
        return userService.addTeacher(SDUId, password, username, permission, secret);
    }

    /**
     * 获取个人信息
     * @return ResponseEntity<Result>
     */
    @Auth
    @PostMapping("/getInfo")
    public ResponseEntity<Result> getInfo() {
        String userId = (String) request.getAttribute("userId");
        return userService.getInfo(userId);
    }
}
