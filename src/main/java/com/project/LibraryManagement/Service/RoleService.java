package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Authorities;
import com.project.LibraryManagement.Entity.Role;
import com.project.LibraryManagement.Repository.AuthoritiesRepository;
import com.project.LibraryManagement.Repository.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public String addRole(Role role) {
        roleRepository.save(role);
        return role.toString();
    }

    public String editRole(Role role, Integer id) {
        Role ogRole = roleRepository.findById(id).orElse(null);
        if (ogRole != null) {
            ogRole.setRole(role.getRole());
            ogRole.setAuthorities(role.getAuthorities());
            roleRepository.save(ogRole);
            return "Role updated!";
        } else {
            return "Role by id " + id + " not found";
        }
    }

    public Role getRoleById(Integer id) throws NotFoundException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return role;
        } else {
            throw new NotFoundException("Role by id " + id + " not found in repository!");
        }
    }

    public String deleteRole(Integer id) throws NotFoundException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
            return "Role deleted!";
        } else {
            throw new NotFoundException("Role by id " + id + " not found in repository!");
        }
    }

    public Collection<Authorities> getAuthorities(Integer id) throws NotFoundException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return role.getAuthorities();
        } else {
            throw new NotFoundException("Role by id " + id + " not found in repository!");
        }
    }

    public String addAuthorities(Integer id, Authorities authorities) throws NotFoundException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            Collection<Authorities> permissions = role.getAuthorities();
            Authorities auth = authoritiesRepository.findAll().stream()
                    .filter(a -> a.getPermission().equals(authorities.getPermission()))
                    .findFirst().orElse(null);
            if (auth != null) {
                permissions.add(auth);
                role.setAuthorities(permissions);
                roleRepository.save(role);
                return "Permissions added!";
            } else {
                throw new NotFoundException(authorities.toString() + " not found in repository!");
            }
        } else {
            throw new NotFoundException("Role by id " + id + " not found in repository!");
        }
    }

    public String deleteAuthorities(Integer roleId, Authorities authorities) throws NotFoundException{
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            Collection<Authorities> permissions = role.getAuthorities();
            if (authoritiesRepository.findAll()
            .stream().anyMatch(auth -> auth == authorities)) {
                if (permissions.contains(authorities)) {
                    permissions.remove(authorities);
                    role.setAuthorities(permissions);
                    roleRepository.save(role);
                    return "Permissions deleted!";
                } else {
                    throw new NotFoundException("Role doesn't have given authorities!");
                }
            } else {
                throw new NotFoundException(authorities.toString() + " not found in repository!");
            }
        } else {
            throw new NotFoundException("Role by id " + roleId + " not found in repository!");
        }
    }
}
