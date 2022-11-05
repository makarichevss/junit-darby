package spring_mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_mvc.models.*;
import spring_mvc.repository.HistoryGradesDao;
import spring_mvc.repository.MathGradesDao;
import spring_mvc.repository.ScienceGradesDao;
import spring_mvc.repository.StudentDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Autowired
    private StudentGrades studentGrades;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
            mathGradeDao.deleteByStudentId(id);
            scienceGradeDao.deleteByStudentId(id);
            historyGradeDao.deleteByStudentId(id);
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

    public int deleteGrade(int id, String gradeType) {
        int studentId = 0;
        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradeDao.deleteById(id);
        }
        if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradeDao.deleteById(id);
        }
        if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradeDao.deleteById(id);
        }
        return studentId;
    }

    public GradebookCollegeStudent getStudentInfo(int i) {
        if (!checkIfStudentIsNull(i)) {
            return null;
        }
        Optional<CollegeStudent> student = studentDao.findById(i);
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(i);
        List<Grade> mathGradeList = new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);
        studentGrades.setMathGradeResults(mathGradeList);
        return new GradebookCollegeStudent(student.get().getId(), student.get().getFirstname(), student.get().getLastname(), student.get().getEmailAddress(), studentGrades);
    }
}
