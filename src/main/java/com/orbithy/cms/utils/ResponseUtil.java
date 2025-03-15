package com.orbithy.cms.utils;

import com.orbithy.cms.data.vo.Result;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<Result> build(Result result) {
        return ResponseEntity
                .status(result.httpStatus())
                .body(result);
    }
}
