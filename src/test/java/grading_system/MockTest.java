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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student1;

    @Autowired
    StudentGrades studentGrades;

    @Mock
    private ApplicationDao applicationDao;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        student1.setFirstname("Rick");
        student1.setLastname("Averson");
        student1.setEmailAddress("test1@mail.ru");
        student1.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("When and Verify")
    public void mockGradeTest() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(student1.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }
}
