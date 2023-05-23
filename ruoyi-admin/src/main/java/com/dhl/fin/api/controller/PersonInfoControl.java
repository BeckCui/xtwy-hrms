package com.dhl.fin.api.controller;

import com.dhl.fin.api.common.controller.CommonController;
import com.dhl.fin.api.domain.PersonInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuiJianbo
 * @since 2023/5/10
 */
@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("personinfo")
public class PersonInfoControl extends CommonController<PersonInfo> {


    @Log(title = "新增人员档案", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@Validated @RequestBody PersonInfo user) {

        try {
            commonService.saveDomain(user, "educations");
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

}
