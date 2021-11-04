package com.project.LibraryManagement.Security.Services;

import com.project.LibraryManagement.Entity.Account;
import com.project.LibraryManagement.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JPAUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account user = accountRepository.findAll().stream()
                .filter(account -> account.getUsername().equals(username))
                .findAny().orElseThrow(() -> new UsernameNotFoundException("Error!"));

        return new User(user);
    }

}
