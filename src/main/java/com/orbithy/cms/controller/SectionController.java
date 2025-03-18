package com.orbithy.cms.controller;

import com.orbithy.cms.annotation.Admin;
import com.orbithy.cms.data.po.Section;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    /**
     * 添加班级
     *
     * @param section 班级信息
     * @return ResponseEntity<Result>
     */
    @PostMapping("/addSection")
    @Admin
    public ResponseEntity<Result> addSection(Section section) {
        return sectionService.addSection(section);
    }

    /**
     * 分配人员
     *
     * @return ResponseEntity<Result>
     */
    @PostMapping("/assign")
    @Admin
    public ResponseEntity<Result> assign(String grade) {
        return sectionService.assign(grade);
    }
}
