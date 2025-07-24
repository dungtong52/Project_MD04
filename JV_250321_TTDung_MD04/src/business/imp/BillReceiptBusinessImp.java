package business.imp;

import business.BillReceiptBusiness;
import dao.BillReceiptDAO;
import dao.imp.ReceiptDAOImpImp;
import entity.Bill;
import entity.PaginationResult;

public abstract class BillReceiptBusinessImp implements BillReceiptBusiness {
    private final BillReceiptDAO billReceiptDAO;

    public BillReceiptBusinessImp() {
        billReceiptDAO = new ReceiptDAOImpImp();
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
