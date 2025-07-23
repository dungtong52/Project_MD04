package presentation;

import business.EmployeeBusiness;
import business.imp.EmployeeBusinessImp;
import entity.Employee;
import business.PaginationBusiness;
import validation.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EmployeeManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final int ID_MAX_LENGTH = 5;
    private final int MAX_LENGTH = 100;


    private final EmployeeBusiness employeeBusiness;

    public EmployeeManagement() {
        employeeBusiness = new EmployeeBusinessImp();
    }

    public void employeeMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n**************** EMPLOYEE MANAGEMENT ***************");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Cập nhật trạng thái nhân viên");
            System.out.println("5. Tìm kiếm nhân viên theo tên nhân viên hoặc mã nhân viên");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 6)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        PaginationPresentation.getListPagination(scanner, employeeBusiness, "employees", new Employee());
                        break;
                    case 2:
                        addEmployee(scanner);
                        break;
                    case 3:
                        updateEmployee(scanner);
                        break;
                    case 4:
                        updateEmpStatus(scanner);
                        break;
                    case 5:
                        System.out.println("Chọn cách thức tìm kiếm nhân viên");
                        System.out.println("1. Tìm kiếm theo tên nhân viên (nhập tên tương đối)");
                        System.out.println("2. Tìm kiếm theo mã nhân viên ( nhập mã chính xác");
                        System.out.print("Lựa chọn: ");
                        String numberChoice = scanner.nextLine();
                        if (Validation.isIntegerInRange(numberChoice, 1, 2)) {
                            if (Integer.parseInt(numberChoice) == 1) {
                                getEmployeeByName(scanner);
                            } else {
                                getEmployeeById(scanner);
                            }
                        } else {
                            System.out.println(ANSI_RED + "Số nhập vào không hợp lệ" + ANSI_RESET);
                        }
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-6" + ANSI_RESET);
            }
        }
    }

    public void addEmployee(Scanner scanner) {
        Employee employee = new Employee();
        employee.setEmpId(inputEmployeeIDForCreate(scanner));
        employee.setEmpName(inputEmployeeName(scanner));
        employee.setBirthOfDate(inputBirthOfDate(scanner));
        employee.setEmail(inputEmail(scanner));
        employee.setPhone(inputPhone(scanner));
        employee.setAddress(inputAddress(scanner));
        boolean success = employeeBusiness.createEmployee(employee);
        if (success) {
            System.out.println(ANSI_BLUE + "Thêm mới nhân viên thành công." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Có lỗi trong quá trình thêm mới." + ANSI_RESET);
        }
    }

    public void updateEmployee(Scanner scanner) {
        String employeeId = inputEmployeeIDForUpdate(scanner);
        Employee updateEmployee = employeeBusiness.getEmployeeById(employeeId);
        PaginationPresentation.printTableHeader("employees");
        System.out.printf("| %-5s %s\n", 1, updateEmployee);
        PaginationPresentation.printDivider();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Cập nhật tên nhân viên");
            System.out.println("2. Cập nhật ngày sinh");
            System.out.println("3. Cập nhật email");
            System.out.println("4. Cập nhật số điện thoại");
            System.out.println("5. Cập nhật địa chỉ");
            System.out.println("6. Thoát cập nhật");
            System.out.print("Lựa chọn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 6)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        updateEmployee.setEmpName(inputEmployeeName(scanner));
                        break;
                    case 2:
                        updateEmployee.setBirthOfDate(inputBirthOfDate(scanner));
                        break;
                    case 3:
                        updateEmployee.setEmail(inputEmail(scanner));
                        break;
                    case 4:
                        updateEmployee.setPhone(inputPhone(scanner));
                        break;
                    case 5:
                        updateEmployee.setAddress(inputAddress(scanner));
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên 1-6" + ANSI_RESET);
            }
        }
        if (employeeBusiness.updateEmployee(updateEmployee)) {
            System.out.println(ANSI_BLUE + "Cập nhật thành công" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BLUE + "Cập nhật thất bại!" + ANSI_RESET);
        }
    }

    public void getEmployeeByName(Scanner scanner) {
        while (true) {
            Employee employeeSearch = new Employee();
            System.out.print("Nhập vào tên tương đối để tìm nhân viên: ");
            String employeeName = scanner.nextLine();
            if (Validation.isNotEmpty(employeeName)) {
                employeeSearch.setEmpName(employeeName);
                PaginationPresentation.getListPagination(scanner, employeeBusiness, "employees", employeeSearch);
                break;
            } else {
                System.out.println(ANSI_RED + "Tên nhập vào không được để trống" + ANSI_RESET);
            }
        }
    }

    public void getEmployeeById(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã nhân viên để tìm kiếm: ");
            String employeeId = scanner.nextLine();
            if (Validation.isValidLength(employeeId, ID_MAX_LENGTH)) {
                Employee employee = employeeBusiness.getEmployeeById(employeeId);
                if (employee != null) {
                    PaginationPresentation.printTableHeader("employees");
                    System.out.printf("| %-5s %s\n", 1, employee);
                    PaginationPresentation.printDivider();
                    break;
                } else {
                    System.out.println(ANSI_RED + "Không tìm thấy nhân viên có mã " + employeeId + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Tên nhập vào không được để trống hoặc quá %d ký tự!\n" + ANSI_RESET, ID_MAX_LENGTH);
            }
        }
    }

    public void updateEmpStatus(Scanner scanner) {
        String employeeId = inputEmployeeIDForUpdate(scanner);
        Employee updateEmployee = employeeBusiness.getEmployeeById(employeeId);
        short statusCurrent = updateEmployee.getEmpStatus();
        System.out.printf("Trạng thái hiện tại của nhân viên có ID %s: %s\n",
                updateEmployee.getEmpId(),
                statusCurrent == 0 ? "Hoạt động" : (statusCurrent == 1 ? "Nghỉ chế độ" : "Nghỉ việc"));

        short statusUpdate = inputStatus(scanner);
        if (employeeBusiness.updateEmployeeStatus(employeeId, statusUpdate)) {
            System.out.println(ANSI_BLUE + "Cập nhật trạng thái thành công" + ANSI_RESET);
            PaginationPresentation.printTableHeader("employees");
            System.out.printf("| %-5s %s\n", 1, updateEmployee);
            PaginationPresentation.printDivider();
        } else {
            System.out.println(ANSI_RED + "Cập nhật trạng thái thất bại!" + ANSI_RESET);
        }
    }

    public String inputEmployeeIDForCreate(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mã nhân viên: ");
            String employeeID = scanner.nextLine();
            if (Validation.isValidLength(employeeID, ID_MAX_LENGTH)) {
                if (employeeBusiness.getEmployeeById(employeeID) == null) {
                    return employeeID;
                } else {
                    System.out.println(ANSI_RED + "Mã nhân viên này đã tồn tại. Vui lòng nhập mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, ID_MAX_LENGTH);
            }
        }
    }

    public String inputEmployeeIDForUpdate(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mã nhân viên: ");
            String employeeID = scanner.nextLine();
            if (Validation.isValidLength(employeeID, ID_MAX_LENGTH)) {
                if (employeeBusiness.getEmployeeById(employeeID) != null) {
                    return employeeID;
                } else {
                    System.out.println(ANSI_RED + "Mã nhân viên này KHÔNG tồn tại. Vui lòng nhập mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, ID_MAX_LENGTH);
            }
        }
    }

    public String inputEmployeeName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên nhân viên: ");
            String employeeName = scanner.nextLine();
            if (Validation.isValidLength(employeeName, MAX_LENGTH)) {
                if (!employeeBusiness.checkExistEmployeeName(employeeName)) {
                    return employeeName;
                } else {
                    System.out.println(ANSI_RED + "Tên nhân viên đã tồn tại. Vui lòng nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, MAX_LENGTH);
            }
        }
    }

    public LocalDate inputBirthOfDate(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Nhập vào ngày sinh (yyyy-MM-dd): ");
            String date = scanner.nextLine();
            if (Validation.isValidDate(date, "yyyy-MM-dd")) {
                return LocalDate.parse(date, formatter);
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập vào không đúng định dạng. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputEmail(Scanner scanner) {
        while (true) {
            System.out.print("Nhập địa chỉ email: ");
            String email = scanner.nextLine();
            if (Validation.isValidEmail(email)) {
                return email;
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputPhone(Scanner scanner) {
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            String phone = scanner.nextLine();
            if (Validation.isValidPhone(phone)) {
                return phone;
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputAddress(Scanner scanner) {
        while (true) {
            System.out.print("Nhập địa chỉ: ");
            String address = scanner.nextLine();
            if (Validation.isNotEmpty(address)) {
                return address;
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public short inputStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào trạng thái (0. Hoạt động | 1. Nghỉ chế độ | 2. Nghỉ việc): ");
            String status = scanner.nextLine();
            if (Validation.isIntegerInRange(status, 0, 2)) {
                return Short.parseShort(status);
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên từ 0-2. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }
}
