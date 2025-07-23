package business.imp;

import business.StatisticBusiness;
import dao.StatisticDAO;
import dao.imp.StatisticDAOImp;
import entity.StatisticEmployee;
import entity.StatisticProduct;
import entity.StatisticRevenueCost;

import java.time.LocalDate;
import java.util.List;

public class StatisticBusinessImp implements StatisticBusiness {
    private final StatisticDAO statisticDAO;

    public StatisticBusinessImp() {
        statisticDAO = new StatisticDAOImp();
    }

    @Override
    public List<StatisticRevenueCost> statisticRevenueCostByDate(String type, boolean billType) {
        switch (type) {
            case "date":
                return statisticDAO.statisticRevenueCostByDate(billType);
            case "month":
                return statisticDAO.statisticRevenueCostByMonth(billType);
            case "year":
                return statisticDAO.statisticRevenueCostByYear(billType);
            default:
                return null;
        }
    }

    @Override
    public List<StatisticRevenueCost> statisticRevenueCostInRange(boolean billType, LocalDate startDate, LocalDate endDate) {
        return statisticDAO.statisticRevenueCostInRange(billType, startDate, endDate);
    }

    @Override
    public List<StatisticEmployee> statisticEmployeeByStatus() {
        return statisticDAO.statisticEmployeeByStatus();
    }

    @Override
    public StatisticProduct statisticProductInRange(String key, boolean billType, LocalDate startDate, LocalDate endDate) {
        switch (key) {
            case "max":
                return statisticDAO.statisticProductMaxTradeInRange(billType, startDate, endDate);
            case "min":
                return statisticDAO.statisticProductMinTradeInRange(billType, startDate, endDate);
            default:
                return null;
        }
    }
}
