package com.lijipeng.search.manager.service;

import com.lijipeng.search.manager.core.Service;
import com.lijipeng.search.manager.po.ReadBookPdPO;


import java.util.List;

/**
 * Created by lijipeng on 2019/11/14.
 */
public interface ReadBookPdService extends Service<ReadBookPdPO> {

	int getBookCount();
	List<ReadBookPdPO> getPageList(int page, int size);
	void reIndex();
}
