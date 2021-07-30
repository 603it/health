package com.halo.health.common;

import lombok.Data;

import java.util.List;

/**
 * 统一分页对象
 * @param <T>
 */
@Data
public class Pageing<T> {

    //总记录数
    private Long total;
    //总页数
    private Long pages;
    //每页显示条数
    private Long size;
    //当前页数
    private Long current;

    private List<T> items;

}
