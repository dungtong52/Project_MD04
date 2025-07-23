package dao;

import entity.Product;
import entity.StatisticEmployee;
import entity.StatisticProduct;
import entity.StatisticRevenueCost;

import java.time.LocalDate;
import java.util.List;

public interface StatisticDAO {
    List<StatisticRevenueCost> statisticRevenueCostByDate(boolean billType);

    List<StatisticRevenueCost> statisticRevenueCostByMonth(boolean billType);

    List<StatisticRevenueCost> statisticRevenueCostByYear(boolean billType);

    List<StatisticRevenueCost> statisticRevenueCostInRange(boolean billType, LocalDate startDate, LocalDate endDate);

    List<StatisticEmployee> statisticEmployeeByStatus();

    StatisticProduct statisticProductMaxTradeInRange(boolean billType, LocalDate startDate, LocalDate endDate);

    StatisticProduct statisticProductMinTradeInRange(boolean billType, LocalDate startDate, LocalDate endDate);
}
