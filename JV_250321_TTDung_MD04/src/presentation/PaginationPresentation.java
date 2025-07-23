package presentation;

import business.PaginationBusiness;
import entity.PaginationResult;
import validation.Validation;

import java.util.List;
import java.util.Scanner;

public class PaginationPresentation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private final static int PRODUCT_PER_PAGE = 10;

    public static <T> void getListPagination(Scanner scanner, PaginationBusiness<T> paginationBusiness, String tableName, T item) {
        boolean exit = false;
        boolean printRepeat = true;
        int currentPage = 1;
        int totalPages = 1;

        while (!exit) {
            if (printRepeat) {
                PaginationResult<T> result = paginationBusiness.getPaginationData(item, PRODUCT_PER_PAGE, currentPage);
                List<T> dataList = result.getDataList();
                totalPages = result.getTotalPages();
                int index = (currentPage - 1) * PRODUCT_PER_PAGE + 1;

                PaginationPresentation.printTableHeader(tableName);
                for (T data : dataList) {
                    System.out.printf("| %-5s %s\n", index, data);
                    index++;
                }
                printDivider();
                System.out.printf(" ".repeat(40) + " PREVIOUS | %s%5d%s / %-5d | NEXT | EXIT \n", ANSI_RED, currentPage, ANSI_RESET, totalPages);
            }

            printRepeat = true;
            System.out.println("1. Xem trang trước (PREV)");
            System.out.println("2. Xem trang tiếp theo (NEXT)");
            System.out.println("3. Chọn trang muốn xem");
            System.out.println("4. Thoát (EXIT)");
            System.out.print("Lựa chọn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 4)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        if (currentPage == 1) {
                            System.out.println(ANSI_RED + "Đây đang là trang đầu tiên!" + ANSI_RESET);
                            printRepeat = false;
                        } else {
                            currentPage--;
                        }
                        break;
                    case 2:
                        if (currentPage == totalPages) {
                            System.out.println(ANSI_RED + "Đây đang là trang cuối cùng!" + ANSI_RESET);
                            printRepeat = false;
                        } else {
                            currentPage++;
                        }
                        break;
                    case 3:
                        System.out.print("Nhập vào số trang muốn xem: ");
                        String input = scanner.nextLine();
                        if (Validation.isIntegerInRange(input, 1, totalPages)) {
                            if (Integer.parseInt(input) == currentPage) {
                                System.out.printf(ANSI_RED + "Đây đang là trang %d rồi!\n" + ANSI_RESET, currentPage);
                                printRepeat = false;
                            } else {
                                currentPage = Integer.parseInt(input);
                            }
                        } else {
                            System.out.println(ANSI_RED + "Số nhập vào vượt quá số trang hiện có" + ANSI_RESET);
                            printRepeat = false;
                        }
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Vui lòng chọn từ 1-4" + ANSI_RESET);
                printRepeat = false;
            }
        }
    }

    public static void printTableHeader(String tableName) {
        switch (tableName.toLowerCase()) {
            case "products":
                printDivider();
                System.out.printf("| %-5s | %-10s | %-30s | %-25s | %-15s | %-5s | %-10s | %-20s |\n",
                        "STT", "Product ID", "Product Name", "Manufacturer", "Created", "Batch", "Quantity", "Status");
                printDivider();
                break;
            case "employees":
                printDivider();
                System.out.printf("| %-5s | %-10s | %-15s | %-15s | %-25s | %-10s | %-25s | %-15s |\n",
                        "STT", "Emp ID", "Emp Name", "Birth", "Email", "Phone", "Address", "Status");
                printDivider();
                break;
            case "accounts":
                printDivider();
                System.out.printf("| %-5s | %-15s | %-20s | %-20s | %-15s | %-10s | %-20s | %-15s |\n",
                        "STT", "Acc ID", "User Name", "Password", "Permission", "Emp ID", "Emp Name", "Status");
                printDivider();
                break;
            case "bills":
                printDivider();
                System.out.printf("| %-5s | %-12s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |\n",
                        "STT", "Bill ID", "Bill Code", "Bill Type", "Emp ID Created", "Created", "Emp ID Auth", "Auth Date", "Status");
                printDivider();
                break;
            case "bill details":
                printDivider();
                System.out.printf("| %-5s | %-25s | %-21s | %-25s | %-25s | %-25s |\n",
                        "STT", "Bill Detail ID", "Bill ID", "Product ID", "Quantity", "Price");
                printDivider();
                break;
            default:
                System.out.println(" ");
        }
    }

    public static void printDivider() {
        System.out.println("-".repeat(145));
    }
}