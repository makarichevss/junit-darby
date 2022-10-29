package spring_mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_mvc.models.CollegeStudent;
import spring_mvc.models.HistoryGrade;
import spring_mvc.models.MathGrade;
import spring_mvc.models.ScienceGrade;
import spring_mvc.repository.HistoryGradesDao;
import spring_mvc.repository.MathGradesDao;
import spring_mvc.repository.ScienceGradesDao;
import spring_mvc.repository.StudentDao;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {

        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100) {
            switch (gradeType) {
                case "math" -> {
                    mathGrade.setId(0);
                    mathGrade.setGrade(grade);
                    mathGrade.setStudentId(studentId);
                    mathGradeDao.save(mathGrade);
                    return true;
                }
                case "science" -> {
                    scienceGrade.setId(0);
                    scienceGrade.setGrade(grade);
                    scienceGrade.setStudentId(studentId);
                    scienceGradeDao.save(scienceGrade);
                    return true;
                }
                case "history" -> {
                    historyGrade.setId(0);
                    historyGrade.setGrade(grade);
                    historyGrade.setStudentId(studentId);
                    historyGradeDao.save(historyGrade);
                    return true;
                }
            }
        }
        return false;
    }
}
