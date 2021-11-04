package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.Account;
import com.project.LibraryManagement.Entity.BookItem;
import com.project.LibraryManagement.Entity.Role;
import com.project.LibraryManagement.Service.AccountService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/")
    public List<Account> getAllAccounts() {
        accountService.onLoadDb();
        return accountService.getAllAccounts();
    }

    @PostMapping("/addAccount/")
    public String addAccount(@RequestBody Account account) {
        accountService.onLoadDb();
        return accountService.addAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.getAccountById(id);
    }

    @GetMapping("/close/{id}")
    public String closeAccount(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.closeAccount(id);
    }

    @GetMapping("/cancel/{id}")
    public String cancelAccount(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.cancelAccount(id);
    }

    @GetMapping("/blacklist/{id}")
    public String blacklistAccount(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.blacklistAccount(id);
    }

    @GetMapping("/reActivate/{id}")
    public String reActivateAccount(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.reactivateAccount(id);
    }

    @GetMapping("/pendingFines")
    public List<Account> getListOfUnpaidFines() {
        accountService.onLoadDb();
        return accountService.getListOfUnpaidFines();
    }

    @GetMapping("/getIssuedBooks/{id}")
    public List<BookItem> getBooksIssued(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.getBooksIssued(id);
    }

    @PutMapping("/edit/{id}")
    public Account editAccount(@PathVariable(value = "id") Long id,
                               @RequestBody Account account) {
        accountService.onLoadDb();
        return accountService.editAccount(id, account);
    }

    @GetMapping("/{id}/payFine/pay")
    public String payFine(@PathVariable(value = "id") Long accountId,
                          @RequestParam(value = "amount") Double amount) {
        accountService.onLoadDb();
        return accountService.payFine(accountId, amount);
    }

    @GetMapping("/getRoles/{id}")
    public List<Role> getRoles(@PathVariable(value = "id") Long id) {
        accountService.onLoadDb();
        return accountService.getRoles(id);
    }

    @PutMapping("/addRole/{id}")
    public String addRole(@PathVariable(value = "id") Long accountId,
                          @RequestBody Role role) throws NotFoundException {
        accountService.onLoadDb();
        return accountService.addRole(accountId, role);
    }

    @DeleteMapping("/deleteRole/{id}")
    public String deleteRole(@PathVariable(value = "id") Long accountId,
                             @RequestBody Role role) throws NotFoundException {
        accountService.onLoadDb();
        return accountService.deleteRole(accountId, role);
    }

}
