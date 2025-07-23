package business;

import entity.Account;
import entity.PaginationResult;

public interface AccountBusiness extends PaginationBusiness<Account> {
    Account getAccountToLogin(String userName, String password);

    boolean createAccount(Account account);

    Account getAccountById(int accountId);

    boolean checkExistAccountName(String accountName);

    boolean checkExistEmpId(String empId);

    boolean updateAccountStatus(Account account);
}
