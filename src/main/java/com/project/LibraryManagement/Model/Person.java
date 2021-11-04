package com.project.LibraryManagement.Model;

import com.project.LibraryManagement.Model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    private String name;
    private Address address;
    private String email;
    private String phone;

}
