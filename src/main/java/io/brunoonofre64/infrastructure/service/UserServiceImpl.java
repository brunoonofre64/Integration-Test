package io.brunoonofre64.infrastructure.service;

import io.brunoonofre64.domain.dto.user.UserInputDTO;
import io.brunoonofre64.domain.dto.user.UserOutpuDTO;
import io.brunoonofre64.domain.entities.UserEntity;
import io.brunoonofre64.domain.enums.CodeMessage;
import io.brunoonofre64.domain.exception.DtoNullOrIsEmptyException;
import io.brunoonofre64.domain.exception.ListIsEmptyException;
import io.brunoonofre64.domain.exception.LoginNotFoundException;
import io.brunoonofre64.domain.exception.UuidNotFoundOrNullException;
import io.brunoonofre64.domain.mapper.UserMapper;
import io.brunoonofre64.infrastructure.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if(!userRepository.existsByLogin(login) || ObjectUtils.isEmpty(login)) {
            throw new LoginNotFoundException(CodeMessage.LOGIN_NOT_FOUND);
        }
        UserEntity userEntity = userRepository.findByLogin(login);

        return User
                .builder()
                .username(userEntity.getLogin())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }

    public UserOutpuDTO saveNewUserIndDB(UserInputDTO userDTO) {
        validateUser(userDTO);

        String criptPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(criptPassword);

        UserEntity entity = mapper.convertDTOToEntity(userDTO);

        userRepository.save(entity);

        return mapper.convertEntityToDTO(entity);
    }

    public UserOutpuDTO getUserByUuid(String uuid) {
        validateUserUuid(uuid);
        UserEntity user = userRepository.findByUuid(uuid);

        return mapper.convertEntityToDTO(user);
    }

    public Page<UserOutpuDTO> getAllUserPaged(Pageable pageable) {
        if(userRepository.findAll(pageable).isEmpty()) {
            throw new ListIsEmptyException(CodeMessage.LIST_IS_EMPTY);
        }
        pageable = PageRequest.of(0, 10);

        Page<UserEntity> userList = userRepository.findAll(pageable);

        return mapper.mapPagesUserEntityToDTO(userList);
    }

    public UserOutpuDTO updateUserByUui(String uuid, UserInputDTO dto) {
        validateUserUuid(uuid);
        validateUser(dto);

        UserEntity user = userRepository.findByUuid(uuid);

        user.setLogin(dto.getLogin());
        user.setRole(dto.getRole());

        userRepository.save(user);

        return mapper.convertEntityToDTO(user);
    }

    @Transactional
    public void deleteUserByUuid(String uuid) {
        validateUserUuid(uuid);

        userRepository.deleteByUuid(uuid);
    }

    private static void validateUser(UserInputDTO userDTO) {
        if(ObjectUtils.isEmpty(userDTO.getLogin()) || ObjectUtils.isEmpty(userDTO.getRole())) {
            throw new DtoNullOrIsEmptyException(CodeMessage.DTO_NULL_OR_IS_EMPTY);
        }
    }

    private void validateUserUuid(String uuid) {
        if(ObjectUtils.isEmpty(uuid) || !userRepository.existsByUuid(uuid)) {
            throw new UuidNotFoundOrNullException(CodeMessage.UUID_NOT_FOUND_OR_NULL);
        }
    }
}
