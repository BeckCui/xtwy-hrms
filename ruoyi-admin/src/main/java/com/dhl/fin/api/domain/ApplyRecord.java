package com.dhl.fin.api.domain;

import cn.hutool.core.date.DateTime;
import com.dhl.fin.api.common.domain.BasicDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author CuiJianbo
 * @since 2023/5/10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_apply_record")
public class ApplyRecord extends BasicDomain {

    @Column(columnDefinition = "varchar(50) comment '申请单号'")
    private String applyNum;

    @Column(columnDefinition = "varchar(50) comment '单据状态'")
    private String applyStatus;

    @Column(columnDefinition = "varchar(50) comment '申请人'")
    private String applyPeron;

    @Column(columnDefinition = "varchar(50) comment '申请科室'")
    private String applyOffice;

    @Column(columnDefinition = "varchar(50) comment '审核人'")
    private String auditPerson;

    @Column(columnDefinition = "varchar(50) comment '审核时间'")
    private DateTime auditDate;

    @Column(columnDefinition = "varchar(50) comment '审核科室'")
    private String auditOffice;

    @OneToOne
    @JoinColumn(name = "person_his_id")
    private PersonInfoHis personInfoHis;

}
