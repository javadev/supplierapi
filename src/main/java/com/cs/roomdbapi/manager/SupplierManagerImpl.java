package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.exception.CustomException;
import com.cs.roomdbapi.model.Role;
import com.cs.roomdbapi.model.Supplier;
import com.cs.roomdbapi.repository.SupplierRepository;
import com.cs.roomdbapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierManagerImpl implements SupplierManager {

    private final SupplierRepository supplierRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public String signIn(String name, String password) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, password);
            authenticationManager.authenticate(authentication);

            Supplier supplier = supplierRepository.findByName(name);
            List<Role> roles = supplier.getRoles();

            return jwtTokenProvider.createToken(name, roles);
        } catch (AuthenticationException e) {
            log.warn("UserManager::signIn invalid credentials for name '{}' and password '{}'", name, password);

            throw new CustomException("Invalid name/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

//    public String signup(Supplier supplier) {
//        if (!userRepository.existsByUsername(supplier.getUsername())) {
//            supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
//            userRepository.save(supplier);
//            return jwtTokenProvider.createToken(supplier.getUsername(), supplier.getRoles());
//        } else {
//            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY); // TODO add logger
//        }
//    }

//    public void delete(String name) {
//        userRepository.deleteByUsername(name);
//    }
//
//    public Supplier search(String name) {
//        Supplier supplier = userRepository.findByUsername(name);
//        if (supplier == null) {
//            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND); // TODO add logger
//        }
//        return supplier;
//    }
//
//    public Supplier whoami(HttpServletRequest req) {
//        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
//    }

    public String refresh(String name) {
        return jwtTokenProvider.createToken(name, supplierRepository.findByName(name).getRoles());
    }

}
