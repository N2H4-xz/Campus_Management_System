package com.orbithy.cms.service;

import com.orbithy.cms.data.vo.Result;
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
    private UserMapper userMapper;
    @Autowired
    private login login;

    @Value("${spring.secret}")
    private String secret1;

    public ResponseEntity<Result> addTeacher(String SDUId, String password, String username, int permission, String secret) {
        if (!Objects.equals(secret, secret1)) {
            return ResponseUtil.build(Result.error(401, "Authentication failed"));
        }
        if (!login.isExisted(SDUId)) {
            String passwd = BcryptUtils.encrypt(password);
            userMapper.addTeacher(username, passwd, SDUId, permission);
        }
        return ResponseUtil.build(Result.ok());
    }

    public ResponseEntity<Result> getInfo(String userId) {
        System.out.println(userId);
        return ResponseUtil.build(Result.success(userMapper.getUserInfo(userId), "获取成功"));
    }
}
