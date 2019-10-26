package Ali.ashique.ITCENTERFEESMANAGEMENT.repositories;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Student;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface StudentRepo extends PagingAndSortingRepository<Student, String> {

}
