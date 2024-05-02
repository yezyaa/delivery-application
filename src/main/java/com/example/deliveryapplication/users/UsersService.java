package com.example.deliveryapplication.users;

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
        List<UsersDto> userDtoList = new ArrayList<>();
        for (UsersEntity usersEntity : usersRepository.findAll()) {
            userDtoList.add(UsersDto.fromEntity(usersEntity));
        }
        return userDtoList;
    }

    // 사용자 단일 조회 (관리자)
    public UsersDto findUser(int id) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        return UsersDto.fromEntity(optionalUsersEntity.get());
    }

    // 사용자 본인 정보 조회
    public UsersProfileDto findMyProfile(int id) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        if (optionalUsersEntity.get().getStatus().equals("탈퇴")) {
            log.info("탈퇴한 사용자입니다. Id = " + id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "탈퇴한 사용자입니다.");
        }

        return UsersProfileDto.fromEntity(optionalUsersEntity.get());
    }

    // 사용자 본인 닉네임 수정
    public UsersProfileDto updateNickname(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setNickname(dto.getNickname());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 이메일 수정
    public UsersProfileDto updateEmail(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setEmail(dto.getEmail());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 전화번호 수정
    public UsersProfileDto updatePhoneNumber(int id, UsersProfileDto dto) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setPhoneNumber(dto.getPhoneNumber());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersProfileDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 비밀번호 수정
    public UsersPasswordDto updatePassword(int id, UsersPasswordDto dto) {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        if (optionalUsersEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

        // 현재 비밀번호 확인
        if (!optionalUsersEntity.get().getPassword().equals(dto.getCurrentPassword())) {
            log.info("현재 비밀번호와 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호와 일치하지 않습니다.");
        }

        // 변경할 비밀번호 확인
        if (!dto.getUpdatePassword().equals(dto.getConfirmPassword())) {
            log.info("새로운 비밀번호와 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 일치하지 않습니다.");
        }

        // 변경할 비밀번호
        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setPassword(dto.getUpdatePassword());
        usersEntity.setUpdatedAt(LocalDateTime.now());
        return UsersPasswordDto.fromEntity(usersRepository.save(usersEntity));
    }

    // 사용자 본인 탈퇴
    public void deleteUser(int id) {
        if (!usersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }

        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(id);
        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setStatus("탈퇴");
        usersEntity.setUpdatedAt(LocalDateTime.now());
        usersRepository.save(usersEntity);
    }
}