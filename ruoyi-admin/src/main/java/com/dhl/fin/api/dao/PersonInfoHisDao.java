package com.dhl.fin.api.dao;

import com.dhl.fin.api.common.dto.QueryDto;
import com.dhl.fin.api.domain.PersonInfoHis;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoHisDao {
    int deleteByPrimaryKey(PersonInfoHis record);

    int insert(PersonInfoHis record);

    int insertSelective(PersonInfoHis record);

    List<PersonInfoHis> selectPageQuery(QueryDto queryDto);

    List<PersonInfoHis> selectSelective(QueryDto queryDto);

    int selectCount(QueryDto queryDto);

    PersonInfoHis selectOne(QueryDto queryDto);

    PersonInfoHis selectByPrimaryKey(Map data);

    int updateByPrimaryKeySelective(@Param("domain")PersonInfoHis record, @Param("query")QueryDto queryDto);

    int updateByPrimaryKey(PersonInfoHis record);
}