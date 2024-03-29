package hr.mmaracic.repository;

import hr.mmaracic.configuration.DataJpaTestConfiguration;
import hr.mmaracic.model.User;
import hr.mmaracic.model.User_;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {DataJpaTestConfiguration.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveValid() {
        userRepository.save(new User("test"));
        List<User> userList = userRepository.findAll();
        assertThat(userList.size(), equalTo(1));
        assertThat(userList.get(0), equalTo(new User("test")));
    }

    @Test
    void testSaveInvalid() {

        User user = new User("too big username");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                userRepository.save(user);
            }
            assertThat(violations.size(), equalTo(1));
            assertThat(violations.toArray(new ConstraintViolation[]{})[0].getMessage(), equalTo("size must be between 1 and 10"));
            ConstraintViolationException exception = assertThrows(
                    ConstraintViolationException.class,
                    () -> userRepository.findAll(),
                    "ConstraintViolationException wasn't thrown"
            );
            assertThat(exception.getMessage(), equalTo("Validation failed for classes [hr.mmaracic.model.User] during persist time for groups [jakarta.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='size must be between 1 and 10', propertyPath=name, rootBeanClass=class hr.mmaracic.model.User, messageTemplate='{jakarta.validation.constraints.Size.message}'}\n]"));
        }
    }

    @Test
    void userNameLengthBetween5and7() {
        userRepository.save(new User("test"));
        userRepository.save(new User("orange"));
        userRepository.save(new User("yellow"));
        userRepository.save(new User("intricate"));
        List<User> userList = userRepository.findAllUsersWithAttributeLengthBetween(User_.name,5,7);
        assertThat(userList.size(), equalTo(2));
        assertThat(userList.get(0), equalTo(new User("orange")));
        assertThat(userList.get(1), equalTo(new User("yellow")));
    }

}
