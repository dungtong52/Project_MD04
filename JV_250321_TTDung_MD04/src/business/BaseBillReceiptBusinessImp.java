package business;

import dao.BillReceiptDAO;
import dao.imp.ReceiptDAOImp;
import entity.Bill;
import entity.PaginationResult;

public abstract class BaseBillReceiptBusinessImp implements BillReceiptBusiness {
    private final BillReceiptDAO billReceiptDAO;

    public BaseBillReceiptBusinessImp() {
        billReceiptDAO = new ReceiptDAOImp();
    }

    @Override
    public PaginationResult<Bill> getPaginationData(Bill item, int size, int currentPage) {
        return billReceiptDAO.getBillBySearchKey(item.isBillType(), size, currentPage);
    }

    @Override
    public boolean createBill(Bill bill) {
        return billReceiptDAO.createBill(bill);
    }

    @Override
    public boolean checkExistBillCode(String billCode, boolean billType) {
        return billReceiptDAO.checkExistBillCode(billCode, billType);
    }

    @Override
    public boolean checkExistBillId(long billId, boolean billType) {
        return billReceiptDAO.checkExistBillId(billId, billType);
    }

    @Override
    public Bill findBillByCode(String billCode) {
        return billReceiptDAO.findBillByCode(billCode);
    }

    @Override
    public boolean updateBill(Bill bill) {
        return billReceiptDAO.updateBill(bill);
    }

    @Override
    public abstract boolean acceptBill(Bill bill);
}
