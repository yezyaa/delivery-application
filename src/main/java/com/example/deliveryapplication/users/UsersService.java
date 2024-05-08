package com.example.deliveryapplication.users;

import com.example.deliveryapplication.userAddress.UserAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {
    private final SpringDataJPAUsersRepository usersRepository;
    private final UserAddressService userAddressService;

    // 회원가입
    public void saveUser(UsersRegistrationDto dto) {
        UsersEntity newUser = UsersEntity.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(dto.getPassword())
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();
        usersRepository.save(newUser);
    }

    // 사용자 전체 조회 (관리자)
    public List<UsersDto> findUsers() {
        List<UsersDto> usersDto = new ArrayList<>();
        for (UsersEntity usersEntity : usersRepository.findAll()) {
            usersDto.add(UsersDto.fromEntity(usersEntity));
        }
        return usersDto;
    }

    // 사용자 단일 조회 (관리자)
    public UsersDto findUser(int id) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        return UsersDto.fromEntity(optionalUsers.get());
    }

    // 사용자 본인 정보 조회
    public UsersProfileDto findMyProfile(int id) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        if (optionalUsers.get().getStatus().equals("탈퇴")) {
            log.info("탈퇴한 사용자입니다. Id = " + id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "탈퇴한 사용자입니다.");
        }

        return UsersProfileDto.fromEntity(optionalUsers.get());
    }

    // 사용자 본인 닉네임 수정
    public UsersProfileDto updateNickname(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsers.get();
        usersEntity.setNickname(dto.getNickname());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 이메일 수정
    public UsersProfileDto updateEmail(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsers.get();
        usersEntity.setEmail(dto.getEmail());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 전화번호 수정
    public UsersProfileDto updatePhoneNumber(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsers.get();
        usersEntity.setPhoneNumber(dto.getPhoneNumber());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 비밀번호 수정
    public UsersPasswordDto updatePassword(int id, UsersPasswordDto dto) {
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        // 현재 비밀번호 확인
        if (!optionalUsers.get().getPassword().equals(dto.getCurrentPassword())) {
            log.info("현재 비밀번호와 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호와 일치하지 않습니다.");
        }

        // 변경할 비밀번호 확인
        if (!dto.getUpdatePassword().equals(dto.getConfirmPassword())) {
            log.info("새로운 비밀번호와 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 일치하지 않습니다.");
        }

        // 변경할 비밀번호
        UsersEntity usersEntity = optionalUsers.get();
        usersEntity.setPassword(dto.getUpdatePassword());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersPasswordDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 탈퇴
    public void deleteUser(int id) {
        if (!usersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }

        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        UsersEntity usersEntity = optionalUsers.get();
        usersEntity.setStatus("탈퇴");
        usersEntity.setUpdatedAt(LocalDateTime.now());
        usersRepository.save(usersEntity);
    }
}
