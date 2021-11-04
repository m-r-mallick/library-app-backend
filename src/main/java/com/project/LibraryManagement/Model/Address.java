package com.project.LibraryManagement.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;

}
