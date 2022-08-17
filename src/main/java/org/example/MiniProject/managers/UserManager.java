package org.example.MiniProject.managers;

import lombok.SneakyThrows;
import org.example.MiniProject.enums.Gender;
import org.example.MiniProject.models.User;
import org.example.MiniProject.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserManager {

    Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @SneakyThrows
    public User save(User user) {
        String sql = "insert into users " +
                "(name, surname, email, password, age, avatar, gender, phone_number)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setString(6, user.getAvatar());
        preparedStatement.setString(7, user.getGender().name());
        preparedStatement.setString(8, user.getPhoneNumber());

        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int userId = generatedKeys.getInt(1);
        logger.info("New user created: " + user);
        user.setID(userId);
        return user;
    }

    @SneakyThrows
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String avatar = resultSet.getString("avatar");
            String phoneNumber = resultSet.getString("phone_number");
            Gender gender = Gender.valueOf(resultSet.getString("gender"));
            int age = resultSet.getInt("age");

            users.add(User.builder()
                    .ID(id)
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .avatar(avatar)
                    .phoneNumber(phoneNumber)
                    .gender(gender)
                    .age(age)
                    .build());
        }
        return users;
    }


    @SneakyThrows
    public boolean deleteById(int id) {
        String query = "delete from users where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int updatedRowCount = preparedStatement.executeUpdate();
        return updatedRowCount > 0;
    }

    @SneakyThrows
    public User getByEmailAndPassword(String email, String password) {
        User user = null;
        String query = "select * from users where email = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = User.builder()
                    .ID(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .email(email)
                    .password(password)
                    .avatar(resultSet.getString("avatar"))
                    .phoneNumber(resultSet.getString("phone_number"))
                    .gender(Gender.valueOf(resultSet.getString("gender")))
                    .age(resultSet.getInt("age"))
                    .build();

        }
        return user;
    }

    @SneakyThrows
    public boolean update(int id, User user) {
        String query = "update users u set u.name = ?, u.surname = ?, u.email =?, u.password = ?, " +
                "u.age = ?, u.gender = ?, u.phone_number = ?, u.avatar = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setString(6, user.getGender().name());
        preparedStatement.setString(7, user.getPhoneNumber());
        preparedStatement.setString(8, user.getAvatar());
        preparedStatement.setInt(9, id);
        int i = preparedStatement.executeUpdate();
        return i > 0;
    }
}