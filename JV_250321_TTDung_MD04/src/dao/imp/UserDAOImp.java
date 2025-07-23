package dao.imp;

import dao.UserDAO;
import entity.Bill;
import entity.PaginationResult;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public PaginationResult<Bill> getAllBillForUser(Bill billSearch, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<Bill> billPaginationResult = null;
        List<Bill> billList;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_all_bill_for_user(?,?,?,?,?,?)}");
            callableStatement.setBoolean(1, billSearch.isBillType());
            callableStatement.setString(2, billSearch.getEmpIdCreated());
            callableStatement.setShort(3, billSearch.getBillStatus());
            callableStatement.setInt(4, size);
            callableStatement.setInt(5, currentPage);
            callableStatement.registerOutParameter(6, Types.INTEGER);
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
            billPaginationResult.setTotalPages(callableStatement.getInt(3));
            billPaginationResult.setDataList(billList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return billPaginationResult;
    }

    @Override
    public boolean checkExistBillCode(String billCode, boolean billType, String empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_bill_code_of_user(?,?,?)}");
            callableStatement.setString(1, billCode);
            callableStatement.setBoolean(2, billType);
            callableStatement.setString(3, empId);
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
    public boolean updateBillForUser(Bill bill) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_bill_for_user(?,?,?,?,?)}");
            callableStatement.setString(1, bill.getBillCode());
            callableStatement.setBoolean(2, bill.isBillType());
            callableStatement.setString(3, bill.getEmpIdCreated());
            callableStatement.setDate(4, Date.valueOf(bill.getCreated()));
            callableStatement.setShort(5, bill.getBillStatus());
            int rows = callableStatement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }

    @Override
    public Bill findBillByCodeForUser(String billCode, boolean billType, String empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Bill bill = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call find_bill_by_code_for_user(?,?,?)}");
            callableStatement.setString(1, billCode);
            callableStatement.setBoolean(2, billType);
            callableStatement.setString(3, empId);
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
}
