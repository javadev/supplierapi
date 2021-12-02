package com.cs.roomdbapi.utilities;

import org.springframework.data.domain.Sort;

public class Commons {

    public static Sort getOrder(String sortBy, boolean sortDesc) {
        Sort sort;
        if (sortDesc) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy);
        }
        return sort;
    }

    public static Integer getPageSize(Integer pageSize) {
        // All records option
        if (pageSize == -1) {
            pageSize = Integer.MAX_VALUE;
        }
        return pageSize;
    }

}
