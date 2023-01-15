package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static se.iths.JdbcConstants.*;

public class HobbyDAO implements CRUD<Hobby> {

    public List<Hobby> hobbyArrayList = new ArrayList<>();
    private Connection connection;

    public HobbyDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to gain connection");
        }

    }

    @Override
    public Collection<Hobby> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Hobby");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hobbyArrayList.add(new Hobby(resultSet.getInt("HobbyId"), resultSet.getString("Hobby")));
            }
        } catch (SQLException e) {
            System.out.println("Sql failed");
        }
        return hobbyArrayList;
    }

    @Override
    public Hobby findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Hobby WHERE HobbyId = ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return new Hobby(resultSet.getInt("HobbyId"), resultSet.getString("Hobby"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void create(Hobby object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Hobby (HobbyId, Hobby) VALUES (?, ?)");
            preparedStatement.setInt(1, object.getHobbyId());
            preparedStatement.setString(2, object.getHobbyName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }

    @Override
    public void update(Hobby object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Hobby SET Hobby = ? WHERE HobbyId = ?");
            preparedStatement.setString(1, object.getHobbyName());
            preparedStatement.setInt(2, object.getHobbyId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }


    @Override
    public void delete(Hobby object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Hobby WHERE Hobby = ?");
            preparedStatement.setString(1, object.getHobbyName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }
    }

}
