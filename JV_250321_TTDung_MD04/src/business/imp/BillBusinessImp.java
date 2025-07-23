package business.imp;

import business.BaseBillReceiptBusinessImp;
import business.PaginationBusiness;
import dao.BillReceiptDAO;
import dao.imp.BillDAOImp;
import entity.Bill;
import entity.PaginationResult;

public class BillBusinessImp extends BaseBillReceiptBusinessImp{
    private final BillReceiptDAO billReceiptDAO;

    public BillBusinessImp() {
        billReceiptDAO = new BillDAOImp();
    }

    @Override
    public boolean acceptBill(Bill bill) {
        return billReceiptDAO.acceptBill(bill);
    }


}
