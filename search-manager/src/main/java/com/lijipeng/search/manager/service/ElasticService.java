package com.lijipeng.search.manager.service;

import java.io.IOException;
import java.util.Map;

public interface ElasticService {
    /**
     * 批量增加 addTestList方法封装list数据
     * @param index_name
     * @param index_type
     * @param dataMap
     * @throws IOException
     */
    void bulkAdd(String index_name, String index_type, Map<Integer, ? extends Object> dataMap) throws IOException;
}
