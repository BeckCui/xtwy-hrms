package com.dhl.fin.api.domain;

import com.dhl.fin.api.common.annotation.ExcelTitle;
import com.dhl.fin.api.common.domain.AttachFile;
import com.dhl.fin.api.common.domain.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by CuiJianbo on 2023.05.02.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_person_info_his")
public class PersonInfoHis extends BasicDomain {

    @Column(columnDefinition = "varchar(50) comment '姓名'")
    private String name;

    @Column(columnDefinition = "char(2) comment '性别'")
    private String sex;

    @Column(columnDefinition = "date comment '生日'")
    private Date birthday;

    @Column(columnDefinition = "varchar(10) comment '民族'")
    private String nation;

    @Column(columnDefinition = "varchar(10) comment '政治面貌'")
    private String politic;

    @Column(columnDefinition = "varchar(5) comment '血型'")
    private String bloodType;

    @Column(columnDefinition = "varchar(50) comment '岗位职务'")
    private String position;

    @Column(columnDefinition = "date comment '工作/入伍时间'")
    private Date workDate;

    @Column(columnDefinition = "int comment '工作/入伍年限'")
    private Integer workYear;

    @Column(columnDefinition = "varchar(20) comment '专业系列'")
    private String speciality;

    @Column(columnDefinition = "varchar(15) comment '学历学位'")
    private String education;

    @Column(columnDefinition = "varchar(15) comment '在职状态'")
    private String status;

    @Column(columnDefinition = "varchar(20) comment '人员类别'")
    private String type;

    @Column(columnDefinition = "varchar(20) comment '所属科室'")
    private String office;

    @Column(columnDefinition = "varchar(50) comment '待遇级别'")
    private String salaryLevel;


    @Column(columnDefinition = "varchar(50) comment '岗位级别'")
    private String positLevel;


    @Column(columnDefinition = "varchar(50) comment '职务层级'")
    private String workLevel;


    @Column(columnDefinition = "date comment '职务层级开始日期'")
    private Date workLevelDate;


    @Column(columnDefinition = "varchar(10) comment '岗位等级'")
    private String positGrade;

    @Column(columnDefinition = "date comment '岗位等级开始日期'")
    private Date positGradeDate;


    @Column(columnDefinition = "varchar(50) comment '专业技术职务'")
    private String specialityTech;


    @Column(columnDefinition = "date comment '专业技术职务开始日期'")
    private Date specialityTechDate;

    @OneToMany(mappedBy = "personInfoHis")
    private List<EducationHis> eduHistories;

    /**
     * 头像
     */
    @OneToOne
    @JoinColumn(name = "head_picture_id")
    private AttachFile headPicture;


    @Transient
    @ExcelTitle(dateFormat = "YYYY-MM-dd")
    String birthdayStr;

    @Transient
    @ExcelTitle(dateFormat = "YYYY-MM-dd")
    String workDateStr;

}


