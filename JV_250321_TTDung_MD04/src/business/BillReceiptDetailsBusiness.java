package business;

import entity.BillDetail;

import java.util.List;

public interface BillReceiptDetailsBusiness extends PaginationBusiness<BillDetail> {

    boolean createBatchDetails(List<BillDetail> billDetailList);

    BillDetail findBillDetailById(long billDetailId);

    boolean updateBillDetails(BillDetail billDetail);

    boolean acceptBill(long billId);
}
