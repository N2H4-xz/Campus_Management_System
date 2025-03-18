package com.orbithy.cms.service;

import com.orbithy.cms.data.po.Section;
import com.orbithy.cms.data.po.Status;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.mapper.SectionMapper;
import com.orbithy.cms.mapper.StatusMapper;
import com.orbithy.cms.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private StatusMapper statusMapper;

    public ResponseEntity<Result> addSection(Section section) {
        section.setId(null);
        sectionMapper.insert(section);
        return ResponseUtil.build(Result.ok());
    }

    public ResponseEntity<Result> assign(String grade) {
        int classCount = sectionMapper.getSectionCount(grade);
        if (classCount == 0) {
            return ResponseUtil.build(Result.error(400, "班级不存在"));
        }
        List<Status> statusList = statusMapper.getStatusList(grade);
        List<Integer> sectionList = sectionMapper.getSectionList(grade);
        int index = 0;
        for (Status status : statusList) {
            int cls = sectionList.get(index % classCount);
            statusMapper.updateStudentSection(status.getId(), cls);
            index++;
        }
        return ResponseUtil.build(Result.ok());
    }
}
