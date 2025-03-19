package com.orbithy.cms.service;

import com.orbithy.cms.data.po.Section;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.mapper.SectionMapper;
import com.orbithy.cms.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;

    public ResponseEntity<Result> addSection(Section section) {
        sectionMapper.insert(section);
        return ResponseUtil.build(Result.ok());
    }
}
