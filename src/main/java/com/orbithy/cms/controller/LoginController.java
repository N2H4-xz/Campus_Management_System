package com.orbithy.cms.controller;

import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.service.SDUlogin;
import com.orbithy.cms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SDUlogin sduLogin;

    @RequestMapping(value = "/SDUlogin", method = {RequestMethod.POST})
    public ResponseEntity<Result> SDUlogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {
        String stuId = null;
        String password = null;
        try {
            stuId = map.get("stuId").toString();
            password = map.get("password").toString();
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(400,"Bad Requests"));
        }
        return sduLogin.SDUIdentify(stuId,password);
    }
}
