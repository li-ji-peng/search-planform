package com.lijipeng.search.manager.dao;

import com.lijipeng.search.manager.core.Mapper;
import com.lijipeng.search.manager.po.ReadBookPdPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReadBookPdMapper extends Mapper<ReadBookPdPO> {
    List<ReadBookPdPO> getPageList(@Param("start") Integer start, @Param("size") Integer size);
}
