package com.briskhu.util.web.result;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 *
 * @author Brisk Hu
 * create on 2020-09-19
 */
@Data
public class PagedData<T> implements Serializable {
    
    /* ---------------------------------------- field ---------------------------------------- */
    /**
     * 总记录数
     */
    private long total;
    /**
     * 每页的记录数
     */
    private int limit;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 当前页码数
     */
    private int page;
    /**
     * 当前页的记录
     */
    private List<T> list = new ArrayList<T>();
    
    /* ---------------------------------------- method ---------------------------------------- */
    
    public PagedData(long total, int pages, int page, int limit, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.page = page;
        this.limit = limit;
        this.list = list;
    }
    
    public PagedData(long total, int page, int limit, List<T> list) {
        this.total = total;
        this.pages = obtainTotalPages(total, limit);
        this.page = page;
        this.limit = limit;
        this.list = list;
    }
    
    /**
     * 获得总页数
     *
     * @param total
     * @param limit
     * @return
     */
    public static int obtainTotalPages(long total, int limit) {
        return (int) ((total % limit) == 0L ? (total / limit) : (total / limit + 1L));
    }
    
    
}
