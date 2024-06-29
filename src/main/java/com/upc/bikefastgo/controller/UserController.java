package com.upc.bikefastgo.controller;

import com.upc.bikefastgo.dto.UserDto;
import com.upc.bikefastgo.exception.ResourceNotFoundException;
import com.upc.bikefastgo.exception.ValidationException;
import com.upc.bikefastgo.model.User;
import com.upc.bikefastgo.repository.UserRepository;
import com.upc.bikefastgo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "UserController", description = "the user API")
@RestController
@RequestMapping("/api/bikefastgo/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/users
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/users/{userId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") Long userId) {
        existsUserByUserId(userId);
        User user = userService.getUserById(userId);
        UserDto userDto = convertToDto(user);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/users/{userId}/image
    // Method: GET
    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> displayImage(@PathVariable(name= "userId")Long userId)throws IOException, SQLException{
        User user = userService.getUserById(userId);
        byte[] imageBytes = null;
        imageBytes = user.getImage().getBytes(1,(int)user.getImage().length());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }


    // URL: http://localhost:8080/api/bikefastgo/v1/users/{userId}/addImage
    // Method: POST
    @PostMapping("/{userId}/addImage")
    public ResponseEntity<UserDto> addImageUser(@PathVariable(name = "userId") Long userId, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        User user = userService.getUserById(userId);
        user.setImage(blob);
        existsUserByUserId(userId);
        validateUser(user);
        user.setId(userId);
        User responseUser = ifDifferentOrEmptyUpdate(user);
        return new ResponseEntity<UserDto>(convertToDto(userService.updateUser(responseUser)), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/users/{userId}
    // Method: PUT
    @Transactional
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "userId") Long userId, @RequestBody User user) {
        existsUserByUserId(userId);
        validateUser(user);
        user.setId(userId);
        User responseUser = ifDifferentOrEmptyUpdate(user);
        return new ResponseEntity<UserDto>(convertToDto(responseUser), HttpStatus.OK);

    }

    // URL: http://localhost:8080/api/bikefastgo/v1/users/{userId}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId) {
        existsUserByUserId(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userFirstName(user.getUserFirstName())
                .userLastName(user.getUserLastName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userBirthDate(user.getUserBirthDate())
                .imageData(user.getImageData())
                .longitudeData(user.getLongitudeData())
                .latitudeData(user.getLatitudeData())
                .bicycles(user.getBicycles())
                .cards(user.getCards())
                .build();
    }

    private void validateUser(User user) {
        if (user.getUserFirstName() == null || user.getUserFirstName().isEmpty()) {
            throw new ValidationException("El nombre del usuario debe ser obligatorio");
        }
        if (user.getUserFirstName().length() > 50) {
            throw new ValidationException("El nombre del usuario no debe exceder los 50 caracteres");
        }
        if (user.getUserLastName() == null || user.getUserLastName().isEmpty()) {
            throw new ValidationException("El apellido del usuario debe ser obligatorio");
        }
        if (user.getUserLastName().length() > 50) {
            throw new ValidationException("El apellido del usuario no debe exceder los 50 caracteres");
        }
        /*if (user.getUserEmail() == null || user.getUserEmail().isEmpty()) {
            throw new ValidationException("El email del usuario debe ser obligatorio");
        }
        if (user.getUserEmail().length() > 50) {
            throw new ValidationException("El email del usuario no debe exceder los 50 caracteres");
        }
        //if (user.getUserPassword() == null || user.getUserPassword().isEmpty()) {
            throw new ValidationException("La contraseña del usuario debe ser obligatorio");
        }
        if (user.getUserPassword().length() > 100) {
            throw new ValidationException("La contraseña del usuario no debe exceder los 100 caracteres");
        }*/

    }

    private void existsUserByEmail(User user) {
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new ValidationException("Ya existe un usuario con el email " + user.getUserEmail());
        }
    }

    private void existsUserByEmail(String email) {
        if (!userRepository.existsByUserEmail(email)) {
            throw new ResourceNotFoundException("No existe un usuario con el email " + email);
        }
    }

    private void existsUserByUserId(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new ResourceNotFoundException("No existe un usuario con el id " + userId);
        }
    }

    private User ifDifferentOrEmptyUpdate(User user){
        return userRepository.findById(user.getId()).map(userToUpdate -> {
            if (user.getUserFirstName() != null && !user.getUserFirstName().isEmpty() && !user.getUserFirstName().equals(userToUpdate.getUserFirstName())) {
                userToUpdate.setUserFirstName(user.getUserFirstName());
            }
            if (user.getUserLastName() != null && !user.getUserLastName().isEmpty() && !user.getUserLastName().equals(userToUpdate.getUserLastName())) {
                userToUpdate.setUserLastName(user.getUserLastName());
            }
            if (user.getUserEmail() != null && !user.getUserEmail().isEmpty() && !user.getUserEmail().equals(userToUpdate.getUserEmail())) {
                userToUpdate.setUserEmail(user.getUserEmail());
            }
            /*if (user.getUserPassword() != null && !user.getUserPassword().isEmpty() && !user.getUserPassword().equals(userToUpdate.getUserPassword())) {
                userToUpdate.setUserPassword(user.getUserPassword());
            }*/
            if (user.getUserBirthDate() != null && !user.getUserBirthDate().equals(userToUpdate.getUserBirthDate())) {
                userToUpdate.setUserBirthDate(user.getUserBirthDate());
            }
            if (user.getUserPhone() != null && !user.getUserPhone().isEmpty() && !user.getUserPhone().equals(userToUpdate.getUserPhone())) {
                userToUpdate.setUserPhone(user.getUserPhone());
            }
            if (user.getImageData() != null && !user.getImageData().isEmpty() && !user.getImageData().equals(userToUpdate.getImageData())) {
                userToUpdate.setImageData(user.getImageData());
            }
            if (user.getLatitudeData() != null && !user.getLatitudeData().equals(userToUpdate.getLatitudeData())){
                userToUpdate.setLatitudeData(user.getLatitudeData());
            }
            if (user.getLongitudeData() != null && !user.getLongitudeData().equals(userToUpdate.getLongitudeData())){
                userToUpdate.setLongitudeData(user.getLongitudeData());
            }
            return userService.updateUser(userToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + user.getId()));
    }
}
