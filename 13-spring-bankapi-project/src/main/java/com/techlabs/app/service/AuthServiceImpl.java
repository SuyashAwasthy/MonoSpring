package com.techlabs.app.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.entity.Admin;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.BankApiException;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private AdminRepository adminRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private Admin save;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AdminRepository adminRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.adminRepository = adminRepository;
	}

	@Override
	public String login(LoginDto loginDto) {

	//	System.out.println("error1");
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);

	//	System.out.println("error2");
		
		return token;
	}

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }


        Set<Role> roles = new HashSet<>();
        Role role;
        if (registerDto.isAdmin()) {
        	
        	Admin admin = new Admin();
            admin.setEmail(registerDto.getEmail());
            admin.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            
            role = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->
                    new BankApiException(HttpStatus.BAD_REQUEST, "Admin role not set up in the database"));
            System.out.println("Admin role ID: " + role.getId());
            
            roles.add(role);
           
            System.out.println(admin);
           
            admin.setName(registerDto.getName());
            save = adminRepository.save(admin);
            
            
        } else {
        	
        	User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            
            role = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                    new BankApiException(HttpStatus.BAD_REQUEST, "User role not set up in the database"));
            System.out.println("User role ID: " + role.getId());
            
            roles.add(role);
            user.setRoles(roles);
            System.out.println(user);
            userRepository.save(user);
        }
        

        return "User registered successfully!";
    }
}
