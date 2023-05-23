package com.dhl.fin.api.dao;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.domain.EducationHis;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface EducationHisDao {
    int deleteByPrimaryKey(EducationHis record);

    int insert(EducationHis record);

    int insertSelective(EducationHis record);

    List<EducationHis> selectPageQuery(QueryDto queryDto);

    List<EducationHis> selectSelective(QueryDto queryDto);

    int selectCount(QueryDto queryDto);

    EducationHis selectOne(QueryDto queryDto);

    int deleteEducationHisAttachFiles(@Param("educationId")Long educationId, @Param("fileId")Long fileId);

    int insertEducationHisAttachFiles(@Param("educationId")Long educationId, @Param("fileId")Long fileId);

    int deleteEducationHisAttachFilesMiddle(@Param("educationHisId")Long educationHisId);

    EducationHis selectByPrimaryKey(Map data);

    int updateByPrimaryKeySelective(@Param("domain")EducationHis record, @Param("query")QueryDto queryDto);

    int updateByPrimaryKey(EducationHis record);
}