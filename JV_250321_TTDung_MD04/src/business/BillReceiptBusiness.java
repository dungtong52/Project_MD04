package business;

import entity.Bill;
import entity.PaginationResult;

public interface BillReceiptBusiness extends PaginationBusiness<Bill> {
    boolean createBill(Bill bill);

    boolean checkExistBillCode(String billCode, boolean billType);

    boolean checkExistBillId(long billId, boolean billType);

    Bill findBillByCode(String billCode);

    boolean updateBill(Bill bill);

    boolean acceptBill(Bill bill);
}
