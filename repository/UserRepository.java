package repository;

import domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(int id);
    List<User> findAll();
    void update(User user);
    void delete(int id);
}