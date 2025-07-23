package entity;

import java.util.List;

public class PaginationResult<T> {
    private int totalPages;
    private List<T> dataList;

    public PaginationResult() {
    }

    public PaginationResult(int totalPages, List<T> dataList) {
        this.totalPages = totalPages;
        this.dataList = dataList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
