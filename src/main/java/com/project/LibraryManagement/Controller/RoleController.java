package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.Authorities;
import com.project.LibraryManagement.Entity.Role;
import com.project.LibraryManagement.Service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/addRole")
    public String addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @PutMapping("/editRole/{id}")
    public String editRole(@RequestBody Role role, @PathVariable("id") Integer id) {
        return roleService.editRole(role, id);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") Integer id) throws NotFoundException {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/deleteRole/{id}")
    public String deleteRole(@PathVariable("id") Integer id) throws NotFoundException {
        return roleService.deleteRole(id);
    }

    @GetMapping("/getAuthorities/{id}")
    public Collection<Authorities> getAuthorities(@PathVariable Integer id) throws NotFoundException {
        return roleService.getAuthorities(id);
    }

    @PutMapping("/addAuthorities/{id}")
    public String addAuthorities(@PathVariable Integer id,
                                 @RequestBody Authorities authorities) throws NotFoundException {
        return roleService.addAuthorities(id, authorities);
    }

    @DeleteMapping("/deleteAuthorities/{id}")
    public String deleteAuthorities(@PathVariable("id") Integer roleId,
                                    @RequestBody Authorities authorities) throws NotFoundException {
        return roleService.deleteAuthorities(roleId, authorities);
    }

}
