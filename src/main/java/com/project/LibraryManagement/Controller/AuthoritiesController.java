package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.Authorities;
import com.project.LibraryManagement.Service.AuthoritiesService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/authorities")
public class AuthoritiesController {

    @Autowired
    AuthoritiesService authoritiesService;

    @GetMapping("/")
    public List<Authorities> getAllAuthorities() {
        return authoritiesService.getAllAuthorities();
    }

    @PostMapping("/addAuthorities")
    public String addAuthorities(@RequestBody Authorities authorities) {
        return authoritiesService.addAuthorities(authorities);
    }

    @PutMapping("/editAuthorities/{id}")
    public String editAuthorities(@RequestBody Authorities authorities, @PathVariable("id") Integer id) throws NotFoundException {
        return authoritiesService.editAuthorities(authorities, id);
    }

    @GetMapping("/{id}")
    public Authorities getAuthoritiesById(@PathVariable("id") Integer id) throws NotFoundException {
        return authoritiesService.getAuthoritiesById(id);
    }

    @DeleteMapping("/deleteAuthorities/{id}")
    public String deleteAuthorities(@PathVariable("id") Integer id) throws NotFoundException {
        return authoritiesService.deleteAuthorities(id);
    }

}
