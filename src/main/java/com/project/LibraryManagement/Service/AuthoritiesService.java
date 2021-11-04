package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Authorities;
import com.project.LibraryManagement.Repository.AuthoritiesRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthoritiesService {

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    public List<Authorities> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }

    public String addAuthorities(Authorities authorities) {
        authoritiesRepository.save(authorities);
        return authorities.toString() + " saved!";
    }

    public String editAuthorities(Authorities authorities, Integer id) throws NotFoundException {
        Authorities ogAuth = authoritiesRepository.findById(id).orElse(null);
        if (ogAuth != null) {
            ogAuth.setPermission(authorities.getPermission());
            authoritiesRepository.save(ogAuth);
            return "Authorities updated!";
        } else {
            throw new NotFoundException("Authorities by id " + id + " not found in repository!");
        }
    }

    public Authorities getAuthoritiesById(Integer id) {
        return authoritiesRepository.findById(id).orElseThrow();
    }

    public String deleteAuthorities(Integer id) throws NotFoundException {
        Authorities auth = authoritiesRepository.findById(id).orElse(null);
        if (auth != null) {
            authoritiesRepository.delete(auth);
            return "Authorities deleted!";
        } else {
            throw new NotFoundException("Authorities by id " + id + " not found in repository!");
        }
    }
}
