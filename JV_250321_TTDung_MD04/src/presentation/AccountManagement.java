package presentation;

import business.AccountBusiness;
import business.EmployeeBusiness;
import business.imp.AccountBusinessImp;
import business.imp.EmployeeBusinessImp;
import entity.Account;
import validation.Validation;

import java.util.Scanner;

public class AccountManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static Account currentAccount = new Account();

    private final int STR_MAX_LENGTH = 30;
    private final int ID_MAX_LENGTH = 5;

    private final AccountBusiness accountBusiness;
    private final EmployeeBusiness employeeBusiness;

    public AccountManagement() {
        accountBusiness = new AccountBusinessImp();
        employeeBusiness = new EmployeeBusinessImp();
    }

    public void accountMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n**************** ACCOUNT MANAGEMENT ***************");
            System.out.println("1. Danh sách tài khoản");
            System.out.println("2. Tạo tài khoản mới");
            System.out.println("3. Cập nhật trạng thái tài khoản");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 5)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        PaginationPresentation.getListPagination(scanner, accountBusiness, "accounts", new Account());
                        break;
                    case 2:
                        createAccount(scanner);
                        break;
                    case 3:
                        updateAccountStatus(scanner);
                        break;
                    case 4:
                        getAccountByName(scanner);
                        System.out.print("Bạn có muốn cập nhật trạng thái tài khoản không? (1. No | 2. Yes): ");
                        while (true) {
                            String numberChoice = scanner.nextLine();
                            if (Validation.isIntegerInRange(numberChoice, 1, 2)) {
                                if (Integer.parseInt(numberChoice) == 2) {
                                    updateAccountStatus(scanner);
                                }
                                break;
                            } else {
                                System.out.println(ANSI_RED + "Lựa chọn không hợp lệ. Hãy chọn 1 hoặc 2!" + ANSI_RESET);
                            }
                        }
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-5" + ANSI_RESET);
            }
        }
    }

    public void createAccount(Scanner scanner) {
        Account account = new Account();
        account.setUserName(inputUserName(scanner));
        account.setPassword(inputPassword(scanner));
        String empId = inputEmpId(scanner);
        if (empId != null) {
            account.setEmpId(empId);
            boolean success = accountBusiness.createAccount(account);
            if (success) {
                System.out.println(ANSI_BLUE + "Tạo mới thành công." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Có lỗi trong quá trình tạo tài khoản!" + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "Không thể tạo mới tài khoản nếu không có mã nhân viên. Vui lòng tạo mới nhân viên trước!" + ANSI_RESET);
        }
    }

    public void updateAccountStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã tài khoản muốn cập nhật: ");
            String accountIdInput = scanner.nextLine();
            if (Validation.isValidType(accountIdInput, "Integer")) {
                int accountId = Integer.parseInt(accountIdInput);
                Account account = accountBusiness.getAccountById(accountId);
                if (account != null) {
                    System.out.printf("Trạng thái hiện tại của tài khoản %d: %s\n", accountId, account.isAccStatus());
                    if (account.getUserName().equals(AccountManagement.currentAccount.getUserName()) || !account.isPermission()) {
                        account.setAccStatus(inputAccStatus(scanner));
                        if (accountBusiness.updateAccountStatus(account)) {
                            System.out.println(ANSI_BLUE + "Cập nhật thành công" + ANSI_RESET);
                            PaginationPresentation.printTableHeader("accounts");
                            System.out.printf("| %-5s %s\n", 1, account);
                            PaginationPresentation.printDivider();
                        } else {
                            System.out.println(ANSI_RED + "Cập nhật thất bại" + ANSI_RESET);
                        }
                    } else {
                        System.out.println(ANSI_RED + "Tài khoản Admin không được phép cập nhật tài khoản Admin khác!" + ANSI_RESET);
                    }
                    break;

                } else {
                    System.out.println(ANSI_RED + "Không tồn tại mã tài khoản này!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public void getAccountByName(Scanner scanner) {
        System.out.print("Có thể tìm tài khoản theo tên đăng nhập hoặc tên nhân viên.\n" +
                "Hãy nhập vào tên để tìm kiếm: ");
        String nameInput = scanner.nextLine();

        Account accountSearch = new Account();
        accountSearch.setUserName(nameInput);
        accountSearch.setEmpName(nameInput);

        PaginationPresentation.getListPagination(scanner, accountBusiness, "accounts", accountSearch);
    }

    public String inputUserName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào tên tài khoản: ");
            String userName = scanner.nextLine();
            if (Validation.isValidLength(userName, STR_MAX_LENGTH)) {
                boolean isExist = accountBusiness.checkExistAccountName(userName);
                if (!isExist) {
                    return userName;
                } else {
                    System.out.println(ANSI_RED + "Tên tài khoản đã tồn tại. Vui lòng nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập vào rỗng hoặc vượt quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, STR_MAX_LENGTH);
            }
        }
    }

    public String inputPassword(Scanner scanner) {
        while (true) {
            System.out.println("Mật khẩu tối thiểu 8 ký tự gồm chữ, số và ký tự %, $.");
            System.out.print("Nhập vào mật khẩu: ");
            String password = scanner.nextLine();
            if (Validation.isValidPassword(password)) {
                return password;
            } else {
                System.out.println(ANSI_RED + "Mật khẩu không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputEmpId(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã nhân viên: ");
            String empId = scanner.nextLine();
            if (Validation.isValidLength(empId, ID_MAX_LENGTH)) {
                boolean isExistInAccountTable = accountBusiness.checkExistEmpId(empId);
                if (!isExistInAccountTable) {
                    if (employeeBusiness.getEmployeeById(empId) != null) {
                        return empId;
                    } else {
                        System.out.println(ANSI_RED + "Không tồn tại mã nhân viên này." + ANSI_RESET);
                        return null;
                    }
                } else {
                    System.out.println(ANSI_RED + "Mã nhân viên này đã tồn tại ở 1 tài khoản khác. Vui lòng nhập mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã nhân viên rỗng hoặc vượt quá 5 ký tự. Vui lòng nhập lại!" + ANSI_RESET);
            }

        }
    }

    public boolean inputAccStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào trạng thái tài khoản (1. Active | 2. Block): ");
            String number = scanner.nextLine();
            if (Validation.isIntegerInRange(number, 1, 2)) {
                return Integer.parseInt(number) == 1;
            } else {
                System.out.println(ANSI_RED + "Trạng thái nhập vào không hợp lệ" + ANSI_RESET);
            }
        }
    }

}
