package dao;

import entity.Bill;
import entity.PaginationResult;

public interface BillReceiptDAO {
    PaginationResult<Bill> getBillBySearchKey(boolean billType, int size, int currentPage);

    boolean checkExistBillCode(String billCode, boolean billType);

    boolean checkExistBillId(long billId, boolean billType);

    boolean createBill(Bill bill);

    Bill findBillByCode(String billCode);

    boolean updateBill(Bill bill);

    boolean acceptBill(Bill bill);

}
