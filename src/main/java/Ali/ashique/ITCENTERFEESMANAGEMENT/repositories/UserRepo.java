package Ali.ashique.ITCENTERFEESMANAGEMENT.repositories;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, String> {
//    Optional<User> findByName(String username);
}