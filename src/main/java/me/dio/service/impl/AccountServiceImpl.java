package me.dio.service.impl;

import me.dio.domain.model.Account;
import me.dio.domain.model.User;
import me.dio.domain.repository.AccountRepository;
import me.dio.service.AccountService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private static final Long UNCHANGEABLE_USER_ID = 1L;
    AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public Account findById(String number) {
        return this.accountRepository.findAccountByNumber(number);
    }

    @Override
    public Account create(Account entity) {
        return null;
    }

    @Transactional
    public Account update(String number, Account accountToUpdate) {
        Account account = this.findById(number);
        if (!account.getId().equals(accountToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }
        account.setAgency(accountToUpdate.getAgency());
        account.setBalance(accountToUpdate.getBalance());
        account.setLimit(accountToUpdate.getLimit());
        account.setNumber(accountToUpdate.getNumber());

        return this.accountRepository.save(account);
    }


    @Override
    public void delete(String s) {

    }
    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
