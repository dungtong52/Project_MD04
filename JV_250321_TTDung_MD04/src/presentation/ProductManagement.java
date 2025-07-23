package presentation;

import business.ProductBusiness;
import business.imp.ProductBusinessImp;
import entity.Product;
import business.PaginationBusiness;
import validation.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ProductManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final int ID_MAX_LENGTH = 5;
    private final int NAME_MAX_LENGTH = 150;
    private final int MANUFAC_MAX_LENGTH = 200;

    private final ProductBusiness productBusiness;

    public ProductManagement() {
        productBusiness = new ProductBusinessImp();
    }

    public void productMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n**************** PRODUCT MANAGEMENT ***************");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Cập nhật trạng thái sản phẩm");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 6)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        PaginationPresentation.getListPagination(scanner, productBusiness, "products", new Product());
                        break;
                    case 2:
                        addProduct(scanner);
                        break;
                    case 3:
                        updateProduct(scanner);
                        break;
                    case 4:
                        getProductByName(scanner);
                        break;
                    case 5:
                        updateProductStatus(scanner);
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-6" + ANSI_RESET);
            }
        }
    }

    public void addProduct(Scanner scanner) {
        Product product = new Product();
        product.setProductId(inputProductIdForCreate(scanner));
        product.setProductName(inputProductName(scanner));
        product.setManufacturer(inputManufacturer(scanner));
        product.setBatch(inputBatch(scanner));
        boolean success = productBusiness.createProduct(product);
        if (success) {
            System.out.println(ANSI_BLUE + "Thêm mới sản phẩm thành công." + ANSI_RESET);
        } else {
            System.err.println(ANSI_RED + "Có lỗi trong quá trình thêm mới sản phẩm." + ANSI_RESET);
        }
    }

    public void updateProduct(Scanner scanner) {
        String productId = inputProductIdForUpdate(scanner);
        Product updateProduct = productBusiness.getProductById(productId);
        PaginationPresentation.printTableHeader("products");
        System.out.printf("| %-5s %s\n", 1, updateProduct);
        PaginationPresentation.printDivider();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Cập nhật tên sản phẩm");
            System.out.println("2. Cập nhật nhà sản xuất");
            System.out.println("3. Cập nhật ngày tạo");
            System.out.println("4. Cập nhật lô chứa sản phẩm");
            System.out.println("5. Thoát cập nhật");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 5)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        updateProduct.setProductName(inputProductName(scanner));
                        break;
                    case 2:
                        updateProduct.setManufacturer(inputManufacturer(scanner));
                        break;
                    case 3:
                        updateProduct.setCreated(inputCreated(scanner));
                        break;
                    case 4:
                        updateProduct.setBatch(inputBatch(scanner));
                        break;
                    default:
                        exit = true;
                }
            }
        }
        if (productBusiness.updateProduct(updateProduct)) {
            System.out.println(ANSI_BLUE + "Cập nhật thành công" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Cập nhật thất bại!" + ANSI_RESET);
        }
    }

    public void getProductByName(Scanner scanner) {
        while (true) {
            Product product = new Product();
            System.out.print("Nhập vào tên tương đối để tìm sản phẩm: ");
            String productName = scanner.nextLine();
            if (Validation.isNotEmpty(productName)) {
                product.setProductName(productName);
                PaginationPresentation.getListPagination(scanner, productBusiness, "products", product);
                break;
            }
            System.out.println(ANSI_RED + "Không được để trống!" + ANSI_RESET);
        }
    }

    public void updateProductStatus(Scanner scanner) {
        String productId = inputProductIdForUpdate(scanner);
        Product updateProduct = productBusiness.getProductById(productId);
        System.out.printf("Trạng thái hiện tại của sản phẩm có ID %s: %s\n",
                updateProduct.getProductId(),
                updateProduct.isProductStatus() ? "Hoạt động" : "Không hoạt động");

        while (true) {
            System.out.print("Cập nhật trạng thái mới (1. Hoạt động | 2. Không hoạt động): ");
            String statusInput = scanner.nextLine();
            if (Validation.isIntegerInRange(statusInput, 1, 2)) {
                boolean status = Integer.parseInt(statusInput) == 1;

                if (productBusiness.updateProductStatus(productId, status)) {
                    updateProduct = productBusiness.getProductById(productId);
                    System.out.println(ANSI_BLUE + "Cập nhật trạng thái thành công" + ANSI_RESET);
                    PaginationPresentation.printTableHeader("products");
                    System.out.printf("| %-5s %s\n", 1, updateProduct);
                    PaginationPresentation.printDivider();
                    break;
                } else {
                    System.out.println(ANSI_RED + "Cập nhật trạng thái thất bại!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Trạng thái nhập vào không đúng!" + ANSI_RESET);
            }
        }
    }

    public String inputProductIdForCreate(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mã sản phẩm: ");
            String productId = scanner.nextLine();
            if (Validation.isValidLength(productId, ID_MAX_LENGTH)) {
                if (productBusiness.getProductById(productId) == null) {
                    return productId;
                } else {
                    System.out.println(ANSI_RED + "Mã sản phẩm này đã tồn tại. Vui lòng nhập mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, ID_MAX_LENGTH);
            }
        }
    }

    public String inputProductIdForUpdate(Scanner scanner) {
        while (true) {
            System.out.print("Nhập mã sản phẩm: ");
            String productId = scanner.nextLine();
            if (Validation.isValidLength(productId, ID_MAX_LENGTH)) {
                if (productBusiness.getProductById(productId) != null) {
                    return productId;
                } else {
                    System.out.println(ANSI_RED + "Mã sản phẩm này KHÔNG tồn tại. Vui lòng nhập mã khác!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, ID_MAX_LENGTH);
            }
        }
    }

    public String inputProductName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();
            if (Validation.isValidLength(productName, NAME_MAX_LENGTH)) {
                if (!productBusiness.checkExistProductName(productName)) {
                    return productName;
                } else {
                    System.out.println(ANSI_RED + "Tên sản phẩm đã tồn tại. Vui lòng nhập nhập lại!" + ANSI_RESET);
                }
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, NAME_MAX_LENGTH);
            }
        }
    }

    public String inputManufacturer(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên nhà sản xuất: ");
            String manufacturer = scanner.nextLine();
            if (Validation.isValidLength(manufacturer, MANUFAC_MAX_LENGTH)) {
                return manufacturer;
            } else {
                System.out.printf(ANSI_RED + "Thông tin nhập không được để trống hoặc có độ dài quá %d ký tự. Vui lòng nhập lại!\n" + ANSI_RESET, MANUFAC_MAX_LENGTH);
            }
        }
    }

    public LocalDate inputCreated(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Nhập vào ngày tạo (yyyy-MM-dd): ");
            String date = scanner.nextLine();
            if (Validation.isValidDate(date, "yyyy-MM-dd")) {
                return LocalDate.parse(date, formatter);
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập vào không đúng định dạng. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

    public short inputBatch(Scanner scanner) {
        while (true) {
            System.out.print("Nhập vào lô chứa sản phẩm: ");
            String batch = scanner.nextLine();
            if (Validation.isValidType(batch, "Short")) {
                return Short.parseShort(batch);
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập vào không đúng định dạng. Vui lòng nhập lại!" + ANSI_RESET);
            }
        }
    }

}
