package business;

import entity.StatisticEmployee;
import entity.StatisticProduct;
import entity.StatisticRevenueCost;

import java.time.LocalDate;
import java.util.List;

public interface StatisticBusiness {
    List<StatisticRevenueCost> statisticRevenueCostByDate(String type, boolean billType);

    List<StatisticRevenueCost> statisticRevenueCostInRange(boolean billType, LocalDate startDate, LocalDate endDate);

    List<StatisticEmployee> statisticEmployeeByStatus();

    StatisticProduct statisticProductInRange(String type, boolean billType, LocalDate startDate, LocalDate endDate);
}
