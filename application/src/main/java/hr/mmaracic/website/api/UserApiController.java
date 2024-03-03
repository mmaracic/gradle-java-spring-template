package hr.mmaracic.website.api;

import hr.mmaracic.api.UserApi;
import hr.mmaracic.api.model.UserDto;
import hr.mmaracic.website.mapper.DtoMapper;
import hr.mmaracic.website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    private final DtoMapper dtoMapper;

    @Override
    public ResponseEntity<Long> userCount() {
        return ResponseEntity.ok(userService.countUsers());
    }

    @Override
    public ResponseEntity<UserDto> userByName(String name) {
        try {
            return ResponseEntity.ok(dtoMapper.mapToDto(userService.getUserByName(name)));
        } catch (Exception exc) {
            //Can also implement global @ControllerAdvice that would return error messages as body
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unknown user", exc);
        }

    }
}
