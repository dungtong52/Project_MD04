package dao.imp;

import dao.BillReceiptDetailDAO;
import entity.BillDetail;
import entity.PaginationResult;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillReceiptDetailsDAOImp implements BillReceiptDetailDAO {
    @Override
    public boolean createBatchDetails(List<BillDetail> billDetailList) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            connection.setAutoCommit(false);
            for (BillDetail billDetail : billDetailList) {
                callableStatement = connection.prepareCall("{call create_bill_detail(?,?,?,?)}");
                callableStatement.setLong(1, billDetail.getBillId());
                callableStatement.setString(2, billDetail.getProductId());
                callableStatement.setInt(3, billDetail.getQuantity());
                callableStatement.setFloat(4, billDetail.getPrice());
                callableStatement.executeUpdate();
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    @Override
    public PaginationResult<BillDetail> getBillDetailsByBillId(long billId, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<BillDetail> billDetailPaginationResult = null;
        List<BillDetail> billDetailList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_bill_details_by_bill_id(?,?,?,?)}");
            callableStatement.setLong(1, billId);
            callableStatement.setInt(2, size);
            callableStatement.setInt(3, currentPage);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            billDetailList = new ArrayList<>();
            billDetailPaginationResult = new PaginationResult<>();
            while (resultSet.next()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailId(resultSet.getLong("bill_detail_id"));
                billDetail.setBillId(resultSet.getLong("bill_id"));
                billDetail.setProductId(resultSet.getString("product_id"));
                billDetail.setQuantity(resultSet.getInt("quantity"));
                billDetail.setPrice(resultSet.getFloat("price"));
                billDetailList.add(billDetail);
            }
            billDetailPaginationResult.setTotalPages(callableStatement.getInt(4));
            billDetailPaginationResult.setDataList(billDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return billDetailPaginationResult;
    }

    @Override
    public boolean acceptBill(long billId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call accept_bill(?)}");
            callableStatement.setLong(1, billId);
            int rows = callableStatement.executeUpdate();
            if (rows > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    @Override
    public BillDetail findBillDetailById(long billDetailId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        BillDetail billDetail = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call find_bill_detail_by_id(?)}");
            callableStatement.setLong(1, billDetailId);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                billDetail = new BillDetail();
                billDetail.setBillDetailId(resultSet.getLong("bill_detail_id"));
                billDetail.setBillId(resultSet.getLong("bill_id"));
                billDetail.setProductId(resultSet.getString("product_id"));
                billDetail.setQuantity(resultSet.getInt("quantity"));
                billDetail.setPrice(resultSet.getFloat("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return billDetail;
    }

    @Override
    public boolean updateBillDetails(BillDetail billDetail) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_bill_detail(?,?,?,?)}");
            callableStatement.setLong(1, billDetail.getBillDetailId());
            callableStatement.setString(2, billDetail.getProductId());
            callableStatement.setInt(3, billDetail.getQuantity());
            callableStatement.setFloat(4, billDetail.getPrice());
            int rows = callableStatement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }
}
