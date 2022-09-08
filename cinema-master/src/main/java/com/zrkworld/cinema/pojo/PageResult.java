package com.zrkworld.cinema.pojo;

import java.io.Serializable;
import java.util.List;


/**
 * @author lzh
 * 对分页数据的封装
 */
public class PageResult<T> implements Serializable {
    private Long pageTotal;

    @Override
    public String toString() {
        return "PageResult{" +
                "pageTotal=" + pageTotal +
                ", list=" + list +
                '}';
    }

    private List<T> list;
    private static final long serialVersionUID = 1L;
    public Long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
