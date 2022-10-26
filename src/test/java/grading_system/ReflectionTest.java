package grading_system;

import grading_system.dao.ApplicationDao;
import grading_system.models.CollegeStudent;
import grading_system.models.StudentGrades;
import grading_system.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReflectionTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student1;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        student1.setFirstname("Rick");
        student1.setLastname("Averson");
        student1.setEmailAddress("test1@mail.ru");
        student1.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student1, "id", 1);
        ReflectionTestUtils.setField(student1, "studentGrades", new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75))));
    }

    @Test
    @DisplayName("Get Private Data")
    public void getPrivateFieldTest() {
        assertEquals(1, ReflectionTestUtils.getField(student1, "id"));
    }

    @Test
    @DisplayName("Call Private Method")
    public void callPrivateMethodTest() {
        assertEquals("Rick 1", ReflectionTestUtils.invokeMethod(student1, "getFirstNameAndId"));
    }
}
