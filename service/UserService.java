package service;

import domain.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public User createUser(String nom, int age) {
        User user = new User(nom, age);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}