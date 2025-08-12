package com.sistemasactivos.apirest.bff.model;

//import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;


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
        
//        public Pageable(PageCustomer.Pageable pageable){
//            this.pageNumber = pageable.getPageNumber();
//            this.pageSize = pageable.getPageSize();
//            this.sort = new Sort(pageable.getSort());
//            this.offset = pageable.getOffset();
//            this.paged = pageable.isPaged();
//            this.unpaged = pageable.isUnpaged();
//        }
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sort {
        private boolean empty;
        private boolean unsorted;
        private boolean sorted;

//        public Sort(PageCustomer.Sort sort){
//            this.empty = sort.isEmpty();
//            this.unsorted = sort.isUnsorted();
//            this.sorted = sort.isSorted();
//        }
    }
    
//    public PageResponse(List<Account> content, PageCustomer costumer) {
//        this.content = content;
//        this.pageable = new Pageable(costumer.getPageable());
//        this.last = costumer.isLast();
//        this.totalPages = costumer.getTotalPages();
//        this.totalElements = costumer.getTotalElements();
//        this.size = costumer.getSize();
//        this.number = costumer.getNumber();
//        this.sort = new Sort(costumer.getSort());
//        this.first = costumer.isFirst();
//        this.numberOfElements = costumer.getNumberOfElements();
//        this.empty = costumer.isEmpty();
//    }
}
