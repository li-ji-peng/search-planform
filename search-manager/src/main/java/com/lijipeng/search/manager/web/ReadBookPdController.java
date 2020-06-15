package com.lijipeng.search.manager.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijipeng.search.manager.core.Result;
import com.lijipeng.search.manager.core.ResultGenerator;
import com.lijipeng.search.manager.service.ReadBookPdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/11/14.
*/
@Controller
@RequestMapping("/read/book/pd")
public class ReadBookPdController {
    @Resource
    private ReadBookPdService readBookPdService;
//
//    @PostMapping
//    public Result add(@RequestBody ReadBookPd readBookPd) {
//        readBookPdService.save(readBookPd);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        readBookPdService.deleteById(id);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @PutMapping
//    public Result update(@RequestBody ReadBookPd readBookPd) {
//        readBookPdService.update(readBookPd);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @GetMapping("/{id}")
//    public Result detail(@PathVariable Integer id) {
//        ReadBookPd readBookPd = readBookPdService.findById(id);
//        return ResultGenerator.genSuccessResult(readBookPd);
//    }
    @GetMapping("/indexAll")
    public Result indexAll() {
    	readBookPdService.reIndex();
    	return ResultGenerator.genSuccessResult();
    }

//    @GetMapping
//    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
//        PageHelper.startPage(page, size);
//        List<ReadBookPd> list = readBookPdService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultGenerator.genSuccessResult(pageInfo);
//    }
}
