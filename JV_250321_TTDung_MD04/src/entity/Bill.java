package entity;

import java.time.LocalDate;

public class Bill {
    private long billId;
    private String billCode;
    private boolean billType;
    private String empIdCreated;
    private LocalDate created;
    private String empIdAuth;
    private LocalDate authDate;
    private short billStatus;

    public Bill() {
    }

    public Bill(long billId, String billCode, boolean billType, String empIdCreated, LocalDate created, String empIdAuth, LocalDate authDate, short billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreated = empIdCreated;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public String getEmpIdCreated() {
        return empIdCreated;
    }

    public void setEmpIdCreated(String empIdCreated) {
        this.empIdCreated = empIdCreated;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public LocalDate getAuthDate() {
        return authDate;
    }

    public void setAuthDate(LocalDate authDate) {
        this.authDate = authDate;
    }

    public short getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(short billStatus) {
        this.billStatus = billStatus;
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |",
                this.billId, this.billCode, this.billType, this.empIdCreated, this.created,
                this.empIdAuth == null ? "" : this.empIdAuth,
                this.empIdAuth == null ? "" : this.authDate,
                this.billStatus == 0 ? "Tạo" : (this.billStatus == 1 ? "Hủy" : "Duyệt"));
    }
}
