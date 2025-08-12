package com.sistemasactivos.accountweb.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {

    private List<Account> content;

    private Pageable pageable;

    private boolean last;

    private int totalPages;

    private int totalElements;

    private int size;

    private int number;

    private Sort sort;

    private boolean first;

    private int numberOfElements;

    private boolean empty;
    
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pageable {
        private int pageNumber;
        private int pageSize;
        private Sort sort;
        private int offset;
        private boolean paged;
        private boolean unpaged;
    }
    
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sort {
        private boolean empty;
        private boolean unsorted;
        private boolean sorted;
    }
    
}
