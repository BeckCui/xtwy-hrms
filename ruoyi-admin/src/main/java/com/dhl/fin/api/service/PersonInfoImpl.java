package com.dhl.fin.api.service;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.common.service.CommonService;
import com.dhl.fin.api.common.util.CollectorUtil;
import com.dhl.fin.api.domain.Education;
import com.dhl.fin.api.domain.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonInfoImpl extends CommonService<PersonInfo> {

    @Autowired
    private PersonEduImpl personEduImpl;

    @Override
    public void afterSave(PersonInfo domain) {
        if (CollectorUtil.isNoTEmpty(domain.getEducations())) {
            domain.getEducations().forEach(p -> {
                try {
                    personEduImpl.saveDomain(p);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    @Override
    public void afterGet(PersonInfo domain) throws Exception {
        if (CollectorUtil.isNoTEmpty(domain.getEducations())) {
            String ids = domain.getEducations().stream().map(Education::getId)
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            List<Education> educations = personEduImpl.select(QueryDto
                    .builder()
                    .available()
                    .addJoinDomain("attachFiles")
                    .addWhere("education.id in (" + ids + ")")
                    .build());
            domain.setEducations(educations);
        }

    }
}
