package com.wang.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoursePublishVo {
    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课程数量")
    private Integer lessonNum;

    @ApiModelProperty(value = "一级课程ID")
    private String subjectLevelOne;

    @ApiModelProperty(value = "二级课程ID")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "价格")
    private String price;//只用于显示
}
