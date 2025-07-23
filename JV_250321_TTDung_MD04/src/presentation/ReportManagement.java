package presentation;

import business.StatisticBusiness;
import business.imp.StatisticBusinessImp;
import entity.StatisticEmployee;
import entity.StatisticProduct;
import entity.StatisticRevenueCost;
import validation.Validation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReportManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private final StatisticBusiness statisticBusiness;

    public ReportManagement() {
        statisticBusiness = new StatisticBusinessImp();
    }

    public void reportMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n**************** REPORT MANAGEMENT ***************");
            System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
            System.out.println("2. Thống kê chi phí theo khoảng thời gian");
            System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
            System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
            System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
            System.out.println("6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
            System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
            System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
            System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
            System.out.println("10. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            if (Validation.isIntegerInRange(choice, 1, 10)) {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        statisticBill(true, "chi phí");
                        break;
                    case 2:
                        statisticBillInRange(scanner, true);
                        break;
                    case 3:
                        statisticBill(false, "doanh thu");
                        break;
                    case 4:
                        statisticBillInRange(scanner, false);
                        break;
                    case 5:
                        statisticEmpByStatus();
                        break;
                    case 6:
                        statisticProductInRange(scanner, "max", true);
                        break;
                    case 7:
                        statisticProductInRange(scanner, "min", true);
                        break;
                    case 8:
                        statisticProductInRange(scanner, "max", false);
                        break;
                    case 9:
                        statisticProductInRange(scanner, "min", false);
                        break;
                    default:
                        exit = true;
                }
            } else {
                System.out.println(ANSI_RED + "Nhập vào số nguyên trong phạm vi 1-10" + ANSI_RESET);
            }
        }
    }

    public void statisticBill(boolean billType, String type) {
        System.out.println("-".repeat(20));
        System.out.printf("Thống kê %s theo ngày\n", type);
        List<StatisticRevenueCost> statisticRevenueCosts1 = statisticBusiness.statisticRevenueCostByDate("date", billType);
        statisticRevenueCosts1.forEach(item ->
                System.out.printf("%s: %,.1f\n", item.getDate(), item.getTotalAmount()));
        System.out.println("-".repeat(20));

        System.out.printf("Thống kê %s theo tháng\n", type);
        List<StatisticRevenueCost> statisticRevenueCosts2 = statisticBusiness.statisticRevenueCostByDate("month", billType);
        statisticRevenueCosts2.forEach(item ->
                System.out.printf("%d-%d: %,.1f\n", item.getYear(), item.getMonth(), item.getTotalAmount()));
        System.out.println("-".repeat(20));

        System.out.printf("Thống kê %s theo năm\n", type);
        List<StatisticRevenueCost> statisticRevenueCosts3 = statisticBusiness.statisticRevenueCostByDate("year", billType);
        statisticRevenueCosts3.forEach(item ->
                System.out.printf("%d: %,.1f\n", item.getYear(), item.getTotalAmount()));
        System.out.println("-".repeat(20));
    }

    public void statisticBillInRange(Scanner scanner, boolean billType) {
        while (true) {
            System.out.print("Nhập vào khoảng thời gian cần thống kê. Ngày bắt đầu (yyyy-MM-dd): ");
            String startDate = scanner.nextLine();
            System.out.print("Ngày kết thúc (yyyy-MM-dd): ");
            String endDate = scanner.nextLine();
            if (Validation.isValidDate(startDate, "yyyy-MM-dd") && Validation.isValidDate(endDate, "yyyy-MM-dd")) {
                List<StatisticRevenueCost> statisticRevenueCosts =
                        statisticBusiness.statisticRevenueCostInRange(billType, LocalDate.parse(startDate), LocalDate.parse(endDate));
                statisticRevenueCosts.forEach(item ->
                        System.out.printf("%s: %,.1f", item.getDate(), item.getTotalAmount()));
                break;
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập không đúng định dạng" + ANSI_RESET);
            }
        }
    }

    public void statisticEmpByStatus() {
        System.out.println("Thống kê nhân viên theo trạng thái");
        List<StatisticEmployee> statisticEmployeeList = statisticBusiness.statisticEmployeeByStatus();
        statisticEmployeeList.forEach(System.out::println);
    }

    public void statisticProductInRange(Scanner scanner, String type, boolean billType) {
        while (true) {
            System.out.print("Nhập vào khoảng thời gian cần thống kê. Ngày bắt đầu (yyyy-MM-dd): ");
            String startDate = scanner.nextLine();
            System.out.print("Ngày kết thúc (yyyy-MM-dd): ");
            String endDate = scanner.nextLine();

            if (Validation.isValidDate(startDate, "yyyy-MM-dd") && Validation.isValidDate(endDate, "yyyy-MM-dd")) {
                System.out.println("Sản phẩm cần tìm:");
                StatisticProduct statisticProduct = statisticBusiness.statisticProductInRange(type, billType, LocalDate.parse(startDate), LocalDate.parse(endDate));
                System.out.println(statisticProduct);
                break;
            } else {
                System.out.println(ANSI_RED + "Thông tin nhập không đúng định dạng" + ANSI_RESET);
            }
        }
    }
}
