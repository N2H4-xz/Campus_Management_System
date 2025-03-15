package com.orbithy.cms.service;

import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.mapper.UserMapper;
import com.orbithy.cms.utils.BcryptUtils;
import com.orbithy.cms.utils.JWTUtil;
import com.orbithy.cms.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.orbithy.cms.utils.JWTUtil.REFRESH_SECRET_KEY;

@Service
public class login {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取token
     *
     * @param id 用户id
     * @return ResponseEntity<Result>
     */
    public ResponseEntity<Result> getToken(String id) {
        String token;
        String refreshToken;
        try {
            refreshToken = jwtUtil.getToken(id, JWTUtil.REFRESH_EXPIRE_TIME, REFRESH_SECRET_KEY);

            token = jwtUtil.getToken(id, JWTUtil.EXPIRE_TIME, JWTUtil.SECRET_KEY);

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("accessToken", token);
            tokenMap.put("refreshToken", refreshToken);

            return ResponseUtil.build(Result.success(tokenMap, "登陆成功"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, e.getMessage()));
        }
    }

    boolean isExisted(String stuId) {
        Integer count = userMapper.getUserId(stuId);
        return count != null && count > 0;
    }


}
