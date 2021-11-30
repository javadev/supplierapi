package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Product;
import com.cs.roomdbapi.manager.ProductManager;
import com.cs.roomdbapi.manager.ValidationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(
        name = "Products",
        description = "API endpoints to access Products for the properties."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ProductController {

    private final ProductManager productManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all products, by property id.",
            description = "All fields of the Product entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<Product>> getAllProductsForProperty(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, propertyId);

        List<Product> all = productManager.getAllProductsForProperty(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get product data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Product> getProduct(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal product Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validateProductAccess(id, req);

        return new ResponseEntity<>(productManager.getProductById(id), HttpStatus.OK);
    }

}
