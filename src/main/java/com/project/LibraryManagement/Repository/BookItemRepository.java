package com.project.LibraryManagement.Repository;

import com.project.LibraryManagement.Entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, Long> {
}
