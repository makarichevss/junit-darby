package spring_mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_mvc.models.CollegeStudent;
import spring_mvc.repository.StudentDao;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkStudentIsNotNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }

    public void deleteStudent(int id) {
        if (checkStudentIsNotNull(id)) {
            studentDao.deleteById(id);
        }
    }

    public Collection<CollegeStudent> getGradebook() {
        return (Collection<CollegeStudent>) studentDao.findAll();
    }
}
