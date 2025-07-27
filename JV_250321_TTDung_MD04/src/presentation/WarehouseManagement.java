package presentation;

import business.UserBusiness;
import business.imp.UserBusinessImp;
import entity.Bill;
import validation.Validation;

import java.util.Scanner;

public class WarehouseManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private final ProductManagement productManagement;
    private final EmployeeManagement employeeManagement;
    private final AccountManagement accountManagement;
    private final ReceiptManagement receiptManagement;
    private final BillManagement billManagement;
    private final ReportManagement reportManagement;
    private final UserBusiness userBusiness;


    public WarehouseManagement() {
        productManagement = new ProductManagement();
        employeeManagement = new EmployeeManagement();
        accountManagement = new AccountManagement();
        receiptManagement = new ReceiptManagement();
        billManagement = new BillManagement();
        reportManagement = new ReportManagement();
        userBusiness = new UserBusinessImp();
    }

    public void warehouseMenuForAdmin(Scanner scanner) {
        System.out.println(ANSI_BLUE + "=======================================");
        System.out.println("|                                     |");
        System.out.println("|            CHÀO ADMIN!              |");
        System.out.println("|     TÔI CÓ THỂ GIÚP GÌ CHO BẠN?     |");
        System.out.println("|                                     |");
        System.out.println("=======================================" + ANSI_RESET);
        while (true) {
            System.out.println("**************** WAREHOUSE MANAGEMENT ***************");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý tài khoản");
            System.out.println("4. Quản lý phiếu nhập");
            System.out.println("5. Quản lý phiếu xuất");
            System.out.println("6. Quản lý báo cáo");
            System.out.println("7. Thoát");
            System.out.println("*".repeat(53));
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 7)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        productManagement.productMenu(scanner);
                        break;
                    case 2:
                        employeeManagement.employeeMenu(scanner);
                        break;
                    case 3:
                        accountManagement.accountMenu(scanner);
                        break;
                    case 4:
                        receiptManagement.receiptMenu(scanner);
                        break;
                    case 5:
                        billManagement.billMenu(scanner);
                        break;
                    case 6:
                        reportManagement.reportMenu(scanner);
                        break;
                    default:
                        goodbye();
                        System.exit(0);
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-7" + ANSI_RESET);
            }
        }
    }

    public void warehouseMenuForUser(Scanner scanner) {
        System.out.println(ANSI_BLUE + "=======================================");
        System.out.println("|                                     |");
        System.out.println("|             CHÀO USER!              |");
        System.out.println("|     TÔI CÓ THỂ GIÚP GÌ CHO BẠN?     |");
        System.out.println("|                                     |");
        System.out.println("=======================================" + ANSI_RESET);

        while (true) {
            System.out.println("**************** WAREHOUSE MANAGEMENT ***************");
            System.out.println("1. Danh sách phiếu nhập theo trạng thái");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật phiếu nhập");
            System.out.println("4. Tìm kiếm phiếu nhập");
            System.out.println("5. Danh sách phiếu xuất theo trạng thái");
            System.out.println("6. Tạo phiếu xuất");
            System.out.println("7. Cập nhật phiếu xuất");
            System.out.println("8. Tìm kiếm phiếu xuất");
            System.out.println("9. Cập nhật mật khẩu tài khoản");
            System.out.println("10. Cập nhật thông tin cá nhân");
            System.out.println("11. Thoát");
            System.out.println("*".repeat(53));
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 11)) {
                String billCode;
                switch (Integer.parseInt(choice)) {
                    case 1:
                        getBillByStatus(scanner, true);
                        break;
                    case 2:
                        receiptManagement.createReceipt(scanner);
                        break;
                    case 3:
                        billCode = inputBillCode(scanner, true);
                        updateBillOfUser(scanner, billCode, true);
                        break;
                    case 4:
                        billCode = inputBillCode(scanner, true);
                        findBillByCodeOfUser(scanner, billCode, true);
                        break;
                    case 5:
                        getBillByStatus(scanner, false);
                        break;
                    case 6:
                        billManagement.createBill(scanner);
                        break;
                    case 7:
                        billCode = inputBillCode(scanner, false);
                        updateBillOfUser(scanner, billCode, false);
                        break;
                    case 8:
                        billCode = inputBillCode(scanner, false);
                        findBillByCodeOfUser(scanner, billCode, false);
                        break;
                    case 9:
                        updatePassword(scanner);
                        break;
                    case 10:
                        String empId = AccountManagement.currentAccount.getEmpId();
                        employeeManagement.updateEmployee(scanner, empId);
                        break;
                    default:
                        goodbye();
                        System.exit(0);
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-11" + ANSI_RESET);
            }
        }
    }

    public void getBillByStatus(Scanner scanner, boolean billType) {
        String empId = AccountManagement.currentAccount.getEmpId();
        System.out.printf("Nhập vào trạng thái phiếu '%s' muốn tìm kiếm (0. Tạo | 1. Hủy | 2. Duyệt): ", billType ? "Nhập" : "Xuất");
        String status = scanner.nextLine();
        if (Validation.isIntegerInRange(status, 0, 2)) {
            Bill receipt = new Bill();
            receipt.setEmpIdCreated(empId);
            receipt.setBillStatus(Short.parseShort(status));
            receipt.setBillType(billType);

            PaginationPresentation.getListPagination(scanner, userBusiness, "bills", receipt);
        } else {
            System.out.println(ANSI_RED + "Trạng thái nhập vào không hợp lệ. Nhập số từ 0-2" + ANSI_RESET);
        }
    }

    public void updateBillOfUser(Scanner scanner, String billCode, boolean billType) {
        Bill billUpdate = userBusiness.findBillByCodeForUser(billCode, billType, AccountManagement.currentAccount.getEmpId());
        PaginationPresentation.printTableHeader("bills");
        System.out.printf("| %-5s %s\n", 1, billUpdate);
        PaginationPresentation.printDivider();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Cập nhật ngày tạo phiếu");
            System.out.println("2. Cập nhật trạng thái phiếu");
            System.out.println("3. Cập nhật chi tiết phiếu");
            System.out.println("4. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 4)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        billUpdate.setCreated(billManagement.inputCreated(scanner));
                        System.out.println(ANSI_BLUE + "Đã đổi ngày tạo phiếu. Thoát cập nhật để lưu thay đổi!" + ANSI_RESET);
                        break;
                    case 2:
                        short status = billManagement.inputStatus(scanner);
                        if (status != 2) {
                            billUpdate.setBillStatus(status);
                            System.out.println(ANSI_BLUE + "Đã đổi trạng thái. Thoát cập nhật để lưu thay đổi!" + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_RED + "Không thể cập nhật trạng thái sang 'Duyệt' ở chức năng này!" + ANSI_RESET);
                        }
                        break;
                    case 3:
                        billManagement.updateBillDetails(scanner, billUpdate.getBillId());
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Chỉ được nhập vào số nguyên từ 1-7." + ANSI_RESET);
            }
        }
        boolean success = userBusiness.updateBillForUser(billUpdate);
        if (success) {
            System.out.println(ANSI_BLUE + "Cập nhật phiếu thành công." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Cập nhật phiếu thất bại!" + ANSI_RESET);
        }
    }

    public void findBillByCodeOfUser(Scanner scanner, String billCode, boolean billType) {
        Bill billSearch = userBusiness.findBillByCodeForUser(billCode, billType, AccountManagement.currentAccount.getEmpId());
        PaginationPresentation.printTableHeader("bills");
        System.out.printf("| %-5s %s\n", 1, billSearch);
        PaginationPresentation.printDivider();

        while (true) {
            System.out.print("Bạn có muốn cập nhật phiếu này không (1. Có || 2. Không): ");
            String number = scanner.nextLine();
            if (Validation.isIntegerInRange(number, 1, 2)) {
                if (Integer.parseInt(number) == 1) {
                    updateBillOfUser(scanner, billCode, billType);
                }
                break;
            } else {
                System.out.println(ANSI_RED + "Chỉ được nhập vào số nguyên 1 hoặc 2" + ANSI_RESET);
            }
        }
    }

    public String inputBillCode(Scanner scanner, boolean billType) {
        int CODE_MAX_LENGTH = 10;
        while (true) {
            System.out.print("Nhập vào mã code (Bill Code): ");
            String billCode = scanner.nextLine();

            if (Validation.isValidLength(billCode, CODE_MAX_LENGTH)) {
                if (userBusiness.checkExistBillCode(billCode, billType, AccountManagement.currentAccount.getEmpId())) {
                    return billCode;
                } else {
                    System.out.println(ANSI_RED + "Mã Code này KHÔNG tồn tại. Hãy nhập vào mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã code nhập vào KHÔNG hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public void updatePassword(Scanner scanner) {
        int accId = AccountManagement.currentAccount.getAccId();
        System.out.println(ANSI_YELLOW + "Mật khẩu mới" + ANSI_RESET);
        String newPassword = accountManagement.inputPassword(scanner);
        System.out.println(ANSI_YELLOW + "Xác nhận thông tin hiện tại" + ANSI_RESET);
        String oldPassword = accountManagement.inputPassword(scanner);
        String email = employeeManagement.inputEmail(scanner);
        String phone = employeeManagement.inputPhone(scanner);

        String errorMsg = userBusiness.updatePassword(accId, email, phone, oldPassword, newPassword);
        if (errorMsg == null) {
            System.out.println(ANSI_BLUE + "Cập nhật password thành công." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Cập nhật password thất bại." + ANSI_RESET);
            System.out.println(ANSI_RED + errorMsg + ANSI_RESET);
        }
    }

    public void goodbye() {
        System.out.println(ANSI_BLUE + "==================================");
        System.out.println("|                                |");
        System.out.println("|      TẠM BIỆT BẠN NHÉ!         |");
        System.out.println("|    CẢM ƠN BẠN ĐÃ SỬ DỤNG!      |");
        System.out.println("|                                |");
        System.out.println("==================================");
    }
}
