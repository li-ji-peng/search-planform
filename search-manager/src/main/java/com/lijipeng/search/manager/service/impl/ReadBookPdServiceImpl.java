package com.lijipeng.search.manager.service.impl;

import com.lijipeng.search.manager.core.AbstractService;
import com.lijipeng.search.manager.dao.ReadBookPdMapper;
import com.lijipeng.search.manager.po.ReadBookPdPO;
import com.lijipeng.search.manager.service.ElasticService;
import com.lijipeng.search.manager.service.ReadBookPdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

/**
 * Created by CodeGenerator on 2019/11/14.
 */
@Slf4j
@Service
@Transactional
public class ReadBookPdServiceImpl extends AbstractService<ReadBookPdPO> implements ReadBookPdService {
    @Resource
    private ReadBookPdMapper readBookPdMapper;
    @Autowired
    private ElasticService elasticService;

    @Override
    public int getBookCount() {
        Condition condition = new Condition(ReadBookPdPO.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("status", 1);
        return readBookPdMapper.selectCountByCondition(condition);
    }

    @Override
    public List<ReadBookPdPO> getPageList(int page, int size) {
        int start = (page - 1) * size;
        return readBookPdMapper.getPageList(start, size);
    }

    public void reIndex() {
            // 如果数据量达到上亿级那则需要引入大数据处理系统，hadoop，进行离线索引重建
            int total = getBookCount();
            int size = 100;
            int page = total / size;
            if (page > 100)
                page = 100;
            if (total % size != 0)
                page++;
            log.info("books数据库中记录为{}，按size={}，page={}", total, size, page);
            ThreadPoolExecutor executorService =
                    new ThreadPoolExecutor(10, 100, 100l, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
            for (int i = 1; i <= page; i++) {
                final int p = i;
                executorService.execute(() -> {

                    List<ReadBookPdPO> bookPds = getPageList(p, size);
                    if (bookPds != null && bookPds.size() > 0) {
                        Map<Integer, ReadBookPdPO> bookPdMap = bookPds.stream().collect(toMap(ReadBookPdPO::getId, x -> x, (key1, key2) -> key2));
                        try {
                            elasticService.bulkAdd("book", "_doc", bookPdMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.error("books的page={}索引重建失败", p, e);
                        }

                        log.info("books的page={}索引重建成功", p);
                    }

                });
            }
            try {
                executorService.shutdown();
                while (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.info("等待索引重建完成.....");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("对books索引全量重建完成,进行集合的切换");
    }
}
