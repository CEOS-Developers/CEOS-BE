package ceos.backend.global.common.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfo {

    private int pageNum;
    private int limit;
    private int totalPages;
    private long totalElements;

    @Builder
    private PageInfo(int pageNum, int limit, int totalPages, long totalElements) {
        this.pageNum = pageNum;
        this.limit = limit;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static PageInfo of(int pageNum, int limit, int totalPages, long totalElements) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .limit(limit)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }
}
