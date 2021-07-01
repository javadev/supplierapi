package com.cs.roomdbapi.security;

import com.cs.roomdbapi.model.Role;
import com.cs.roomdbapi.model.Supplier;
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

@Service
@RequiredArgsConstructor
public class CSUserDetails implements UserDetailsService {

    private final SupplierRepository supplierRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final Supplier supplier = supplierRepository.findByName(name);

        if (supplier == null) {
            throw new UsernameNotFoundException("User '" + name + "' not found");
        }

        List<Role> roles = supplier.getRoles();

        List<SimpleGrantedAuthority> rolesList = roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getRoleName().getAuthority()))
                .filter(Objects::nonNull).collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User//
                .withUsername(name)//
                .password(supplier.getPassword())//
                .authorities(rolesList)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
