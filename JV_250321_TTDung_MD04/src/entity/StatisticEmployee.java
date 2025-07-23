package entity;

public class StatisticEmployee {
    private short status;
    private int countEmp;

    public StatisticEmployee() {
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getCountEmp() {
        return countEmp;
    }

    public void setCountEmp(int countEmp) {
        this.countEmp = countEmp;
    }

    @Override
    public String toString() {
        return String.format("%-15s: %5d",
                this.status == 0 ? "Hoạt động" : (this.status == 1 ? "Nghỉ chế độ" : "Nghỉ việc"),
                this.countEmp);
    }
}
