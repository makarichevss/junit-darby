package spring_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_mvc.models.CollegeStudent;
import spring_mvc.models.Gradebook;
import spring_mvc.service.StudentAndGradeService;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentService;

    @GetMapping(value = "/")
    public String getStudents(Model m) {
        Iterable<CollegeStudent> studentsList = studentService.getGradebook();
        m.addAttribute("student", studentsList);
        return "index";
    }

    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
        Iterable<CollegeStudent> studentsList2 = studentService.getGradebook();
        m.addAttribute("students", studentsList2);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {
        if (!studentService.checkIfStudentIsNull(id))
            return "error";
        studentService.deleteStudent(id);
        Iterable<CollegeStudent> studentsList3 = studentService.getGradebook();
        m.addAttribute("students", studentsList3);
        return "index";
    }

}
