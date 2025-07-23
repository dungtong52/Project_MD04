package business;

import entity.Product;

public interface ProductBusiness extends PaginationBusiness<Product>{

    Product getProductById(String productId);

    boolean checkExistProductName(String productName);

    boolean createProduct(Product product);

    boolean updateProduct(Product product);

    boolean updateProductStatus(String productId, boolean status);
}
