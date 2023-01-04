package io.brunoonofre64.infrastructure.jpa;

import io.brunoonofre64.domain.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    boolean existsByUuid(String uuid);

    UserEntity findByUuid(String uuid);

    void deleteByUuid(String uuid);

    boolean existsByLogin(String login);

    UserEntity findByLogin(String login);
}
