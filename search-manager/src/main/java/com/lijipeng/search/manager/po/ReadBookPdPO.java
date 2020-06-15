package com.lijipeng.search.manager.po;

import java.util.*;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Table(name = "read_book_pd")
public class ReadBookPdPO{
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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