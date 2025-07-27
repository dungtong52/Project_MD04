package business.imp;

import dao.BillReceiptDAO;
import dao.imp.BillDAOImp;
import entity.Bill;

public class BillBusinessImp extends BillReceiptBusinessImp {
    private final BillReceiptDAO billReceiptDAO;

    public BillBusinessImp() {
        billReceiptDAO = new BillDAOImp();
    }

    @Override
    public boolean acceptBill(Bill bill) {
        return billReceiptDAO.acceptBill(bill);
    }


}
