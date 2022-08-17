package org.example.MiniProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.MiniProject.enums.Gender;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Article {
    private  int ID;
    private  String title;
    private  String content;
    private  int userID;
    private  User author;
    private List<Comment> comments = new ArrayList<>();

}
