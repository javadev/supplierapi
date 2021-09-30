package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Product;
import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.ProductManager;
import com.cs.roomdbapi.manager.PropertyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Tag(
        name = "Product",
        description = "API endpoints to access Products and additional information about it. <br/>" +
                "Product is a combination of the Sellable Units. " +
                "It's like a package that include different sellable items"
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductManager productManager;

    private final PropertyManager propertyManager;

    @Operation(
            summary = "Get list of all products, by property id.",
            description = "All fields of the product entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<Product>> getAllProducts(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);

        List<Product> all = productManager.getAllProductsByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get product data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Product> getProduct(
            @PathVariable
            @Parameter(description = "RoomDB internal product Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validateProductAccess(id, req);

        return new ResponseEntity<>(productManager.getProductById(id), HttpStatus.OK);
    }

    private void validateProductAccess(Integer productId, HttpServletRequest req) {
        if (productManager.productNotExistsById(productId)) {
            throw new BadRequestException("Product with provided id does not exists in a system.");
        }

        Integer propertyId = productManager.getPropertyIdByProductId(productId);

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);
    }

    // TODO add API methods for descriptions

}
