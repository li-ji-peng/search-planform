package com.lijipeng.search.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.lijipeng.search.manager.service.ElasticService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class ElasticServiceImpl implements ElasticService {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void bulkAdd(String index_name, String index_type, List<Map<String, Object>> dataList)
            throws IOException {
        String id;
        BulkRequest bulkAddRequest = new BulkRequest();
        for (int i = 0; i < dataList.size(); i++) {
            id=dataList.get(i).get("id").toString();
            IndexRequest indexRequest = new IndexRequest(index_name, index_type, id);
            indexRequest.source(JSON.toJSONString(dataList.get(i)), XContentType.JSON);
            bulkAddRequest.add(indexRequest);
        }
        BulkResponse bulkAddResponse = client.bulk(bulkAddRequest, RequestOptions.DEFAULT);
        log.info("bulkAddResponse: " + JSON.toJSONString(bulkAddResponse));
    }
}
