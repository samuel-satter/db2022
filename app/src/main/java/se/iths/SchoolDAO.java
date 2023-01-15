package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static se.iths.JdbcConstants.*;

public class SchoolDAO implements CRUD<School> {

    public List<School> schoolArrayList = new ArrayList<>();
    private Connection connection;

    public SchoolDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to gain connection");
        }

    }

    @Override
    public Collection<School> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM School");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                schoolArrayList.add(new School(resultSet.getInt("SchoolId"), resultSet.getString("School"), resultSet.getString("City")));
            }
        } catch (SQLException e) {
            System.out.println("Sql failed");
        }
        return schoolArrayList;
    }

    @Override
    public School findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM School WHERE SchoolId = ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return new School(resultSet.getInt("SchoolId"), resultSet.getString("School"), resultSet.getString("City"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(School object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO School (School, City) VALUES (?, ?)");
            preparedStatement.setString(1, object.getSchoolName());
            preparedStatement.setString(2, object.getCity());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }

    @Override
    public void update(School object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE School SET School = ?, City = ? WHERE SchoolId = ?");
            preparedStatement.setString(1, object.getSchoolName());
            preparedStatement.setString(2, object.getCity());
            preparedStatement.setInt(3, object.getSchoolId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }

    }

    @Override
    public void delete(School object) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM School WHERE School = ? AND City = ? ");
            preparedStatement.setString(1, object.getSchoolName());
            preparedStatement.setString(2, object.getCity());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Statement Failed");
        }
    }

}
