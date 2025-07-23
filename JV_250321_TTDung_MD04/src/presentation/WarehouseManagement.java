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

    private final int CODE_MAX_LENGTH = 10;

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
        System.out.print(ANSI_BLUE);
        System.out.println("-".repeat(23));
        System.out.println("|" + " ".repeat(5) + "HELLO ADMIN" + " ".repeat(5) + "|");
        System.out.println("-".repeat(23));
        System.out.print(ANSI_RESET);
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
                        System.exit(0);
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-7" + ANSI_RESET);
            }
        }
    }

    public void warehouseMenuForUser(Scanner scanner) {
        System.out.print(ANSI_BLUE);
        System.out.println("-".repeat(22));
        System.out.println("|" + " ".repeat(5) + "HELLO USER" + " ".repeat(5) + "|");
        System.out.println("-".repeat(22));
        System.out.print(ANSI_RESET);

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
            System.out.println("9. Thoát");
            System.out.println("*".repeat(53));
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 9)) {
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
                    default:
                        System.exit(0);
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-9" + ANSI_RESET);
            }
        }
    }

    public void getBillByStatus(Scanner scanner, boolean billType) {
        String empId = AccountManagement.currentAccount.getEmpId();
        System.out.print("Nhập vào trạng thái phiếu xuất muốn tìm kiếm (0. Tạo | 1. Hủy | 2. Duyệt): ");
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
            System.out.println("2. Cập nhật trạng thái phiếu xuất");
            System.out.println("3. Cập nhật chi tiết phiếu xuất");
            System.out.println("4. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 4)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        billUpdate.setCreated(billManagement.inputCreated(scanner));
                        break;
                    case 2:
                        short status = billManagement.inputStatus(scanner);
                        if (status != 2) {
                            billUpdate.setBillStatus(status);
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
            System.out.println(ANSI_BLUE + "Cập nhật phiếu nhập thành công." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Cập nhật phiếu nhập thất bại!" + ANSI_RESET);
        }
    }

    public void findBillByCodeOfUser(Scanner scanner, String billCode, boolean billType) {
        Bill billSearch = userBusiness.findBillByCodeForUser(billCode, billType, AccountManagement.currentAccount.getEmpId());
        PaginationPresentation.printTableHeader("bills");
        System.out.printf("| %-5s %s\n", 1, billSearch);
        PaginationPresentation.printDivider();

        while (true) {
            System.out.print("Bạn có muốn cập nhật phiếu nhập này không (1. Có || 2. Không): ");
            String number = scanner.nextLine();
            if (Validation.isIntegerInRange(number, 1, 2)) {
                if (Integer.parseInt(number) == 1) {
                    updateBillOfUser(scanner, billCode, billType);
                } else {
                    break;
                }
            } else {
                System.out.println(ANSI_RED + "Chỉ được nhập vào số nguyên 1 hoặc 2" + ANSI_RESET);
            }
        }
    }

    public String inputBillCode(Scanner scanner, boolean billType) {
        while (true) {
            System.out.println("Nhập vào mã code: ");
            String billCode = scanner.nextLine();
            if (Validation.isValidLength(billCode, CODE_MAX_LENGTH)) {
                if (userBusiness.checkExistBillCode(billCode, billType, AccountManagement.currentAccount.getEmpId())) {
                    return billCode;
                } else {
                    System.out.println(ANSI_RED + "Mã Code này KHÔNG tồn tại. Hãy nhập vào mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã code nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }
}
