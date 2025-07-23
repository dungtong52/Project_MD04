package business;

import entity.Employee;

public interface EmployeeBusiness extends PaginationBusiness<Employee> {
    Employee getEmployeeById(String employeeId);

    boolean checkExistEmployeeName(String employeeName);

    boolean createEmployee(Employee employee);

    boolean updateEmployee(Employee employee);

    boolean updateEmployeeStatus(String employeeId, short status);
}
