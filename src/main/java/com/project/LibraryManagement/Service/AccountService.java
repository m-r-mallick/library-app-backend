package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Account;
import com.project.LibraryManagement.Entity.BookItem;
import com.project.LibraryManagement.Entity.Role;
import com.project.LibraryManagement.Model.AccountStatus;
import com.project.LibraryManagement.Repository.AccountRepository;
import com.project.LibraryManagement.Repository.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.LibraryManagement.Model.Constants.DAY;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountRepository.findAll());
    }

    public String addAccount(Account account) {
        String ogPwd = account.getPassword();
        String newPwd = passwordEncoder.encode(ogPwd);                             // Encrypting password before saving in DB...

        account.setPassword(newPwd);
        account.setDateOfMembership(new java.sql.Date(System.currentTimeMillis()));
        account.setStatus(AccountStatus.Active);
        account.setFineUnPaid(0.0);
        account.setFinePaid(0.0);
        account.setTotalBooksCheckedOut(0);
        accountRepository.save(account);
        return account.toString() + " added!";
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public String closeAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && account.getStatus() != AccountStatus.Closed) {
            account.setStatus(AccountStatus.Closed);
            accountRepository.save(account);
            return "Account closed!";
        } else if (account != null && account.getStatus() == AccountStatus.Closed) {
            return "Account already closed!";
        }
        return "Account not found!";
    }

    public String cancelAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && account.getStatus() != AccountStatus.Canceled) {
            account.setStatus(AccountStatus.Canceled);
            accountRepository.save(account);
            return "Account cancelled!";
        } else if (account != null && account.getStatus() == AccountStatus.Canceled) {
            return "Account already cancelled!";
        }
        return "Account not found!";
    }

    public String blacklistAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && account.getStatus() != AccountStatus.Blacklisted) {
            account.setStatus(AccountStatus.Blacklisted);
            accountRepository.save(account);
            return "Account blacklisted!";
        } else if (account != null && account.getStatus() == AccountStatus.Blacklisted) {
            return "Account already blacklisted!";
        }
        return "Account not found!";
    }

    public String reactivateAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && account.getStatus() != AccountStatus.Active) {
            account.setStatus(AccountStatus.Active);
            accountRepository.save(account);
            return "Account re-activated!";
        } else if (account != null && account.getStatus() == AccountStatus.Active) {
            return "Account already active!";
        } else {
            return "Account not found!";
        }
    }

    public List<Account> getListOfUnpaidFines() {
        return accountRepository.findAll().stream().filter(
                account -> account.getFineUnPaid() > 0
        ).collect(Collectors.toList());
    }


    public List<BookItem> getBooksIssued(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            return account.getIssuedBooks();
        } else {
            return null;
        }
    }

    public Account editAccount(Long id, Account account) {
        Account ogAccount = accountRepository.findAll().stream()
                .filter(copy -> copy.getId().equals(id))
                .findAny().orElse(null);
        if (ogAccount != null) {

            String ogPwd = account.getPassword();
            String newPwd = passwordEncoder.encode(ogPwd);

            ogAccount.setUsername(account.getUsername());
            ogAccount.setPassword(newPwd);
            accountRepository.save(ogAccount);
            return ogAccount;
        } else {
            return null;
        }
    }

    public List<Role> getRoles(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            Collection<Role> roles = account.getRoles();
            return new ArrayList<>(roles);
        }
        return new ArrayList<>();
    }

    public String addRole(Long accountId, Role role) throws NotFoundException {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            Collection<Role> roles = account.getRoles();
            Role r = roleRepository.findAll().stream()
                    .filter(anyRole -> anyRole.getRole().equals(role.getRole()))
                    .findAny().orElse(null);
            if (r != null) {
                if (!roles.contains(r)) {
                    roles.add(r);
                    account.setRoles(roles);
                    accountRepository.save(account);
                    return "Roles added!";
                } else {
                    return r.toString() + " already assigned to user!";
                }
            } else {
                throw new NotFoundException(role.toString() + " not found in repository!");
            }

        } else {
            throw new NotFoundException("Account by id " + accountId + " not found in repository!");
        }
    }

    public String deleteRole(Long accountId, Role role) throws NotFoundException {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            Collection<Role> roles = account.getRoles();
            Role r = roleRepository.findAll().stream()
                    .filter(anyRole -> anyRole.getRole().equals(role.getRole()))
                    .findAny().orElse(null);
            if (r != null) {
                if (roles.contains(r)) {
                    roles.remove(r);
                    account.setRoles(roles);
                    accountRepository.save(account);
                    return "Roles removed!";
                } else {
                    throw new NotFoundException(role.toString() + " not found!");
                }
            } else {
                throw new NotFoundException(role.toString() + " not found in repository!");
            }

        } else {
            throw new NotFoundException("Account by id " + accountId + " not found in repository!");
        }
    }

    private Double calculateFine(Account account) {
        long daysExceeded;
        double fine;
        double totalFine = 0;
        double finePaid = account.getFinePaid() == null ? 0.0 : account.getFinePaid();
        List<BookItem> list = new ArrayList<>(account.getIssuedBooks());
        for (BookItem book : list) {
            /*
             * Here, it is assumed, fine = daysExceeded % of BOOK_PRICE
             * Can be modified as per user requirement by
             * changing the two lines below the commented section
             * as ADMIN sees fit.
             */
            if (book.getDueDate().getTime() < System.currentTimeMillis()) {
                daysExceeded = (System.currentTimeMillis() - book.getDueDate().getTime()) / DAY;
                fine = Math.ceil(daysExceeded) * book.getPrice() / 100;
                if (fine >= book.getPrice()) {
                    fine = book.getPrice();
                }
                totalFine = totalFine + fine - finePaid;
            }
        }
        return totalFine;
    }

    public String payFine(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            if (amount <= account.getFineUnPaid()) {
                account.setFinePaid(account.getFinePaid() + amount);
                return "Rs. " + amount + " paid by " + account.getUsername();
            } else {
                return "Amount greater than due fines!";
            }
        } else {
            return "Account not found!";
        }
    }

    private Integer calculateTotalBooksCheckedOut(Account account) {
        return account.getIssuedBooks().size();
    }

    public void onLoadDb() {
        List<Account> accountList = accountRepository.findAll().stream()
                .filter(account -> account.getStatus() == AccountStatus.Active &&
                        !account.getIssuedBooks().isEmpty())
                .collect(Collectors.toList());
        for (Account account : accountList) {
            account.setTotalBooksCheckedOut(calculateTotalBooksCheckedOut(account));
            account.setFineUnPaid(calculateFine(account));
            accountRepository.save(account);
        }
        System.out.println("DB updated!");
    }
}
