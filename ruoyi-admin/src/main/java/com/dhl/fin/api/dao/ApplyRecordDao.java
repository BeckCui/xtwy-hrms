package com.dhl.fin.api.dao;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.domain.ApplyRecord;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ApplyRecordDao {
    int deleteByPrimaryKey(ApplyRecord record);

    int insert(ApplyRecord record);

    int insertSelective(ApplyRecord record);

    List<ApplyRecord> selectPageQuery(QueryDto queryDto);

    List<ApplyRecord> selectSelective(QueryDto queryDto);

    int selectCount(QueryDto queryDto);

    ApplyRecord selectOne(QueryDto queryDto);

    ApplyRecord selectByPrimaryKey(Map data);

    int updateByPrimaryKeySelective(@Param("domain")ApplyRecord record, @Param("query")QueryDto queryDto);

    int updateByPrimaryKey(ApplyRecord record);
}