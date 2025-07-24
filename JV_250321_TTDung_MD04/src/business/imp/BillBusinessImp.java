package business.imp;

import dao.BillReceiptDAO;
import dao.imp.BillDAOImpImp;
import entity.Bill;

public class BillBusinessImp extends BillReceiptBusinessImp {
    private final BillReceiptDAO billReceiptDAO;

    public BillBusinessImp() {
        billReceiptDAO = new BillDAOImpImp();
    }

    @Override
    public boolean acceptBill(Bill bill) {
        return billReceiptDAO.acceptBill(bill);
    }


}
