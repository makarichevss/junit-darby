package spring_mvc.repository;

import org.springframework.data.repository.CrudRepository;
import spring_mvc.models.HistoryGrade;

public interface HistoryGradesDao extends CrudRepository<HistoryGrade, Integer> {

    Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
