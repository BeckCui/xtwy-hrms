package com.dhl.fin.api.domain;

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
@Table(name = "t_education")
public class Education extends BasicDomain {

    @Column(columnDefinition = "varchar(50) comment '毕业院校'")
    private String schoolName;

    @Column(columnDefinition = "varchar(10) comment '学历'")
    private String eduDegree;

    @Column(columnDefinition = "varchar(5) comment '学制'")
    private String eduYear;

    @Column(columnDefinition = "varchar(10) comment '性质'")
    private String eduType;

    @Column(columnDefinition = "varchar(20) comment '专业'")
    private String profession;

    @Column(columnDefinition = "varchar(10) comment '学位'")
    private String degree;

    @Column(columnDefinition = "date comment '教育开始时间'")
    private Date eduStartDate;

    @Column(columnDefinition = "date comment '教育结束时间'")
    private Date eduEndDate;

    @Transient
    private Long[] fileIds;

    @ManyToMany
    @JoinTable(name = "t_education_file",
            joinColumns = {@JoinColumn(name = "education_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private List<AttachFile> attachFiles;

    @ManyToOne
    @JoinColumn(name = "person_info_id")
    private PersonInfo personInfo;

}



