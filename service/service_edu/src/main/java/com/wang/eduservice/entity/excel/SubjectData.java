package com.wang.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(value = "一级课程名称", index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级课程名称", index = 1)
    private String twoSubjectName;
}
