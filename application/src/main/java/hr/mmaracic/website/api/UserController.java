package hr.mmaracic.website.api;

import hr.mmaracic.model.User;
import hr.mmaracic.website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/count", produces = "application/json")
    public long countUsers() {
        return userService.countUsers();
    }

    @GetMapping(path = "", produces = "application/json")
    public User getUserByName(String name) throws Exception {
        try {
            return userService.getUserByName(name);
        } catch (Exception exc) {
            //Can also implement global @ControllerAdvice that would retuen error messages as body
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unknown user", exc);
        }

    }
}
