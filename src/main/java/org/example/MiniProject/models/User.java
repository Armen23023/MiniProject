package org.example.MiniProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.MiniProject.enums.Gender;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private  int ID;
    private  String name;
    private  String surname;
    private  String email;
    private  String password;
    private  int age;
    private  String avatar;
    private Gender gender;
    private String phoneNumber;

}
