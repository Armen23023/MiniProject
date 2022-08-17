package org.example.MiniProject;


import org.example.MiniProject.enums.Gender;
import org.example.MiniProject.managers.ArticleManager;
import org.example.MiniProject.managers.UserManager;
import org.example.MiniProject.models.Article;
import org.example.MiniProject.models.User;

public class Main {

    public static void main(String[] args) {
 //               UserManager userManager = new UserManager();
//        User poxos = userManager.save(User.builder()
//                .name("Poxos")
//                .surname("Poxosyan")
//                .email("poxos@gmail.com")
//                .password("123456")
//                .age(18)
//                .avatar("avatar.png")
//                .gender(Gender.FEMALE)
//                .phoneNumber("+37494859674")
//                .build());
//
//        User petros = userManager.save(User.builder()
//                .name("Petros1")
//                .surname("Petrosyan")
//                .email("petros@gmail.com")
//                .password("147852")
//                .age(50)
//                .avatar("avatar.png")
//                .gender(Gender.FEMALE)
//                .phoneNumber("+37496857414")
//                .build());
//
//        boolean isDelete = userManager.deleteById(7);
//        System.out.println(isDelete);
//                User poxos = userManager.getByEmailAndPassword("poxos@gmail.com", "123456");
//                poxos.setAge(81);
//
//                System.out.println(userManager.update(4, poxos));

        ArticleManager articleManager = new ArticleManager();
        articleManager.save(Article.builder()
                        .content("This is a our second created article by Petros")
                        .title("Article 2")
                        .userID(5)
                        .build());
        }
    }

