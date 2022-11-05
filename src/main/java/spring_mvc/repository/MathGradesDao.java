package spring_mvc.repository;

import org.springframework.data.repository.CrudRepository;
import spring_mvc.models.MathGrade;

public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {

    Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
