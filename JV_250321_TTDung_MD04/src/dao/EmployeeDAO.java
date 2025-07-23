package dao;

import entity.Employee;
import entity.PaginationResult;

public interface EmployeeDAO {
    PaginationResult<Employee> getEmployeeBySearchKey(String empName, int size, int currentPage);

    boolean createEmployee(Employee employee);

    Employee getEmployeeById(String employeeId);

    boolean checkExistEmployeeName(String employeeName);

    boolean updateEmployee(Employee employee);

    boolean updateEmployeeStatus(String employeeId, short status);
}
