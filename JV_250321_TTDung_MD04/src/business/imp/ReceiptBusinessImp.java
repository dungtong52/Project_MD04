package business.imp;

import dao.BillReceiptDAO;
import dao.imp.ReceiptDAOImpImp;
import entity.Bill;

public class ReceiptBusinessImp extends BillReceiptBusinessImp {
    private final BillReceiptDAO billReceiptDAO;

    public ReceiptBusinessImp() {
        billReceiptDAO = new ReceiptDAOImpImp();
    }

    @Override
    public boolean acceptBill(Bill bill) {
        return billReceiptDAO.acceptBill(bill);
    }


}
