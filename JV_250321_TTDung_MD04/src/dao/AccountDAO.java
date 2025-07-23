package dao;

import entity.Account;
import entity.PaginationResult;

public interface AccountDAO {
    Account getAccountToLogin(String userName, String password);

    PaginationResult<Account> getAccountBySearchKey(Account accountSearch, int size, int currentPage);

    boolean createAccount(Account account);

    Account getAccountById(int accountId);

    boolean checkExistAccountName(String accountName);

    boolean checkExistEmpId(String empId);

    boolean updateAccountStatus(Account account);

}
