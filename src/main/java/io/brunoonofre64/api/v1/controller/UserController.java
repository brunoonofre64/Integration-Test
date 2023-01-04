package io.brunoonofre64.api.v1.controller;

import io.brunoonofre64.domain.dto.user.UserInputDTO;
import io.brunoonofre64.domain.dto.user.UserOutpuDTO;
import io.brunoonofre64.infrastructure.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/usuario")
public class UserController {

    private UserServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutpuDTO saveNewUserIndDB(@RequestBody UserInputDTO userDTO) {
        return service.saveNewUserIndDB(userDTO);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutpuDTO getCustomerByUuid(@PathVariable String uuid) {
        return service.getUserByUuid(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserOutpuDTO> getAllUserPaged(Pageable pageable) {
       return service.getAllUserPaged(pageable);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserOutpuDTO updateUserByUui(@PathVariable String uuid,
                                        @RequestBody UserInputDTO dto) {
        return service.updateUserByUui(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUuid(@PathVariable String uuid) {
        service.deleteUserByUuid(uuid);
    }
}
