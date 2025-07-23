package dao.imp;

import dao.AbstractBillReceiptDAO;
import entity.Bill;
import utils.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ReceiptDAOImp extends AbstractBillReceiptDAO {

    @Override
    public boolean acceptBill(Bill bill) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall("{call accept_receipt(?,?,?)}");
            callableStatement.setLong(1, bill.getBillId());
            callableStatement.setString(2, bill.getEmpIdAuth());
            callableStatement.setDate(3, Date.valueOf(bill.getAuthDate()));
            int rows = callableStatement.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return true;
            }
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
}
