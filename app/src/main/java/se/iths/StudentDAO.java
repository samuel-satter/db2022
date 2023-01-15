package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static se.iths.JdbcConstants.*;

public class StudentDAO implements CRUD<Student> {

    public List<Student> studentArrayList = new ArrayList<>();
    Connection connection;

    public StudentDAO() {

        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to gain connection");
        }

    }


    @Override
    public Collection<Student> findAll() {
        try {
            Connection con = DriverManager.getConnection(JDBC_CONNECTION, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Student");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentArrayList.add(new Student(resultSet.getInt("StudentId"), resultSet.getString("FirstName"), resultSet.getString("LastName")));
            }
        } catch (SQLException e) {
            System.out.println("Sql failed");
        }
        return studentArrayList;
    }

    @Override
    public Student findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Student WHERE StudentId = ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return new Student(resultSet.getInt("StudentId"), resultSet.getString("FirstName"), resultSet.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Student object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student (FirstName, LastName) VALUES (?, ?)");
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }

    @Override
    public void update(Student object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Student SET FirstName = ?, LastName = ? WHERE StudentId = ?");
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setInt(3, object.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }

    @Override
    public void delete(Student object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Student WHERE FirstName = ? AND LastName = ? ");
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }
    }
}
