package com.project.LibraryManagement.Repository;

import com.project.LibraryManagement.Entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
}
