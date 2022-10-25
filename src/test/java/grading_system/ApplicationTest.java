package grading_system;

import grading_system.models.CollegeStudent;
import grading_system.models.Student;
import grading_system.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationTest {

    private static int counter = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void beforeEach() {
        counter++;
        System.out.println("Testing: " + appInfo + ", " + appDescription + ", version: " + appVersion + ", method № " + counter);
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("test@mail.ru");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Add student grades")
    void addStudentGradesTest() {
        assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Is grade greater")
    void isGradeGreaterTest() {
        assertTrue(studentGrades.isGradeGreater(90, 75));
    }

    @Test
    @DisplayName("Is grade null")
    void isGradeNullTest() {
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Create student without grade")
    void createStudentWithoutGradesTest() {
        CollegeStudent student2 = context.getBean("collegeStudent", CollegeStudent.class);
        student2.setFirstname("Rick");
        student2.setLastname("Averson");
        student2.setEmailAddress("test2@mail.ru");
        assertNotNull(student2.getFirstname());
        assertNull(studentGrades.checkNull(student2.getStudentGrades()));
    }

    @Test
    @DisplayName("Verify student prototypes")
    void verifyStudentPrototypesTest() {
        CollegeStudent student3 = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student, student3);
    }

    @Test
    @DisplayName("Find average grade")
    void findAverageGradeTest() {
        assertAll(
                () -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(88.31, studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults())));
    }
}
