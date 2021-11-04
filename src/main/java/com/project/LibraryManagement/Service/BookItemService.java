package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Account;
import com.project.LibraryManagement.Entity.BookItem;
import com.project.LibraryManagement.Model.BookStatus;
import com.project.LibraryManagement.Repository.AccountRepository;
import com.project.LibraryManagement.Repository.BookItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.LibraryManagement.Model.Constants.*;

@Service
public class BookItemService {

    @Autowired
    BookItemRepository bookItemRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<BookItem> getAllCopies() {
        return new ArrayList<>(bookItemRepository.findAll());
    }

    public String addCopy(BookItem bookItem) {
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // utilDate.getTime() can be replaced with
        bookItem.setDateOfPurchase(sqlDate);                           // java.sql.Date(System.currentTimeMillis);
        bookItemRepository.save(bookItem);
        return bookItem.toString() + " added!\n";
    }

    public List<BookItem> getCopiesOfBook(Long isbn) {
        return bookItemRepository.findAll().stream()
                .filter(bookItem -> bookItem.getOriginalBook().getISBN() == isbn)
                .collect(Collectors.toList());
    }

    public BookItem getBookByBarcode(Long barcode) {
        return bookItemRepository.findById(barcode).orElse(null);
    }

    public BookItem issueBook(Long barcode, Long accountId) {
        BookItem copy = bookItemRepository.findById(barcode).orElse(null);
        if (copy != null) {
            if (copy.getStatus() == BookStatus.Reserved) {
                if (System.currentTimeMillis() - copy.getBorrowed().getTime() >= MAX_RESERVATION_PERIOD) {   // Checking if book has been reserved for
                    copy.setStatus(BookStatus.Available);                                                    // more than a week. If yes, reservation is
                    copy.setCustomer(null);                                                                  // cancelled and BookStatus is set to available
                    bookItemRepository.save(copy);                                                           // and proceed to checkout
                    issueBook(barcode, accountId);
                } else if (copy.getCustomer().getId().equals(accountId)) {
                    copy.setStatus(BookStatus.Available);
                    bookItemRepository.save(copy);
                    issueBook(barcode, accountId);
                } else {
                    return null;
                }
            }
            if (copy.getStatus() == BookStatus.Available &&
                    !copy.getIsReferenceOnly()) {
                Account account = accountRepository.findById(accountId).orElse(null);
                copy.setCustomer(account);
                if (account != null) {
                    List<BookItem> issuedBooks = account.getIssuedBooks();
                    if (issuedBooks.size() + account.getReservedBooks().size() >= ISSUE_OR_RESERVATION_LIMIT) {
                        return null;
                    }
                    copy.setBorrowed(new java.sql.Date(System.currentTimeMillis()));
                    copy.setDueDate(new java.sql.Date(System.currentTimeMillis() + MAX_ISSUE_PERIOD));
                    copy.setStatus(BookStatus.Loaned);
                    issuedBooks.add(copy);
                    account.setIssuedBooks(issuedBooks);

                    accountRepository.save(account);
                    bookItemRepository.save(copy);
                    return copy;
                }
            }
        }
        return null;
    }

    public String deleteCopy(Long barcode) {
        BookItem copy = bookItemRepository.findById(barcode).orElse(null);
        if (copy != null) {
            bookItemRepository.delete(copy);
            return "Item deleted!";
        } else {
            return "Item not found!";
        }
    }

    public String returnBook(BookItem bookItem) {
        BookItem copy = bookItemRepository.findById(bookItem.getBarcode()).orElse(null);
        if (copy != null &&
                copy.getStatus() == BookStatus.Loaned &&
                    !copy.getIsReferenceOnly()) {
            copy.setBorrowed(null);
            copy.setDueDate(null);
            copy.setStatus(BookStatus.Available);
            copy.setCustomer(null);
            bookItemRepository.save(copy);
            return "Book item returned!";
        }
        return "No record of borrowed book found!";
    }

    public BookItem renewBook(Long barcode) {
        BookItem bookItem = bookItemRepository.findById(barcode).orElse(null);
        if (bookItem != null &&
                bookItem.getStatus() == BookStatus.Reserved &&
                    !bookItem.getIsReferenceOnly()) {
            bookItem.setDueDate(new java.sql.Date(bookItem.getDueDate().getTime() + 259200000));
            bookItemRepository.save(bookItem);
            return bookItem;
        } else {
            return null;
        }
    }

    public String editBook(Long barcode, BookItem bookItem) {
        BookItem ogCopy = bookItemRepository.findById(barcode).orElse(null);
        if (ogCopy != null) {
            ogCopy.setIsReferenceOnly(bookItem.getIsReferenceOnly());
            ogCopy.setStatus(bookItem.getStatus());
            ogCopy.setBorrowed(bookItem.getBorrowed());
            ogCopy.setDueDate(bookItem.getDueDate());
            ogCopy.setPrice(bookItem.getPrice());
            ogCopy.setFormat(bookItem.getFormat());
            ogCopy.setOriginalBook(bookItem.getOriginalBook());
            ogCopy.setCustomer(bookItem.getCustomer());
            bookItemRepository.save(ogCopy);
            return "Book item updated successfully!";
        } else {
            return "Book item not found!";
        }
    }

//    Reservation will be automatically cancelled after ISSUE_OR_RESERVATION_LIMIT days have expired
    public String reserveBook(Long barcode, Long accountId) {
        BookItem bookItem = bookItemRepository.findById(barcode).orElse(null);
        if (bookItem != null) {
            List<Account> reservees = bookItem.getReservees();
            Account account = accountRepository.findById(accountId).orElse(null);
            if (account != null) {
                if (account.getIssuedBooks().contains(bookItem) ||
                        account.getReservedBooks().contains(bookItem)) {
                    return "Error : Book already issued to account!";
                } else if (account.getReservedBooks().size() + account.getIssuedBooks().size() >= ISSUE_OR_RESERVATION_LIMIT) {
                    return "Error : Can't reserve/issue more than 5 books at a time";
                } else {
                    reservees.add(account);
                    List<BookItem> reservedBooks = account.getReservedBooks();
                    reservedBooks.add(bookItem);
                    if (bookItem.getStatus() == BookStatus.Available) {
                        bookItem.setCustomer(reservees.get(0));
                        bookItem.setStatus(BookStatus.Reserved);
                        bookItem.setBorrowed(new java.sql.Date(System.currentTimeMillis())); // storing date of reservation
                        bookItem.setDueDate(new java.sql.Date(bookItem.getBorrowed().getTime() + MAX_RESERVATION_PERIOD));
                    }
                    account.setReservedBooks(reservedBooks);
                    accountRepository.save(account);
                    bookItem.setReservees(reservees);
                    bookItemRepository.save(bookItem);
                    return "Book reserved!";
                }
            } else {
                return "Account not found!";
            }
        } else {
            return "Book not found!";
        }
    }

    public String cancelReservation(Long barcode, Long accountId) {
        BookItem bookItem = bookItemRepository.findById(barcode).orElse(null);
        if (bookItem != null) {
            List<Account> reservees = bookItem.getReservees();
            Account account = accountRepository.findById(accountId).orElse(null);
            if (account != null) {
                List<BookItem> reservedBooks = account.getReservedBooks();
                reservedBooks.remove(bookItem);
                List<Account> tempList = bookItem.getReservees();
                if (bookItem.getStatus() == BookStatus.Reserved) {
                    if (poll(tempList).size() == 0) {
                        bookItem.setCustomer(null);
                        bookItem.setStatus(BookStatus.Available);
                        bookItem.setBorrowed(null);
                        bookItem.setDueDate(null);
                    } else {
                        bookItem.setCustomer(reservees.get(reservees.indexOf(account) + 1));
                        bookItem.setBorrowed(new java.sql.Date(System.currentTimeMillis()));
                        bookItem.setDueDate(new java.sql.Date(System.currentTimeMillis() + MAX_RESERVATION_PERIOD));
                    }
                }
                account.setReservedBooks(reservedBooks);
                accountRepository.save(account);
                bookItem.setReservees(poll(reservees));
                bookItemRepository.save(bookItem);
                return "Reservation cancelled successfully!";
            } else {
                return "Account not found!";
            }
        } else {
            return "Book item not found!";
        }
    }

    /*
     User-Defined method for implementation of
     Queue (only poll function) using ArrayList
     since Spring Data JPA does not support storing
     Queue or LinkedList directly in the database
    */
    private List<Account> poll(List<Account> originalList) {
        originalList.set(0, null);
        List<Account> newList = new ArrayList<>();
        for (Account acc : originalList) {
            if (acc != null) {
                newList.add(acc);
            }
        }
        return newList;
    }
}

