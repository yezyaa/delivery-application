package com.example.deliveryapplication.userAddress;

import com.example.deliveryapplication.users.SpringDataJPAUsersRepository;
import com.example.deliveryapplication.users.UsersEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAddressService {
    private final SpringDataJPAUsersRepository usersRepository;
    private final SpringDataJPAUserAddressRepository userAddressRepository;

    // 주소 등록
    public void saveUserAddress(int userId, UserAddressDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        UserAddressEntity newUserAddress = UserAddressEntity.builder()
                .user(usersEntity)
                .name(dto.getName())
                .address(dto.getAddress())
                .build();
        if (dto.getName() == null)
            newUserAddress.setName(dto.getAddress());
        userAddressRepository.save(newUserAddress);
    }

    // 사용자별 주소 전체 조회
    public List<UserAddressDto> findUserAddressesByUserId(int userId) {
        List<UserAddressDto> userAddressDtoList = new ArrayList<>();
        for (UserAddressEntity userAddressEntity : userAddressRepository.findByUserId(userId)) {
            userAddressDtoList.add(UserAddressDto.fromEntity(userAddressEntity));
        }
        return userAddressDtoList;
    }

    // 사용자별 주소지명 수정
    public UserAddressDto updateUserAddressNameByUserId(int userId, int id, UserAddressDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<UserAddressEntity> optionalUserAddressEntity = userAddressRepository.findById(id);
        if (optionalUserAddressEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주소를 찾을 수 없습니다.");

        UserAddressEntity userAddressEntity = optionalUserAddressEntity.get();
        userAddressEntity.setName(dto.getName());
        return UserAddressDto.fromEntity(userAddressRepository.save(userAddressEntity));
    }

    // 사용자별 주소 수정
    public UserAddressDto updateUserAddressByUserId(int userId, int id, UserAddressDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<UserAddressEntity> optionalUserAddressEntity = userAddressRepository.findById(id);
        if (optionalUserAddressEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주소를 찾을 수 없습니다.");

        UserAddressEntity userAddressEntity = optionalUserAddressEntity.get();
        userAddressEntity.setAddress(dto.getAddress());
        return UserAddressDto.fromEntity(userAddressRepository.save(userAddressEntity));
    }

    // 사용자별 특정 주소 삭제
    public void deleteUserAddressByUserId(int id) {
        Optional<UserAddressEntity> optionalUserAddressEntity = userAddressRepository.findById(id);
        if (optionalUserAddressEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주소를 찾을 수 없습니다.");
        userAddressRepository.deleteById(id);
    }
}
