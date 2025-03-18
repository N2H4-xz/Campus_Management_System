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
            course.setStatus(Classes.CourseStatus.PENDING); // 设置初始状态为申请中

            // 验证课程时间
            if (!course.isValidTime()) {
                return ResponseUtil.build(Result.error(400, "课程时间设置不合理，请检查周次、课时和时间段"));
            }

            // 验证课程容量
            if (!course.isValidCapacity()) {
                return ResponseUtil.build(Result.error(400, "课程容量必须大于0"));
            }

            // 验证学期格式
            if (!course.isValidTerm()) {
                return ResponseUtil.build(Result.error(400, "学期格式不正确，应为YYYY-YYYY-S格式，例如：2023-2024-1"));
            }

            // 验证必填字段
            if (course.getName() == null || course.getName().trim().isEmpty()) {
                return ResponseUtil.build(Result.error(400, "课程名称不能为空"));
            }
            if (course.getPoint() == null || course.getPoint() <= 0) {
                return ResponseUtil.build(Result.error(400, "学分必须大于0"));
            }
            if (course.getTeacherId() == null) {
                return ResponseUtil.build(Result.error(400, "教师ID不能为空"));
            }
            if (course.getClassroom() == null || course.getClassroom().trim().isEmpty()) {
                return ResponseUtil.build(Result.error(400, "教室不能为空"));
            }
            if (course.getCollege() == null || course.getCollege().trim().isEmpty()) {
                return ResponseUtil.build(Result.error(400, "开课学院不能为空"));
            }
            if (course.getType() == null) {
                return ResponseUtil.build(Result.error(400, "课程类型不能为空"));
            }

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
            course.setStatus(Classes.CourseStatus.fromCode(status));
            classMapper.updateCourseStatus(courseId, status);
            
            String message = status == 1 ? "课程审批通过" : "课程审批拒绝";
            return ResponseUtil.build(Result.success(null, message));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.build(Result.error(400, "无效的课程状态码"));
        } catch (Exception e) {
            return ResponseUtil.build(Result.error(500, "审批课程失败：" + e.getMessage()));
        }
    }
}
