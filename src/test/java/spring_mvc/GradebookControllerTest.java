package spring_mvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;
import spring_mvc.models.CollegeStudent;
import spring_mvc.models.GradebookCollegeStudent;
import spring_mvc.repository.StudentDao;
import spring_mvc.service.StudentAndGradeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
public class GradebookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentServiceMock;

    @Autowired
    private StudentDao studentDao;

    @BeforeAll
    public static void setupRequest() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Sergey");
        request.setParameter("lastname", "Makarichev");
        request.setParameter("emailAddress", "3@mail.ru");
    }

    @BeforeEach
    public void prepareDatabase() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address)values (1, 'Sergey', 'Makarichev', '1@mail.ru')");
    }

    @AfterEach
    public void cleanDatabase() {
        jdbc.execute("delete from student");
    }

    @Test
    public void getStudentRequestTest() throws Exception {
        CollegeStudent student1 = new GradebookCollegeStudent("Sergey", "Makarichev", "1@mail.ru");
        CollegeStudent student2 = new GradebookCollegeStudent("Sergey2", "Makarichev2", "2@mail.ru");
        List<CollegeStudent> studentsList = new ArrayList<>(Arrays.asList(student1, student2));
        when(studentServiceMock.getGradebook()).thenReturn(studentsList);
        assertIterableEquals(studentsList, studentServiceMock.getGradebook());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/")).andExpect(status().isOk()).andReturn();
        ModelAndView mav = result.getModelAndView();
        if (mav != null) {
            ModelAndViewAssert.assertViewName(mav, "index");
        }

    }

    @Test
    public void createStudentRequest() throws Exception {

        CollegeStudent studentOne = new CollegeStudent("Sergey", "Makarichev", "1@mail.ru");
        List<CollegeStudent> studentList = new ArrayList<>(List.of(studentOne));

        when(studentServiceMock.getGradebook()).thenReturn(studentList);

        assertIterableEquals(studentList, studentServiceMock.getGradebook());

        MvcResult result = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = result.getModelAndView();
        if (mav != null) {
            ModelAndViewAssert.assertViewName(mav, "index");
        }

        CollegeStudent checkStudent = studentDao.findByEmailAddress("3@mail.ru");
        assertNotNull(checkStudent);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}", 1)).andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        if (mav != null) {
            ModelAndViewAssert.assertViewName(mav, "index");
        }
        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    public void deleteStudentErrorPageTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}", 0)).andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        if (mav != null) {
            ModelAndViewAssert.assertViewName(mav, "error");
        }
    }
}
