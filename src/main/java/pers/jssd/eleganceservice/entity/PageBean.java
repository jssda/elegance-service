package pers.jssd.eleganceservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 *
 * @author jssdjing@gmail.com
 */
@Data
public class PageBean<T> implements Serializable {

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页多少条数据
     */
    private int size;

    /**
     * 根据时间排序字段
     */
    private int sort;

    /**
     * 响应数据
     */
    private List<T> data;

    /**
     * 总共多少页
     */
    private int totaledPage;

    /**
     * 总共多少条数据
     */
    private long totaledSize;

    /**
     * 当前返回了多少条数据
     */
    private int currentSize;

    /**
     * 查询字段, 按照顺序接收字段
     */
    private List<String> sortFiled;

    public PageBean(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageBean(int page, int size, int sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public PageBean(List<T> data, int totaledPage, long totaledSize) {
        this.data = data;
        this.totaledPage = totaledPage;
        this.totaledSize = totaledSize;
        this.currentSize = data.size();
    }

    public static <T> PageBean<T> getPageData(List<T> data, int totaledPage, long totaledSize) {
        return new PageBean<>(data, totaledPage, totaledSize);
    }

    public static <T> PageBean<T> getPageBean(List<T> data, int totaledPage, long totaledSize, int page, int size) {
        PageBean<T> pageBean = new PageBean<>(data, totaledPage, totaledSize);
        pageBean.setPage(page);
        pageBean.setSize(size);
        return pageBean;
    }

}
