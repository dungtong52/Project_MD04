package entity;

import java.time.LocalDate;

public class Employee {
    private String empId;
    private String empName;
    private LocalDate birthOfDate;
    private String email;
    private String phone;
    private String address;
    private short empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, LocalDate birthOfDate, String email, String phone, String address, short empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public LocalDate getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(LocalDate birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(short empStatus) {
        this.empStatus = empStatus;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-15s | %-15s | %-25s | %-10s | %-25s | %-15s |",
                this.empId, this.empName, this.birthOfDate,
                this.email, this.phone, this.address,
                this.empStatus == 0 ? "Hoạt động" : (this.empStatus == 1 ? "Nghỉ chế độ" : "Nghỉ việc"));
    }
}
