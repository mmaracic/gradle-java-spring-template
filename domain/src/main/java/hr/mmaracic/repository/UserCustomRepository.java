package hr.mmaracic.repository;

import hr.mmaracic.model.User;
import jakarta.persistence.metamodel.Attribute;

import java.util.List;

public interface UserCustomRepository {
    List<User> findAllUsersWithAttributeLengthBetween(Attribute<User, String> attribute, Integer minWidth, Integer maxWidth);
}
