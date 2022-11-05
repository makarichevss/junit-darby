package spring_mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import spring_mvc.models.*;
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

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;
    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;
    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;
    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;
    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;
    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;
    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeEach
    public void prepareDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    public void cleanDatabase() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }

    @Test
    public void createStdeletService() {
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
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradesDao.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradesDao.findById(1);
        assertFalse(student.isPresent());
        assertFalse(mathGrade.isPresent());
        assertFalse(historyGrade.isPresent());
        assertFalse(scienceGrade.isPresent());
    }

    @Test
    @Sql("/insertTestData.sql")
    public void getGradebookServiceTest() {
        Iterable<CollegeStudent> students = studentService.getGradebook();
        List<Object> collegeStudents = new ArrayList<>();
        for (CollegeStudent student : students) {
            collegeStudents.add(student);
        }
        assertEquals(5, collegeStudents.size());
    }

    @Test
    public void createGradeServiceTest() {
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size());
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size());
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size());
    }

    @Test
    public void insertInvalidDataTest() {
        assertFalse(studentService.createGrade(105, 1, "math"));
        assertFalse(studentService.createGrade(-5, 1, "math"));
        assertFalse(studentService.createGrade(80.50, 2, "math"));
        assertFalse(studentService.createGrade(80.50, 1, "english"));
    }

    @Test
    public void deleteGradeServiceTest(){
        assertEquals(1, studentService.deleteGrade(1, "math"));
        assertEquals(1, studentService.deleteGrade(1, "science"));
        assertEquals(1, studentService.deleteGrade(1, "history"));
    }

    @Test
    public void deleteGradeService0Id() {
        assertEquals(0, studentService.deleteGrade(0, "science"));
        assertEquals(0, studentService.deleteGrade(1, "literature"));
    }

    @Test
    public void getStudentInfo() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.getStudentInfo(1);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("Sergey", gradebookCollegeStudent.getFirstname());
        assertEquals(1, gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void getNoStudentInfo() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.getStudentInfo(0);
        assertNull(gradebookCollegeStudent);
    }
}
