package business.imp;

import business.BillReceiptDetailsBusiness;
import business.PaginationBusiness;
import dao.BillReceiptDetailDAO;
import dao.imp.BillReceiptDetailsDAOImp;
import entity.BillDetail;
import entity.PaginationResult;

import java.util.List;

public class BillReceiptDetailsBusinessImp implements BillReceiptDetailsBusiness {
    private final BillReceiptDetailDAO billReceiptDetailDAO;

    public BillReceiptDetailsBusinessImp() {
        billReceiptDetailDAO = new BillReceiptDetailsDAOImp();
    }

    @Override
    public boolean createBatchDetails(List<BillDetail> billDetailList) {
        return billReceiptDetailDAO.createBatchDetails(billDetailList);
    }

    @Override
    public BillDetail findBillDetailById(long billDetailId) {
        return billReceiptDetailDAO.findBillDetailById(billDetailId);
    }

    @Override
    public boolean updateBillDetails(BillDetail billDetail) {
        return billReceiptDetailDAO.updateBillDetails(billDetail);
    }

    @Override
    public boolean acceptBill(long billId) {
        return billReceiptDetailDAO.acceptBill(billId);
    }

    @Override
    public PaginationResult<BillDetail> getPaginationData(BillDetail item, int size, int currentPage) {
        return billReceiptDetailDAO.getBillDetailsByBillId(item.getBillId(), size, currentPage);
    }
}
