package com.cs.roomdbapi.security;

import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.model.Role;
import com.cs.roomdbapi.model.SupplierEntity;
import com.cs.roomdbapi.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cs.roomdbapi.utilities.AppUtils.NAME;
import static com.cs.roomdbapi.utilities.AppUtils.SUPPLIER;

@Service
@RequiredArgsConstructor
public class CSUserDetails implements UserDetailsService {

    private final SupplierRepository supplierRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final SupplierEntity entity = supplierRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, name));


        if (entity == null) {
            throw new UsernameNotFoundException("User '" + name + "' not found");
        }

        List<Role> roles = entity.getRoles();

        List<SimpleGrantedAuthority> rolesList = roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getRoleName().getAuthority()))
                .filter(Objects::nonNull).collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User//
                .withUsername(name)//
                .password(entity.getPassword())//
                .authorities(rolesList)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
