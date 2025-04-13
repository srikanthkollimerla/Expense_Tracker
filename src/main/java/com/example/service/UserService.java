package com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(String name,String email) {
        User user = new User(name,email);
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public Optional<UserDTO> getUserById(int id) {
        return userRepository.findById(id)
                .map(this::toDTO);
    }

    public Optional<UserDTO> getUserByName(String name) {
        return userRepository.findByName(name)
                .map(this::toDTO);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // Utility to convert entity to DTO
    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
