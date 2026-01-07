package com.cyro.cravekart.service;

import com.cyro.cravekart.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {

  @Autowired
  private UserRepository userRepository;





}
