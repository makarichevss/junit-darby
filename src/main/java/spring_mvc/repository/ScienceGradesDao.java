package spring_mvc.repository;

import org.springframework.data.repository.CrudRepository;
import spring_mvc.models.ScienceGrade;

public interface ScienceGradesDao  extends CrudRepository<ScienceGrade, Integer> {

    Iterable<ScienceGrade> findGradeByStudentId(int id);
}
