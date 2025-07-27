package dao;

import entity.Bill;
import entity.Employee;
import entity.PaginationResult;

public interface UserDAO {
    PaginationResult<Bill> getAllBillForUser(Bill billSearch, int size, int currentPage);

    boolean checkExistBillCode(String billCode, boolean billType, String empId);

    boolean updateBillForUser(Bill bill);

    Bill findBillByCodeForUser(String billCode, boolean billType, String empId);

    String updatePassword(int accId, String email, String phone, String oldPassword, String newPassword);
}
