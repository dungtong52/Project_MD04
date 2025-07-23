package dao.imp;

import dao.AccountDAO;
import entity.Account;
import entity.PaginationResult;
import utils.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImp implements AccountDAO {
    @Override
    public Account getAccountToLogin(String userName, String password) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Account account = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_account_to_login(?,?)}");
            callableStatement.setString(1, userName);
            callableStatement.setString(2, password);

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccId(resultSet.getInt("acc_id"));
                account.setPermission(resultSet.getBoolean("permission"));
                account.setEmpId(resultSet.getString("emp_id"));
                account.setAccStatus(resultSet.getBoolean("acc_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return account;
    }

    @Override
    public PaginationResult<Account> getAccountBySearchKey(Account accountSearch, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<Account> paginationResult = null;
        List<Account> accountList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_list_account_by_search_key(?,?,?,?,?)}");

            callableStatement.setString(1, accountSearch.getUserName() != null ? accountSearch.getUserName() : null);
            callableStatement.setString(2, accountSearch.getEmpName() != null ? accountSearch.getEmpName() : null);
            callableStatement.setInt(3, size);
            callableStatement.setInt(4, currentPage);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            paginationResult = new PaginationResult<>();
            accountList = new ArrayList<>();

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccId(resultSet.getInt("acc_id"));
                account.setUserName(resultSet.getString("user_name"));
                account.setPassword(resultSet.getString("password"));
                account.setPermission(resultSet.getBoolean("permission"));
                account.setEmpId(resultSet.getString("emp_id"));
                account.setEmpName(resultSet.getString("emp_name"));
                account.setAccStatus(resultSet.getBoolean("acc_status"));
                accountList.add(account);
            }
            paginationResult.setTotalPages(callableStatement.getInt(5));
            paginationResult.setDataList(accountList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return paginationResult;
    }

    @Override
    public boolean createAccount(Account account) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call create_account(?,?,?)}");
            callableStatement.setString(1, account.getUserName());
            callableStatement.setString(2, account.getPassword());
            callableStatement.setString(3, account.getEmpId());
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
    public Account getAccountById(int accountId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Account account = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_account_by_id(?)}");
            callableStatement.setInt(1, accountId);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccId(resultSet.getInt("acc_id"));
                account.setUserName(resultSet.getString("user_name"));
                account.setPassword(resultSet.getString("password"));
                account.setPermission(resultSet.getBoolean("permission"));
                account.setEmpId(resultSet.getString("emp_id"));
                account.setEmpName(resultSet.getString("emp_name"));
                account.setAccStatus(resultSet.getBoolean("acc_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return account;
    }

    @Override
    public boolean checkExistAccountName(String accountName) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_account_name(?)}");
            callableStatement.setString(1, accountName);
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
    public boolean checkExistEmpId(String empId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_emp_id(?)}");
            callableStatement.setString(1, empId);
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
    public boolean updateAccountStatus(Account account) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_account_status(?,?)}");
            callableStatement.setInt(1, account.getAccId());
            callableStatement.setBoolean(2, account.isAccStatus());
            int rows = callableStatement.executeUpdate();
            if (rows > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return false;
    }
}
