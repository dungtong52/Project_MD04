package business.imp;

import business.BaseBillReceiptBusinessImp;
import business.PaginationBusiness;
import dao.BillReceiptDAO;
import dao.imp.ReceiptDAOImp;
import entity.Bill;
import entity.PaginationResult;

public class ReceiptBusinessImp extends BaseBillReceiptBusinessImp {
    private final BillReceiptDAO billReceiptDAO;

    public ReceiptBusinessImp() {
        billReceiptDAO = new ReceiptDAOImp();
    }

    @Override
    public boolean acceptBill(Bill bill) {
        return billReceiptDAO.acceptBill(bill);
    }


}
