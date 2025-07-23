package dao.imp;

import dao.EmployeeDAO;
import entity.Employee;
import entity.PaginationResult;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImp implements EmployeeDAO {
    @Override
    public PaginationResult<Employee> getEmployeeBySearchKey(String empName, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<Employee> paginationResult = null;
        List<Employee> employeeList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_list_employee_by_search_key(?,?,?,?)}");
            callableStatement.setString(1, empName);
            callableStatement.setInt(2, size);
            callableStatement.setInt(3, currentPage);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            paginationResult = new PaginationResult<>();
            employeeList = new ArrayList<>();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmpId(resultSet.getString("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setBirthOfDate(resultSet.getDate("birth_of_date").toLocalDate());
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setAddress(resultSet.getString("address"));
                employee.setEmpStatus(resultSet.getShort("emp_status"));
                employeeList.add(employee);
            }
            paginationResult.setTotalPages(callableStatement.getInt(4));
            paginationResult.setDataList(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return paginationResult;
    }

    @Override
    public boolean createEmployee(Employee employee) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call create_employee(?,?,?,?,?,?)}");
            callableStatement.setString(1, employee.getEmpId());
            callableStatement.setString(2, employee.getEmpName());
            callableStatement.setDate(3, Date.valueOf(employee.getBirthOfDate()));
            callableStatement.setString(4, employee.getEmail());
            callableStatement.setString(5, employee.getPhone());
            callableStatement.setString(6, employee.getAddress());
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
    public Employee getEmployeeById(String employeeId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Employee employee = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_employee_by_id(?)}");
            callableStatement.setString(1, employeeId);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee();
                employee.setEmpId(resultSet.getString("emp_id"));
                employee.setEmpName(resultSet.getString("emp_name"));
                employee.setBirthOfDate(resultSet.getDate("birth_of_date").toLocalDate());
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setAddress(resultSet.getString("address"));
                employee.setEmpStatus(resultSet.getShort("emp_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return employee;
    }

    @Override
    public boolean checkExistEmployeeName(String employeeName) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_employee_name(?)}");
            callableStatement.setString(1, employeeName);
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
    public boolean updateEmployee(Employee employee) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_employee(?,?,?,?,?,?)}");
            callableStatement.setString(1, employee.getEmpId());
            callableStatement.setString(2, employee.getEmpName());
            callableStatement.setDate(3, Date.valueOf(employee.getBirthOfDate()));
            callableStatement.setString(4, employee.getEmail());
            callableStatement.setString(5, employee.getPhone());
            callableStatement.setString(6, employee.getAddress());
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
    public boolean updateEmployeeStatus(String employeeId, short status) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall("{call update_emp_status(?,?)}");
            callableStatement.setString(1, employeeId);
            callableStatement.setShort(2, status);
            callableStatement.execute();

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
}
