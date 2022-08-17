package org.example.MiniProject.managers;

import lombok.SneakyThrows;
import org.example.MiniProject.models.Article;
import org.example.MiniProject.models.Comment;
import org.example.MiniProject.models.User;
import org.example.MiniProject.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CommentManager {
    Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @SneakyThrows
    public Comment save(Comment comment, Article article) {
        String sql = "insert into  comments " +
                "(content, user_ID,article_ID)" +
                " values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, comment.getContent());
        preparedStatement.setInt(2, comment.getUserID());
        preparedStatement.setInt(3, article.getID());


        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int userId = generatedKeys.getInt(1);
        logger.info("New user created: " + comment);
        comment.setID(userId);
        return comment;
    }

    @SneakyThrows
    public List<Comment> articlesByArticle(Article article) {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT c.*,u.`Name`AS author_name," +
                "u.`Surname`AS author_surname" +
                " FROM comments c INNER JOIN users u ON c.`author_ID`=u.`ID` where c.article_ID = ?");
        statement.setInt(1, article.getID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            int ID = resultSet.getInt("ID");
            String content = resultSet.getString("content");
            int userID = resultSet.getInt("author_ID");
            comments.add(Comment.builder()
                    .ID(ID)
                    .content(content)
                    .author(User.builder()
                            .ID(userID)
                            .name(resultSet.getString("author_name"))
                            .surname(resultSet.getString("author_surname"))
                            .build())
                    .build());
        }
        return comments;
    }
}
