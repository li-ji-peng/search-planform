package com.lijipeng.search.manager.po;

import java.util.*;


import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ReadBookPdPO{
    /**
     * 
     */
    private Integer id;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private String enName;
    /**
     * 
     */
    private String author;
    /**
     * 
     */
    private String imgurl;
    /**
     * 
     */
    private String discription;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Integer creator;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 1正常，-1删除，0下架
     */
    private Integer status;
    /**
     * 评论数
     */
    private Integer commentNum;
    /**
     * 价格，分
     */
    private Integer price;
    /**
     * 
     */
    private String category;
}