package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfo {

    private int pageNum;

    private int limit;

    @Builder
    private PageInfo(int pageNum, int limit) {
        this.pageNum = pageNum;
        this.limit = limit;
    }

    public static PageInfo of(int pageNum, int limit) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .limit(limit)
                .build();
    }
}
