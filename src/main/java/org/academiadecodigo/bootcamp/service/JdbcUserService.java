package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codecadet on 15/07/2019.
 */
public class JdbcUserService implements UserService{


    private Connection connection;
    private ConnectionManager connectionManager;

    public JdbcUserService() {
        connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();

    }

    @Override
    public boolean authenticate(String username, String password) {

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT username, password from user WHERE username = '" + username + "' AND password = '" + Security.getHash(password) + "'";
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            if (resultSet.getString("username").equals(username) && resultSet.getString("password").equals(Security.getHash(password))) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void add(User user) {

        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();

        try{
            // create a new statement
            Statement statement = connection.createStatement();

            // create a query
            String query = "INSERT INTO user (username, password, email, firstName, lastName, phone)" +
                    "VALUES(" + "'"+ username + "'," + "'" +password + "'," + "'" + email + "'," + "'" + firstName + "'," + "'" + lastName + "'," + "'" + phone + "'" +");";

            // execute the query
            statement.execute(query);


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User findByName(String username) {

        User user = null;

        try{
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM user WHERE username = '" + username + "';";
            ResultSet resultSet = statement.executeQuery(query);

            // user exists
            if(resultSet.next()) {

                String usernameValue = resultSet.getString("username");
                String passwordValue = resultSet.getString("password");
                String emailValue = resultSet.getString("email");
                String firstNameValue = resultSet.getString("firstname");
                String lastNameValue = resultSet.getString("lastname");
                String phoneValue = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passwordValue, firstNameValue, lastNameValue, phoneValue);

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public List<User> findAll() {

        List<User> myList = new ArrayList<User>();
        User user = null;

        try{
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM user;";
            ResultSet resultSet = statement.executeQuery(query);

            // user exists
            while(resultSet.next()) {

                String usernameValue = resultSet.getString("username");
                String passwordValue = resultSet.getString("password");
                String emailValue = resultSet.getString("email");
                String firstNameValue = resultSet.getString("firstname");
                String lastNameValue = resultSet.getString("lastname");
                String phoneValue = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passwordValue, firstNameValue, lastNameValue, phoneValue);
                myList.add(user);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return myList;
    }

    @Override
    public int count() {

        int result = 0;

        try {
            // create a new statement
            Statement statement = connection.createStatement();

            // create a query
            String query = "SELECT COUNT(*) FROM user";

            // execute the query
            ResultSet resultSet = statement.executeQuery(query);

            // get the results
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setDbConnection(Connection dbConnection) {
        this.connection = dbConnection;
    }
}
