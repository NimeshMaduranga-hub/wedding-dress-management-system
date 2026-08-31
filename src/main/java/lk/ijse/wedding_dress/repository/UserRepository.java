package lk.ijse.wedding_dress.repository;
import java.util.Optional;
import lk.ijse.wedding_dress.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
