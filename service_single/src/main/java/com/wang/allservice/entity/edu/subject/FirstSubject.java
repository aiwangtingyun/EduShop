package com.wang.allservice.entity.edu.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 一级课程分类
@Data
public class FirstSubject {

    private String id;
    private String title;

    // 一个一级分类包含多个二级分类
    private List<SecondSubject> children = new ArrayList<>();
}
