package io.brunoonofre64.infrastructure.jpa;

import io.brunoonofre64.domain.entities.CustomerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long> {

    CustomerEntity findByUuid(String uuid);

    void deleteByUuid(String uuid);

    boolean existsByUuid(String uuid);

    boolean existsByCpf(String cpf);
}

