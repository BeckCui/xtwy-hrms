package com.dhl.fin.api.dao;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.domain.Education;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface EducationDao {
    int deleteByPrimaryKey(Education record);

    int insert(Education record);

    int insertSelective(Education record);

    List<Education> selectPageQuery(QueryDto queryDto);

    List<Education> selectSelective(QueryDto queryDto);

    int selectCount(QueryDto queryDto);

    Education selectOne(QueryDto queryDto);

    int deleteEducationAttachFiles(@Param("educationId")Long educationId, @Param("fileId")Long fileId);

    int insertEducationAttachFiles(@Param("educationId")Long educationId, @Param("fileId")Long fileId);

    int deleteEducationAttachFilesMiddle(@Param("educationId")Long educationId);

    Education selectByPrimaryKey(Map data);

    int updateByPrimaryKeySelective(@Param("domain")Education record, @Param("query")QueryDto queryDto);

    int updateByPrimaryKey(Education record);
}