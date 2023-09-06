package com.example.todos.auth;

import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.todos.auth.dto.AuthenticationErrorDTO;
import com.example.todos.auth.dto.AuthenticationRequest;
import com.example.todos.auth.dto.AuthenticationResponse;
import com.example.todos.auth.dto.AuthenticationTokenDTO;
import com.example.todos.auth.dto.RegisterRequest;
import com.example.todos.config.JwtService;
import com.example.todos.enums.Role;
import com.example.todos.model.TodoDAO;
import com.example.todos.model.dto.TodoResponse;
import com.example.todos.user.User;
import com.example.todos.user.UserDTO;
import com.example.todos.user.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {

    var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
        .email(request.getEmail()).username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();

    var userThroughUsername = userRepository.findByUsername(request.getUsername());
    var userThroughEmail = userRepository.findByEmail(request.getEmail());

    if (userThroughUsername.isPresent()) {
      return AuthenticationErrorDTO.builder().message("Username already exists").build();
    }

    if (userThroughEmail.isPresent()) {
      return AuthenticationErrorDTO.builder().message("Email already  registered").build();
    }


    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);

    var savedUser = userRepository.findByUsername(request.getUsername()).orElseThrow();

    var userDto = UserDTO.builder().email(savedUser.getEmail()).firstName(savedUser.getFirstName())
        .lastName(savedUser.getLastName()).username(savedUser.getUsername()).id(savedUser.getId())
        .todos(extractTodoResponse(savedUser.getTodos())).build();

    return AuthenticationTokenDTO.builder().token(jwtToken).data(userDto).build();
  }

  private static TodoResponse extractTodoResponse(TodoDAO todo) {

    return TodoResponse.builder().id(todo.getId()).priority(todo.getPriority())
        .status(todo.getStatus()).description(todo.getDescription()).title(todo.getTitle())
        .userId(todo.getUser().getId()).build();

  }

  private Set<TodoResponse> extractTodoResponse(Set<TodoDAO> todos) {
    
    if(todos == null) return Collections.emptySet();
    
    return todos.stream().map(AuthenticationService::extractTodoResponse)
        .collect(Collectors.toSet());
    
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
   
    var userDto = UserDTO.builder().email(user.getEmail()).firstName(user.getFirstName())
        .lastName(user.getLastName()).username(user.getUsername()).id(user.getId())
        .todos(extractTodoResponse(user.getTodos())).build();
    
    if (userRepository.findByUsername(request.getUsername()).isEmpty()) {
      return AuthenticationErrorDTO.builder().message("Invalid credentials").build();
    }

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationTokenDTO.builder().token(jwtToken).data(userDto).build();
  }


}
