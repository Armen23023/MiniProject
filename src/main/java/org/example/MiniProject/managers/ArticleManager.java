package org.example.MiniProject.managers;

import lombok.SneakyThrows;
import org.example.MiniProject.models.Article;
import org.example.MiniProject.models.User;
import org.example.MiniProject.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArticleManager {
    Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @SneakyThrows
    public Article save(Article article) {
        String sql = "insert into  articles " +
                "(title, content, user_ID)" +
                " values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, article.getTitle());
        preparedStatement.setString(2, article.getContent());
        preparedStatement.setInt(3, article.getUserID());


        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int userId = generatedKeys.getInt(1);
        logger.info("New user created: " + article);
        article.setID(userId);
        return article;
    }

    @SneakyThrows
    public List<Article> articlesByAuthor(User author) {
        List<Article> articles = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from articles where user_ID = ?");
        statement.setInt(1,author.getID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            int ID = resultSet.getInt("ID");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");


            articles.add(Article.builder()
                    .ID(ID)
                    .title(title)
                    .content(content)
                    .author(author)
                    .userID(author.getID())
                    .build());
        }
        return articles;
    }
}
