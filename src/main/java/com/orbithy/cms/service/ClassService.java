package com.orbithy.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orbithy.cms.data.dto.CreateCourseDTO;
import com.orbithy.cms.data.po.Classes;
import com.orbithy.cms.data.vo.Result;
import com.orbithy.cms.mapper.ClassMapper;
import com.orbithy.cms.mapper.UserMapper;
import com.orbithy.cms.utils.ResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClassService {
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 教师创建课程
     */
    public ResponseEntity<Result> createCourse(String teacherId, CreateCourseDTO courseDTO) {
        try {
            // 验证教师权限
            if (userMapper.getPermission(teacherId) != 1) {
                return ResponseUtil.build(Result.error(403, "无权限创建课程"));
            }

            // 验证课序号是否重复
            if (classMapper.selectCount(new QueryWrapper<Classes>()
                    .eq("class_num", courseDTO.getClassNum())) > 0) {
                return ResponseUtil.build(Result.error(400, "课序号已存在"));
            }

            // 创建课程对象
            Classes course = new Classes();
            BeanUtils.copyProperties(courseDTO, course);
            course.setTeacherId(Integer.parseInt(teacherId));
            course.setStatus(Classes.CourseStatus.fromCode(0)); // 设置初始状态为申请中

            // 保存课程
            classMapper.createCourse(course);
            return ResponseUtil.build(Result.success(null, "课程创建成功，等待教务审批"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, "创建课程失败：" + e.getMessage()));
        }
    }

    /**
     * 教务审批课程
     */
    public ResponseEntity<Result> approveCourse(String adminId, Integer courseId, Integer status) {
        try {
            // 验证教务权限
            if (userMapper.getPermission(adminId) != 0) {
                return ResponseUtil.build(Result.error(403, "无权限审批课程"));
            }

            // 验证课程是否存在
            Classes course = classMapper.getCourseById(courseId);
            if (course == null) {
                return ResponseUtil.build(Result.error(404, "课程不存在"));
            }

            // 验证课程状态是否为申请中
            if (course.getStatus().getCode() != 0) {
                return ResponseUtil.build(Result.error(400, "课程状态不正确"));
            }

            // 更新课程状态
            classMapper.updateCourseStatus(courseId, status);
            String message = status == 1 ? "课程审批通过" : "课程审批拒绝";
            return ResponseUtil.build(Result.success(null, message));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, "审批课程失败：" + e.getMessage()));
        }
    }
}
