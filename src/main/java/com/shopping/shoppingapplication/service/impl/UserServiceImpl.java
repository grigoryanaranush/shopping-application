package com.shopping.shoppingapplication.service.impl;

import com.shopping.shoppingapplication.domain.dto.UserDTO;
import com.shopping.shoppingapplication.domain.entity.Role;
import com.shopping.shoppingapplication.domain.entity.User;
import com.shopping.shoppingapplication.exception.user.UserNotFoundException;
import com.shopping.shoppingapplication.repository.RoleRepository;
import com.shopping.shoppingapplication.repository.UserRepository;
import com.shopping.shoppingapplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements  UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void blockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        user.setBlocked(true);
        userRepository.save(user);
    }

    public void unblockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        user.setBlocked(false);
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        return authorities;
    }

    public Long save(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        List<Role> roles = userDTO.getRoles().parallelStream()
                .map(roleRepository::findRoleByName)
                .collect(Collectors.toList());

        user.setRoles(roles);
        user = userRepository.save(user);

        return user.getId();
    }
}
