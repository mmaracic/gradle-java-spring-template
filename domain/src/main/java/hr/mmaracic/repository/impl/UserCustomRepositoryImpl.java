package hr.mmaracic.repository.impl;

import hr.mmaracic.model.User;
import hr.mmaracic.repository.UserCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllUsersWithAttributeLengthBetween(Attribute<User, String> attribute, Integer minWidth, Integer maxWidth) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> user = cq.from(User.class);
        cq.where(cb.and(
                cb.ge(cb.length(user.get(attribute.getName()).as(String.class)), minWidth),
                cb.le(cb.length(user.get(attribute.getName()).as(String.class)), maxWidth)
        ));
        return entityManager.createQuery(cq).getResultList();
    }
}
