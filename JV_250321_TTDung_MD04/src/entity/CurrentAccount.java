package entity;

public class CurrentAccount {
    private int accId;
    private String empId;
    private boolean permission;

    public CurrentAccount() {
    }

    public CurrentAccount(int accId, String empId, boolean permission) {
        this.accId = accId;
        this.empId = empId;
        this.permission = permission;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
