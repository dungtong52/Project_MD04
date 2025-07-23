package entity;

public class StatisticProduct {
    private String productName;
    private int quantity;

    public StatisticProduct() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%-15s: %6d sản phẩm", this.productName, this.quantity);
    }
}
