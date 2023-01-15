
package se.iths;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest {
    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/iths";
    private static final String JDBC_USER = "iths";
    private static final String JDBC_PASSWORD = "iths";

    public static Connection con = null;

    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection(JDBC_CONNECTION, JDBC_USER, JDBC_PASSWORD);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        con.close();
    }

    @Order(1)
    @Test
    void shouldFindAllStudents() {
        StudentDAO studentDAO = new StudentDAO();
        assertTrue(studentDAO.findAll().size() > 0);
    }

    @Order(2)
    @Test
    void crudOperationOnStudents() {
        //Deleting potential rest Students
        Student deleteStudent = new Student(-1, "Samuel", "Satter");
        Student deleteStudent2 = new Student(-2, "Lars", "Erikson");
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.delete(deleteStudent);
        studentDAO.delete(deleteStudent2);
        //Test starting
        Student student = new Student(46, "Samuel", "Satter");
        studentDAO.create(student);
        Optional<Student> optionalStudent = studentDAO.findAll()
                .stream()
                .filter(student1 -> student1.getFirstName()
                        .equalsIgnoreCase("Samuel") && student1
                        .getLastName()
                        .equalsIgnoreCase("Satter"))
                .findAny();
        if (optionalStudent.isPresent()) {
            assertNotNull(studentDAO.findById(optionalStudent.get().getId()));
            Student student1 = new Student(optionalStudent.get().getId(), "Lars", "Erikson");
            studentDAO.update(student1);
            Student updatedStudent = studentDAO.findById(optionalStudent.get().getId());
            assertEquals(updatedStudent.getLastName(), "Erikson");
            studentDAO.delete(student1);
            assertNull(studentDAO.findById(optionalStudent.get().getId()));
        }


    }

    @Order(3)
    @Test
    void shouldFindAllSchools() {
        SchoolDAO schoolDAO = new SchoolDAO();
        schoolDAO.findAll().forEach(school -> System.out.println(school.toString()));
        //assertEquals(schoolDAO.findAll().size(), 4);
    }


    @Order(4)
    @Test
    void shouldAddNewSchool() {
        School school = new School(5, "Aspudden", "Stockholm");
        SchoolDAO schoolDAO = new SchoolDAO();
        schoolDAO.create(school);
        assertTrue(schoolDAO.findAll()
                .stream()
                .anyMatch(school1 -> school.getSchoolName().equalsIgnoreCase("Aspudden")));
    }

    @Order(5)
    @Test
    void shouldFindById() {
        SchoolDAO schoolDAO = new SchoolDAO();
        System.out.println(schoolDAO.findById(1).getSchoolName());
    }

    @Order(6)
    @Test
    void shouldUpdateSchool() {
        SchoolDAO schoolDAO = new SchoolDAO();
        Optional<School> school = schoolDAO.findAll()
                .stream()
                .filter(school1 -> school1.getSchoolName()
                        .equalsIgnoreCase("Aspudden") && school1
                        .getCity().equalsIgnoreCase("Stockholm"))
                .findAny();
        if (school.isPresent()) {
            School school1 = new School(school.get().schoolId, "newAspudden", "Kalmar");
            schoolDAO.update(school1);
        }
    }


    @Order(7)
    @Test
    void shouldDeleteSchool() {
        School school = new School(5, "newAspudden", "Kalmar");
        SchoolDAO schoolDAO = new SchoolDAO();
        schoolDAO.delete(school);

    }

    @Order(8)
    @Test
    void shouldFindAllHobbies() {
        HobbyDAO hobbyDAO = new HobbyDAO();
        hobbyDAO.findAll().forEach(hobby -> System.out.println(hobby.toString()));
    }

    @Order(9)
    @Test
    void crudOperationOnHobby() {
        //Deleting potential rest Hobbies
        Hobby deleteHobby = new Hobby(-1, "Programming");
        HobbyDAO hobbyDAO = new HobbyDAO();
        hobbyDAO.delete(deleteHobby);
        //Test starting
        Hobby hobby = new Hobby(10, "Programming");
        hobbyDAO.create(hobby);
        Optional<Hobby> optionalHobby = hobbyDAO.findAll()
                .stream()
                .filter(hobby1 -> hobby1.getHobbyName()
                        .equalsIgnoreCase("Programming"))
                .findAny();
        if (optionalHobby.isPresent()) {
            assertNotNull(hobbyDAO.findById(optionalHobby.get().getHobbyId()));
            Hobby hobby1 = new Hobby(optionalHobby.get().getHobbyId(), "Coding");
            hobbyDAO.update(hobby1);
            Hobby updatedHobby = hobbyDAO.findById(optionalHobby.get().getHobbyId());
            assertEquals(updatedHobby.getHobbyName(), "Coding");
            hobbyDAO.delete(hobby1);
            assertNull(hobbyDAO.findById(optionalHobby.get().getHobbyId()));
        }
    }
}