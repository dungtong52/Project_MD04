package dao;

import entity.Bill;
import entity.PaginationResult;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBillReceiptDAO implements BillReceiptDAO {
    @Override
    public PaginationResult<Bill> getBillBySearchKey(boolean billType, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<Bill> billPaginationResult = null;
        List<Bill> billList;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_list_bill_by_search_key(?,?,?,?)}");
            callableStatement.setBoolean(1, billType);
            callableStatement.setInt(2, size);
            callableStatement.setInt(3, currentPage);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            billList = new ArrayList<>();
            billPaginationResult = new PaginationResult<>();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setBillId(resultSet.getLong("bill_id"));
                bill.setBillCode(resultSet.getString("bill_code"));
                bill.setBillType(resultSet.getBoolean("bill_type"));
                bill.setEmpIdCreated(resultSet.getString("emp_id_created"));
                bill.setCreated(resultSet.getDate("created").toLocalDate());
                bill.setEmpIdAuth(resultSet.getString("emp_id_auth"));
                bill.setAuthDate(resultSet.getDate("auth_date").toLocalDate());
                bill.setBillStatus(resultSet.getShort("bill_status"));
                billList.add(bill);
            }
            billPaginationResult.setTotalPages(callableStatement.getInt(4));
            billPaginationResult.setDataList(billList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return billPaginationResult;
    }

    @Override
    public boolean checkExistBillCode(String billCode, boolean billType) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_bill_code(?,?)}");
            callableStatement.setString(1, billCode);
            callableStatement.setBoolean(2, billType);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    @Override
    public boolean checkExistBillId(long billId, boolean billType) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_bill_id(?,?)}");
            callableStatement.setLong(1, billId);
            callableStatement.setBoolean(2, billType);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    @Override
    public boolean createBill(Bill bill) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call create_bill(?,?,?)}");
            callableStatement.setString(1, bill.getBillCode());
            callableStatement.setBoolean(2, bill.isBillType());
            callableStatement.setString(3, bill.getEmpIdCreated());
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
    public Bill findBillByCode(String billCode) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Bill bill = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call find_bill_by_code(?)}");
            callableStatement.setString(1, billCode);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                bill = new Bill();
                bill.setBillId(resultSet.getLong("bill_id"));
                bill.setBillCode(resultSet.getString("bill_code"));
                bill.setBillType(resultSet.getBoolean("bill_type"));
                bill.setEmpIdCreated(resultSet.getString("emp_id_created"));
                bill.setCreated(resultSet.getDate("created").toLocalDate());
                bill.setEmpIdAuth(resultSet.getString("emp_id_auth"));
                bill.setAuthDate(resultSet.getDate("auth_date").toLocalDate());
                bill.setBillStatus(resultSet.getShort("bill_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return bill;
    }

    @Override
    public boolean updateBill(Bill bill) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_bill(?,?,?,?,?,?)}");
            callableStatement.setString(1, bill.getBillCode());
            callableStatement.setString(2, bill.getEmpIdCreated());
            callableStatement.setDate(3, Date.valueOf(bill.getCreated()));
            callableStatement.setString(4, bill.getEmpIdAuth());
            callableStatement.setDate(5, Date.valueOf(bill.getAuthDate()));
            callableStatement.setShort(6, bill.getBillStatus());
            int rows = callableStatement.executeUpdate();
            if (rows > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    public abstract boolean acceptBill(Bill bill);
}
