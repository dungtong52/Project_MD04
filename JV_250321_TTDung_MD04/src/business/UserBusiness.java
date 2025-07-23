package business;

import entity.Bill;
import entity.PaginationResult;

public interface UserBusiness extends PaginationBusiness<Bill> {
    boolean checkExistBillCode(String billCode, boolean billType, String empId);

    boolean updateBillForUser(Bill bill);

    Bill findBillByCodeForUser(String billCode, boolean billType, String empId);
}
