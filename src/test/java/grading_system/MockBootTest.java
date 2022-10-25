package grading_system;

import grading_system.dao.ApplicationDao;
import grading_system.models.CollegeStudent;
import grading_system.models.StudentGrades;
import grading_system.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockBootTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student1;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        student1.setFirstname("Rick");
        student1.setLastname("Averson");
        student1.setEmailAddress("test1@mail.ru");
        student1.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Mock When and Verify")
    public void mockWhenAndVerifyTest() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(student1.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());

        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31, applicationService.findGradePointAverage(student1.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1)).findGradePointAverage(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("Mock Not Null")
    public void mockNotNullTest() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

        assertNotNull(applicationService.checkNull(student1.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Mock Throws an Exception")
    public void mockThrowExceptionTest() {
        CollegeStudent nullStudent = null;
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenThrow(new RuntimeException())
                .thenReturn("OK");

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
        assertEquals("OK", applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }
}
