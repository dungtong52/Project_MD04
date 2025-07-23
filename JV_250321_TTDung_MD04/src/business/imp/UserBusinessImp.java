package business.imp;

import business.UserBusiness;
import dao.UserDAO;
import dao.imp.UserDAOImp;
import entity.Bill;
import entity.PaginationResult;

public class UserBusinessImp implements UserBusiness {
    private final UserDAO userDAO;

    public UserBusinessImp() {
        userDAO = new UserDAOImp();
    }

    @Override
    public PaginationResult<Bill> getPaginationData(Bill item, int size, int currentPage) {
        return userDAO.getAllBillForUser(item, size, currentPage);
    }

    @Override
    public boolean checkExistBillCode(String billCode, boolean billType, String empId) {
        return userDAO.checkExistBillCode(billCode, billType, empId);
    }

    @Override
    public boolean updateBillForUser(Bill bill) {
        return userDAO.updateBillForUser(bill);
    }

    @Override
    public Bill findBillByCodeForUser(String billCode, boolean billType, String empId) {
        return userDAO.findBillByCodeForUser(billCode, billType, empId);
    }
}
