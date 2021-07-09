package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.dto.SupplierWebhook;
import com.cs.roomdbapi.exception.CustomException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.SupplierMapper;
import com.cs.roomdbapi.model.Role;
import com.cs.roomdbapi.model.SupplierEntity;
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

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierManagerImpl implements SupplierManager {

    private final SupplierRepository supplierRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public String signIn(String supplierName, String password) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(supplierName, password);
            authenticationManager.authenticate(authentication);

            SupplierEntity entity = supplierRepository.findByName(supplierName)
                    .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

            List<Role> roles = entity.getRoles();

            return jwtTokenProvider.createToken(supplierName, roles);
        } catch (AuthenticationException e) {
            log.warn("Invalid credentials for name '{}' and password '{}'", supplierName, password);

            throw new CustomException("Invalid name/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String refreshToken(String supplierName) {
        SupplierEntity entity = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        return jwtTokenProvider.createToken(supplierName, entity.getRoles());
    }

    public SupplierWebhook saveWebhook(String supplierName, SupplierWebhook webhookUpdate) {
        SupplierEntity entity = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));


        // TODO add validation that webhook url exists

        entity.setWebhook(webhookUpdate.getWebHookUrl());
        supplierRepository.save(entity);

        log.info("Webhook for name '{}' updated to '{}'", supplierName, entity.getWebhook());

        return new SupplierWebhook(entity.getWebhook());
    }

    public Supplier getByName(String supplierName) {
        SupplierEntity entity = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        return SupplierMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<Supplier> getSuppliers() {
        List<SupplierEntity> all = supplierRepository.findAll();

        return SupplierMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<Supplier> getSuppliersActiveWithWebhook() {
        List<SupplierEntity> all = supplierRepository.findByIsActiveIsTrueAndWebhookIsNotNull();

        return SupplierMapper.MAPPER.toListDTO(all);
    }

    @Override
    public String getWebhookById(Integer supplierId) {
        SupplierEntity supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, ID, supplierId));

        return supplier.getWebhook();
    }

}
