package dao;

import entity.BillDetail;
import entity.PaginationResult;

import java.util.List;

public interface BillReceiptDetailDAO {
    boolean createBatchDetails(List<BillDetail> billDetailList);

    PaginationResult<BillDetail> getBillDetailsByBillId(long billId, int size, int currentPage);

    BillDetail findBillDetailById(long billDetailId);

    boolean updateBillDetails(BillDetail billDetail);

    boolean acceptBill(long billId);
}
