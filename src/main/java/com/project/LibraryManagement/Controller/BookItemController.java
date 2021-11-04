package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.BookItem;
import com.project.LibraryManagement.Service.BookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/copies")
public class BookItemController {

    @Autowired
    BookItemService bookItemService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public List<BookItem> getAllCopies() {
        return bookItemService.getAllCopies();
    }

    @PostMapping("/addCopy")
    public String addCopy(@RequestBody BookItem bookItem) {
        return bookItemService.addCopy(bookItem);
    }

    @GetMapping("/book/{isbn}")
    public List<BookItem> getCopiesOfBook(@PathVariable(value = "isbn") Long isbn) {
        return bookItemService.getCopiesOfBook(isbn);
    }
    @GetMapping("/{barcode}")
    public BookItem getBookByBarcode(@PathVariable(value = "barcode") Long barcode) {
        return bookItemService.getBookByBarcode(barcode);
    }

    @GetMapping("/issue/{barcode}/customer")
    public BookItem issueBook(@PathVariable(value = "barcode") Long barcode,
                              @RequestParam(value = "id") Long id) {
        return bookItemService.issueBook(barcode, id);
    }

    @DeleteMapping("/delete/{barcode}")
    public String deleteCopy(@PathVariable(value = "barcode") Long barcode) {
        return bookItemService.deleteCopy(barcode);
    }

    @PostMapping("/return")
    public String returnBook(@RequestBody BookItem bookItem) {
        return bookItemService.returnBook(bookItem);
    }

    @GetMapping("/renew/{barcode}")
    public BookItem renewBook(@PathVariable(value = "barcode") Long barcode) {
        return bookItemService.renewBook(barcode);
    }

    @GetMapping("/reserve/{barcode}/customer")
    public String reserveBook(@PathVariable(value = "barcode") Long barcode,
                              @RequestParam(value = "id") Long id) {
        return bookItemService.reserveBook(barcode, id);
    }

    @GetMapping("/cancelReservation/{barcode}/customer")
    public String cancelReservation(@PathVariable(value = "barcode") Long barcode,
                                    @RequestParam(value = "id") Long accountId) {
        return bookItemService.cancelReservation(barcode, accountId);
    }

    @PutMapping("/edit/{barcode}")
    public String editBook(@PathVariable(value = "barcode") Long barcode,
                           @RequestBody BookItem bookItem) {
        return bookItemService.editBook(barcode, bookItem);
    }

}
