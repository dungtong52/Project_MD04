package dao.imp;

import dao.ProductDAO;
import entity.PaginationResult;
import entity.Product;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements ProductDAO {
    @Override
    public PaginationResult<Product> getProductBySearchKey(String productName, int size, int currentPage) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        PaginationResult<Product> paginationResult = null;
        List<Product> productList = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_list_product_by_key(?,?,?,?)}");
            callableStatement.setString(1, productName);
            callableStatement.setInt(2, size);
            callableStatement.setInt(3, currentPage);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            productList = new ArrayList<>();
            paginationResult = new PaginationResult<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getString("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setManufacturer(resultSet.getString("manufacturer"));
                product.setCreated(resultSet.getDate("created").toLocalDate());
                product.setBatch(resultSet.getShort("batch"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setProductStatus(resultSet.getBoolean("product_status"));
                productList.add(product);
            }
            paginationResult.setTotalPages(callableStatement.getInt(4));
            paginationResult.setDataList(productList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return paginationResult;
    }

    @Override
    public Product getProductById(String productId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Product product = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call get_product_by_id(?)}");
            callableStatement.setString(1, productId);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getString("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setManufacturer(resultSet.getString("manufacturer"));
                product.setCreated(resultSet.getDate("created").toLocalDate());
                product.setBatch(resultSet.getShort("batch"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setProductStatus(resultSet.getBoolean("product_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callableStatement);
        }
        return product;
    }

    @Override
    public boolean checkExistProductName(String productName) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call check_exist_product_name(?)}");
            callableStatement.setString(1, productName);
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
    public boolean createProduct(Product product) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call create_product(?,?,?,?)}");
            callableStatement.setString(1, product.getProductId());
            callableStatement.setString(2, product.getProductName());
            callableStatement.setString(3, product.getManufacturer());
            callableStatement.setShort(4, product.getBatch());
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
    public boolean updateProduct(Product product) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_product(?,?,?,?,?)}");
            callableStatement.setString(1, product.getProductId());
            callableStatement.setString(2, product.getProductName());
            callableStatement.setString(3, product.getManufacturer());
            callableStatement.setDate(4, Date.valueOf(product.getCreated()));
            callableStatement.setShort(5, product.getBatch());
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
    public boolean updateProductStatus(String productId, boolean status) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionDB.openConnection();
            callableStatement = connection.prepareCall("{call update_product_status(?,?)}");
            callableStatement.setString(1, productId);
            callableStatement.setBoolean(2, status);
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

