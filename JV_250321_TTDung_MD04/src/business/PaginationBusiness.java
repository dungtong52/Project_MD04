package business;

import entity.PaginationResult;

public interface PaginationBusiness<T> {
    PaginationResult<T> getPaginationData(T item, int size, int currentPage);
}
