package com.orbithy.cms.service;

import com.orbithy.cms.cache.IGlobalCache;
import com.orbithy.cms.data.po.StudentStatus;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.data.po.Status;
import com.orbithy.cms.mapper.StatusMapper;
import com.orbithy.cms.mapper.UserMapper;
import com.orbithy.cms.utils.BcryptUtils;
import com.orbithy.cms.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private StatusMapper StatusMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private IGlobalCache redis;

    @Value("${spring.secret}")
    private String secret1;

    public ResponseEntity<Result> addTeacher(String SDUId, String password, String username, int permission, String secret) {
        if (!Objects.equals(secret, secret1)) {
            return ResponseUtil.build(Result.error(401, "Authentication failed"));
        }
        if (!loginService.isExisted(SDUId)) {
            String passwd = BcryptUtils.encrypt(password);
            userMapper.addTeacher(username, passwd, SDUId, permission);
        }
        return ResponseUtil.build(Result.ok());
    }

    public ResponseEntity<Result> getInfo(String userId) {
        System.out.println(userId);
        return ResponseUtil.build(Result.success(userMapper.getUserInfo(userId), "获取成功"));
    }

    public ResponseEntity<Result> setUserStatus(String teacherId, String userId, String status) {
        if (userMapper.getPermission(teacherId) != 0) {
            return ResponseUtil.build(Result.error(401, "无权限"));
        }
        Status status1 = new Status();
        status1.setId(Integer.valueOf(userId));
        status1.setStatus(StudentStatus.fromDescription(status));
        StatusMapper.updateById(status1);
        return ResponseUtil.build(Result.ok());
    }

    /**
     * 用户登出
     *
     * @param token 用户token
     * @return 登出结果
     */
    public ResponseEntity<Result> logout(String token) {
        try {
            redis.del(token);
            return ResponseUtil.build(Result.success(null, "登出成功"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(400, "登出失败" + e));
        }
    }

    /**
     * 更新用户手机号
     *
     * @param userId 用户ID
     * @param phone 新手机号
     * @return 更新结果
     */
    public ResponseEntity<Result> updatePhone(String userId, String phone) {
        try {
            // 验证手机号格式
            if (!phone.matches("^1[3-9]\\d{9}$")) {
                return ResponseUtil.build(Result.error(400, "手机号格式不正确"));
            }
            userMapper.updatePhone(userId, phone);
            return ResponseUtil.build(Result.success(null, "手机号更新成功"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, "更新失败：" + e.getMessage()));
        }
    }

    /**
     * 更新用户邮箱
     *
     * @param userId 用户ID
     * @param email 新邮箱
     * @return 更新结果
     */
    public ResponseEntity<Result> updateEmail(String userId, String email) {
        try {
            // 验证邮箱格式
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return ResponseUtil.build(Result.error(400, "邮箱格式不正确"));
            }
            userMapper.updateEmail(userId, email);
            return ResponseUtil.build(Result.success(null, "邮箱更新成功"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, "更新失败：" + e.getMessage()));
        }
    }
}
