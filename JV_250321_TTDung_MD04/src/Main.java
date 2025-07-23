import business.AccountBusiness;
import business.imp.AccountBusinessImp;
import dao.AccountDAO;
import dao.imp.AccountDAOImp;
import entity.Account;
import presentation.AccountManagement;
import presentation.WarehouseManagement;
import validation.Validation;

import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private final AccountBusiness accountBusiness;
    private final WarehouseManagement warehouseManagement;

    public Main() {
        accountBusiness = new AccountBusinessImp();
        warehouseManagement = new WarehouseManagement();
    }

    public boolean accountLogin(Scanner scanner) {
        while (true) {
            System.out.println("*************** LOGIN ***************");
            System.out.print("Nhập tên tài khoản: ");
            String userName = scanner.nextLine();
            System.out.print("Nhập mật khẩu: ");
            String password = scanner.nextLine();

            if (Validation.isValidUserName(userName) && Validation.isValidPassword(password)) {
                Account account = accountBusiness.getAccountToLogin(userName, password);
                if (account != null) {
                    if (account.isAccStatus()) {
                        AccountManagement.currentAccount = account;
                        return account.isPermission();
                    } else {
                        System.out.println(ANSI_RED + "Tài khoản đã bị khóa!" + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Tên tài khoản hoặc mật khẩu không đúng. Vui lòng nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Tên người dùng và mật khẩu không hợp lệ. Vui lòng nhập lại" + ANSI_RESET);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        if (main.accountLogin(scanner)) {
            main.warehouseManagement.warehouseMenuForUser(scanner);
        } else {
            main.warehouseManagement.warehouseMenuForAdmin(scanner);
        }
    }
}
