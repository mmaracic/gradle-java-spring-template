package hr.mmaracic.website.service;

import hr.mmaracic.model.User;
import hr.mmaracic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long countUsers() {
        return userRepository.count();
    }

    public User getUserByName(String name) throws Exception {
        return Optional.ofNullable(userRepository.findByName(name)).orElseThrow(() -> new Exception("Unknown user"));
    }
}
