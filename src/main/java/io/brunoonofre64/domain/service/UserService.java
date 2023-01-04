package io.brunoonofre64.domain.service;

import io.brunoonofre64.domain.dto.user.UserInputDTO;
import io.brunoonofre64.domain.dto.user.UserOutpuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserOutpuDTO saveNewUserIndDB(UserInputDTO userDTO);

    UserOutpuDTO getUserByUuid(String uuid);

    Page<UserOutpuDTO> getAllUserPaged(Pageable pageable);

    UserOutpuDTO updateUserByUui(String uuid, UserInputDTO dto);

    void deleteUserByUuid(String uuid);
}
