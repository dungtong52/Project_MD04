package presentation;

import business.*;
import business.imp.EmployeeBusinessImp;
import business.imp.ProductBusinessImp;
import business.imp.ReceiptBusinessImp;
import business.imp.BillReceiptDetailsBusinessImp;
import entity.Bill;
import entity.BillDetail;
import entity.Employee;
import entity.Product;
import validation.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReceiptManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final int CODE_MAX_LENGTH = 10;
    private final int STR_MAX_LENGTH = 5;

    private final BillReceiptBusiness receiptBusiness;
    private final BillReceiptDetailsBusiness billReceiptDetailsBusiness;
    private final EmployeeBusiness employeeBusiness;
    private final ProductBusiness productBusiness;


    public ReceiptManagement() {
        receiptBusiness = new ReceiptBusinessImp();
        billReceiptDetailsBusiness = new BillReceiptDetailsBusinessImp();
        employeeBusiness = new EmployeeBusinessImp();
        productBusiness = new ProductBusinessImp();

    }

    public void receiptMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n**************** RECEIPT MANAGEMENT ***************");
            System.out.println("1. Danh sách phiếu nhập");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật thông tin phiếu nhập");
            System.out.println("4. Chi tiết phiếu nhập");
            System.out.println("5. Duyệt phiếu nhập");
            System.out.println("6. Tìm kiếm phiếu nhập");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 7)) {
                String billCode;
                switch (Integer.parseInt(choice)) {
                    case 1:
                        Bill receipt = new Bill();
                        receipt.setBillType(true);
                        PaginationPresentation.getListPagination(scanner, receiptBusiness, "bills", receipt);
                        break;
                    case 2:
                        createReceipt(scanner);
                        break;
                    case 3:
                        billCode = inputExistBillCode(scanner);
                        updateReceipt(scanner, billCode);
                        break;
                    case 4:
                        long billId = inputBillId(scanner);
                        getReceiptDetailsByBillId(scanner, billId);
                        break;
                    case 5:
                        billCode = inputExistBillCode(scanner);
                        acceptReceipt(billCode);
                        break;
                    case 6:
                        billCode = inputExistBillCode(scanner);
                        findReceiptByCode(scanner, billCode);
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-7" + ANSI_RESET);
            }
        }
    }

    public void createReceipt(Scanner scanner) {
        Bill receipt = new Bill();
        String billCode = inputNewBillCode(scanner);

        receipt.setBillCode(billCode);
        receipt.setBillType(false);
        receipt.setEmpIdCreated(AccountManagement.currentAccount.getEmpId());

        boolean success = receiptBusiness.createBill(receipt);
        if (success) {
            receipt = receiptBusiness.findBillByCode(billCode);
            System.out.println(ANSI_BLUE + "Tạo phiếu nhập thành công. Tiếp tục tạo chi tiết phiếu nhập." + ANSI_RESET);
            createReceiptDetails(scanner, receipt.getBillId());
        } else {
            System.out.println(ANSI_RED + "Tạo phiếu xuất thất bại!" + ANSI_RESET);
        }
    }

    public void createReceiptDetails(Scanner scanner, long billId) {
        List<BillDetail> receiptDetailList = new ArrayList<>();
        while (true) {
            System.out.print("Nhập vào số lượng chi tiết phiếu nhập: ");
            String numberInput = scanner.nextLine();
            if (Validation.isValidType(numberInput, "Integer")) {
                int number = Integer.parseInt(numberInput);
                for (int i = 0; i < number; i++) {
                    BillDetail billDetail = new BillDetail();
                    System.out.println("Nhập vào sản phẩm thứ " + (i + 1));
                    billDetail.setBillId(billId);
                    billDetail.setProductId(inputProductId(scanner));
                    billDetail.setQuantity(inputQuantity(scanner));
                    billDetail.setPrice(inputPrice(scanner));
                    System.out.println("**********");

                    receiptDetailList.add(billDetail);
                }
                boolean success = billReceiptDetailsBusiness.createBatchDetails(receiptDetailList);
                if (success) {
                    System.out.println(ANSI_BLUE + "Tạo chi tiết phiếu nhập thành công" + ANSI_RESET);
                    break;
                } else {
                    System.out.println(ANSI_RED + "Tạo chi tiết phiếu nhập thất bại!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Số lượng nhập vào không đúng định dạng. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public void updateReceipt(Scanner scanner, String billCode) {
        Bill receipt = receiptBusiness.findBillByCode(billCode);
        PaginationPresentation.printTableHeader("bills");
        System.out.printf("| %-5s %s\n", 1, receipt);
        PaginationPresentation.printDivider();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Cập nhật mã nhân viên nhập");
            System.out.println("2. Cập nhật ngày tạo phiếu");
            System.out.println("3. Cập nhật mã nhân viên duyệt");
            System.out.println("4. Cập nhật ngày duyệt");
            System.out.println("5. Cập nhật trạng thái phiếu nhập");
            System.out.println("6. Cập nhật chi tiết phiếu nhập");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 7)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        receipt.setEmpIdCreated(inputEmpIdCreated(scanner));
                        break;
                    case 2:
                        receipt.setCreated(inputCreated(scanner));
                        break;
                    case 3:
                        receipt.setEmpIdAuth(inputEmpIdAuth(scanner));
                        break;
                    case 4:
                        receipt.setAuthDate(inputAuthDate(scanner));
                        break;
                    case 5:
                        short status = inputStatus(scanner);
                        if (status != 2) {
                            receipt.setBillStatus(status);
                        } else {
                            System.out.println(ANSI_RED + "Không thể cập nhật trạng thái sang 'Duyệt' ở chức năng này!" + ANSI_RESET);
                        }
                        break;
                    case 6:
                        updateReceiptDetails(scanner, receipt.getBillId());
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Chỉ được nhập vào số nguyên từ 1-7." + ANSI_RESET);
            }
        }
        boolean success = receiptBusiness.updateBill(receipt);
        if (success) {
            System.out.println(ANSI_BLUE + "Cập nhật phiếu nhập thành công." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Cập nhật phiếu nhập thất bại!" + ANSI_RESET);
        }
    }

    public void updateReceiptDetails(Scanner scanner, long billId) {
        getReceiptDetailsByBillId(scanner, billId);

        System.out.print("Nhập vào mã phiếu chi tiết muốn cập nhật: ");
        String receiptDetailId = scanner.nextLine();
        if (Validation.isValidType(receiptDetailId, "Long")) {
            BillDetail billDetail = billReceiptDetailsBusiness.findBillDetailById(Long.parseLong(receiptDetailId));
            boolean exit = false;
            while (!exit) {
                System.out.println("1. Cập nhật mã sản phẩm");
                System.out.println("2. Cập nhật số lượng");
                System.out.println("3. Cập nhật giá");
                System.out.println("4. Thoát");
                System.out.print("Lựa chọn của bạn: ");
                String choice = scanner.nextLine();
                if (Validation.isIntegerInRange(choice, 1, 4)) {
                    switch (Integer.parseInt(choice)) {
                        case 1:
                            billDetail.setProductId(inputProductId(scanner));
                            break;
                        case 2:
                            billDetail.setQuantity(inputQuantity(scanner));
                            break;
                        case 3:
                            billDetail.setPrice(inputPrice(scanner));
                            break;
                        default:
                            exit = true;
                    }
                    boolean success = billReceiptDetailsBusiness.updateBillDetails(billDetail);
                    if (success) {
                        System.out.println(ANSI_BLUE + "Cập nhật chi tiết phiếu nhập thành công." + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "Cập nhật chi tiết phiếu nhập thất bại!" + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Chỉ được nhập vào số nguyên từ 1-4." + ANSI_RESET);
                }
            }
        } else {
            System.out.println(ANSI_RED + "Mã phiếu chi tiết không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
        }
    }

    public void getReceiptDetailsByBillId(Scanner scanner, long billId) {
        BillDetail receiptDetailSearch = new BillDetail();
        receiptDetailSearch.setBillId(billId);

        System.out.println("Danh sách chi tiết phiếu nhập có mã phiếu " + billId);
        PaginationPresentation.getListPagination(scanner, billReceiptDetailsBusiness, "bill details", receiptDetailSearch);
    }

    public void acceptReceipt(String billCode) {
        Bill receipt = receiptBusiness.findBillByCode(billCode);

        receipt.setEmpIdAuth(AccountManagement.currentAccount.getEmpId());
        receipt.setAuthDate(LocalDate.now());

        boolean accepted = receiptBusiness.acceptBill(receipt);
        if (accepted) {
            System.out.println(ANSI_BLUE + "Duyệt phiếu nhập và cập nhật số lượng sản phẩm thành công" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "Cập nhật mã nhân viên duyệt và ngày duyệt thành công." + ANSI_RESET);
        } else {
            System.out.printf(ANSI_RED + "Duyệt phiếu có mã code %s thất bại!\n" + ANSI_RESET, billCode);
        }
    }

    public void findReceiptByCode(Scanner scanner, String billCode) {
        Bill receipt = receiptBusiness.findBillByCode(billCode);
        PaginationPresentation.printTableHeader("bills");
        System.out.printf("| %-5s %s\n", 1, receipt);
        PaginationPresentation.printDivider();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Cập nhật phiếu nhập trên");
            System.out.println("2. Duyệt phiếu nhập trên");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 3)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        updateReceipt(scanner, billCode);
                        break;
                    case 2:
                        acceptReceipt(billCode);
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-3" + ANSI_RESET);
            }
        }

    }

    public long inputBillId(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã phiếu (Bill ID): ");
            String billId = scanner.nextLine();
            if (Validation.isValidType(billId, "Long")) {
                if (receiptBusiness.checkExistBillId(Long.parseLong(billId), true)) {
                    return Long.parseLong(billId);
                } else {
                    System.out.println(ANSI_RED + "Mã Code này KHÔNG tồn tại. Hãy nhập vào mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã code nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputExistBillCode(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã code phiếu nhập (Bill Code): ");
            String billCode = scanner.nextLine();
            if (Validation.isValidLength(billCode, CODE_MAX_LENGTH)) {
                if (receiptBusiness.checkExistBillCode(billCode, true)) {
                    return billCode;
                } else {
                    System.out.println(ANSI_RED + "Mã Code này KHÔNG tồn tại. Hãy nhập vào mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã code nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputNewBillCode(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã code phiếu nhập (Bill Code): ");
            String billCode = scanner.nextLine();
            if (Validation.isValidLength(billCode, CODE_MAX_LENGTH)) {
                if (!receiptBusiness.checkExistBillCode(billCode, true) && !receiptBusiness.checkExistBillCode(billCode, false)) {
                    return billCode;
                } else {
                    System.out.println(ANSI_RED + "Mã Code này đã tồn tại. Hãy nhập vào mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Mã code nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputEmpIdCreated(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã nhân viên nhập: ");
            String empIdCreated = scanner.nextLine();
            if (Validation.isValidLength(empIdCreated, STR_MAX_LENGTH)) {
                Employee employee = employeeBusiness.getEmployeeById(empIdCreated);
                if (employee != null) {
                    return empIdCreated;
                } else {
                    System.out.println(ANSI_RED + "Không tồn tại mã nhân viên này. Vui lòng nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public LocalDate inputCreated(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Nhập vào ngày tạo phiếu (yyyy-MM-dd): ");
            String date = scanner.nextLine();
            if (Validation.isValidDate(date, "yyyy-MM-dd")) {
                return LocalDate.parse(date, formatter);
            } else {
                System.out.println(ANSI_RED + "Ngày nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public String inputEmpIdAuth(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã nhân viên duyệt: ");
            String empIdAuth = scanner.nextLine();
            if (Validation.isValidLength(empIdAuth, STR_MAX_LENGTH)) {
                Employee employee = employeeBusiness.getEmployeeById(empIdAuth);
                if (employee != null) {
                    return empIdAuth;
                } else {
                    System.out.println(ANSI_RED + "Không tồn tại nhân viên này. Vui lòng nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Mã nhân viên nhập vào không được rỗng hoặc vượt quá %d ký tự\n" + ANSI_RESET, STR_MAX_LENGTH);
            }
        }
    }

    public LocalDate inputAuthDate(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Nhập vào ngày duyệt phiếu (yyyy-MM-dd): ");
            String date = scanner.nextLine();
            if (Validation.isValidDate(date, "yyyy-MM-dd")) {
                return LocalDate.parse(date, formatter);
            } else {
                System.out.println(ANSI_RED + "Ngày nhập vào không hợp lệ. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public short inputStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào trạng thái phiếu nhập (0. Tạo || 1. Hủy || 2. Duyệt): ");
            String status = scanner.nextLine();
            if (Validation.isIntegerInRange(status, 0, 2)) {
                return Short.parseShort(status);
            } else {
                System.out.println(ANSI_RED + "Nhập vào số 0 (Tạo) hoặc 1 (Hủy) hoặc 2 (Duyệt)" + ANSI_RESET);
            }
        }
    }

    public String inputProductId(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào mã sản phẩm: ");
            String productId = scanner.nextLine();
            if (Validation.isValidLength(productId, STR_MAX_LENGTH)) {
                Product product = productBusiness.getProductById(productId);
                if (product != null) {
                    return productId;
                } else {
                    System.out.println(ANSI_RED + "Không tồn tại mã sản phẩm này" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Mã sản phẩm không được để trống hoặc vượt quá %d ký tự. Vui lòng nập lại!\n" + ANSI_RESET, STR_MAX_LENGTH);
            }
        }
    }

    public int inputQuantity(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào số lượng: ");
            String quantity = scanner.nextLine();
            if (Validation.isValidType(quantity, "Integer")) {
                return Integer.parseInt(quantity);
            } else {
                System.out.println(ANSI_RED + "Phải nhập vào số nguyên dương. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public float inputPrice(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào giá sản phẩm: ");
            String price = scanner.nextLine();
            if (Validation.isValidType(price, "Float")) {
                return Float.parseFloat(price);
            } else {
                System.out.println(ANSI_RED + "Phải nhập vào số dương. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }
}
