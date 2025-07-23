package dao.imp;

import dao.StatisticDAO;
import entity.StatisticEmployee;
import entity.StatisticProduct;
import entity.StatisticRevenueCost;
import utils.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAOImp implements StatisticDAO {
    @Override
    public List<StatisticRevenueCost> statisticRevenueCostByDate(boolean billType) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<StatisticRevenueCost> statisticRevenueCostList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_revenue_cost_by_date(?)}");
            callableStatement.setBoolean(1, billType);
            ResultSet resultSet = callableStatement.executeQuery();
            statisticRevenueCostList = new ArrayList<>();
            while (resultSet.next()) {
                StatisticRevenueCost statisticRevenueCost = new StatisticRevenueCost();
                statisticRevenueCost.setDate(resultSet.getDate("created").toLocalDate());
                statisticRevenueCost.setTotalAmount(resultSet.getFloat("Tổng số tiền"));
                statisticRevenueCostList.add(statisticRevenueCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticRevenueCostList;
    }

    @Override
    public List<StatisticRevenueCost> statisticRevenueCostByMonth(boolean billType) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<StatisticRevenueCost> statisticRevenueCostList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_revenue_cost_by_month(?)}");
            callableStatement.setBoolean(1, billType);
            ResultSet resultSet = callableStatement.executeQuery();
            statisticRevenueCostList = new ArrayList<>();
            while (resultSet.next()) {
                StatisticRevenueCost statisticRevenueCost = new StatisticRevenueCost();
                statisticRevenueCost.setMonth(resultSet.getInt("Tháng"));
                statisticRevenueCost.setYear(resultSet.getInt("Năm"));
                statisticRevenueCost.setTotalAmount(resultSet.getFloat("Tổng số tiền"));
                statisticRevenueCostList.add(statisticRevenueCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticRevenueCostList;
    }

    @Override
    public List<StatisticRevenueCost> statisticRevenueCostByYear(boolean billType) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<StatisticRevenueCost> statisticRevenueCostList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_revenue_cost_by_year(?)}");
            callableStatement.setBoolean(1, billType);
            ResultSet resultSet = callableStatement.executeQuery();
            statisticRevenueCostList = new ArrayList<>();
            while (resultSet.next()) {
                StatisticRevenueCost statisticRevenueCost = new StatisticRevenueCost();
                statisticRevenueCost.setYear(resultSet.getInt("Năm"));
                statisticRevenueCost.setTotalAmount(resultSet.getFloat("Tổng số tiền"));
                statisticRevenueCostList.add(statisticRevenueCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticRevenueCostList;
    }

    @Override
    public List<StatisticRevenueCost> statisticRevenueCostInRange(boolean billType, LocalDate startDate, LocalDate endDate) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<StatisticRevenueCost> statisticRevenueCostList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_revenue_cost_in_date_range(?,?,?)}");
            callableStatement.setBoolean(1, billType);
            callableStatement.setDate(2, Date.valueOf(startDate));
            callableStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = callableStatement.executeQuery();
            statisticRevenueCostList = new ArrayList<>();
            while (resultSet.next()) {
                StatisticRevenueCost statisticRevenueCost = new StatisticRevenueCost();
                statisticRevenueCost.setDate(resultSet.getDate("created").toLocalDate());
                statisticRevenueCost.setTotalAmount(resultSet.getFloat("Tổng số tiền"));
                statisticRevenueCostList.add(statisticRevenueCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticRevenueCostList;
    }

    @Override
    public List<StatisticEmployee> statisticEmployeeByStatus() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        List<StatisticEmployee> statisticEmployeeList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_employee_by_status()}");
            ResultSet resultSet = callableStatement.executeQuery();
            statisticEmployeeList = new ArrayList<>();
            while (resultSet.next()) {
                StatisticEmployee statisticEmployee = new StatisticEmployee();
                statisticEmployee.setStatus(resultSet.getShort("emp_status"));
                statisticEmployee.setCountEmp(resultSet.getInt("Số nhân viên"));
                statisticEmployeeList.add(statisticEmployee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticEmployeeList;
    }

    @Override
    public StatisticProduct statisticProductMaxTradeInRange(boolean billType, LocalDate startDate, LocalDate endDate) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        StatisticProduct statisticProduct = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_product_max_trade_in_date_range(?,?,?)}");
            callableStatement.setBoolean(1, billType);
            callableStatement.setDate(2, Date.valueOf(startDate));
            callableStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                statisticProduct = new StatisticProduct();
                statisticProduct.setProductName(resultSet.getString("product_name"));
                statisticProduct.setQuantity(resultSet.getInt("Số sản phẩm"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticProduct;
    }

    @Override
    public StatisticProduct statisticProductMinTradeInRange(boolean billType, LocalDate startDate, LocalDate endDate) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        StatisticProduct statisticProduct = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call statistic_product_min_trade_in_date_range(?,?,?)}");
            callableStatement.setBoolean(1, billType);
            callableStatement.setDate(2, Date.valueOf(startDate));
            callableStatement.setDate(3, Date.valueOf(endDate));
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                statisticProduct = new StatisticProduct();
                statisticProduct.setProductName(resultSet.getString("product_name"));
                statisticProduct.setQuantity(resultSet.getInt("Số sản phẩm"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return statisticProduct;
    }
}
