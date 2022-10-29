package spring_mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import spring_mvc.models.CollegeStudent;
import spring_mvc.models.HistoryGrade;
import spring_mvc.models.MathGrade;
import spring_mvc.models.ScienceGrade;
import spring_mvc.repository.HistoryGradesDao;
import spring_mvc.repository.MathGradesDao;
import spring_mvc.repository.ScienceGradesDao;
import spring_mvc.repository.StudentDao;
import spring_mvc.service.StudentAndGradeService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @BeforeEach
    public void prepareDatabase() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address)" +
                "values (1, 'Sergey', 'Makarichev', '1@mail.ru')");
    }

    @AfterEach
    public void cleanDatabase() {
        jdbc.execute("delete from student");
    }

    @Test
    public void createStudentService() {
        studentService.createStudent("Sergey", "Makarichev", "6@mail.ru");
        CollegeStudent student = studentDao.findByEmailAddress("6@mail.ru");
        assertEquals("6@mail.ru", student.getEmailAddress());
    }

    @Test
    public void isStudentNullTest() {
        assertTrue(studentService.checkIfStudentIsNull(1));
    }

    @Test
    public void deleteStudentTest() {
        studentService.deleteStudent(1);
        Optional<CollegeStudent> student = studentDao.findById(1);
        assertFalse(student.isPresent());
    }

    @Test
    @Sql("/insertTestData.sql")
    public void getGradebookService() {
        Iterable<CollegeStudent> students = studentService.getGradebook();
        List<Object> collegeStudents = new ArrayList<>();
        for (CollegeStudent student : students) {
            collegeStudents.add(student);
        }
        assertEquals(5, collegeStudents.size());
    }

    @Test
    public void createGradeService() {
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);
        assertTrue(mathGrades.iterator().hasNext());
        assertTrue(scienceGrades.iterator().hasNext());
        assertTrue(historyGrades.iterator().hasNext());
    }

}
