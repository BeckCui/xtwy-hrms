package com.dhl.fin.api.dao;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.domain.PersonInfo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDao {
    int deleteByPrimaryKey(PersonInfo record);

    int insert(PersonInfo record);

    int insertSelective(PersonInfo record);

    List<PersonInfo> selectPageQuery(QueryDto queryDto);

    List<PersonInfo> selectSelective(QueryDto queryDto);

    int selectCount(QueryDto queryDto);

    PersonInfo selectOne(QueryDto queryDto);

    PersonInfo selectByPrimaryKey(Map data);

    int updateByPrimaryKeySelective(@Param("domain")PersonInfo record, @Param("query")QueryDto queryDto);

    int updateByPrimaryKey(PersonInfo record);
}