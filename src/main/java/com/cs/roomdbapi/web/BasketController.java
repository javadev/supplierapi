package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Basket;
import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.BasketManager;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.manager.ValidationManager;
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
        name = "Basket",
        description = "API endpoints to access Baskets and additional information about it. <br/>" +
                "Basket is a combination of the Sellable Units. " +
                "It's like a package that include different sellable items"
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/basket", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketController {

    private final BasketManager basketManager;

    private final PropertyManager propertyManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all baskets, by property id.",
            description = "All fields of the basket entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<Basket>> getAllBaskets(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        List<Basket> all = basketManager.getAllBasketsByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get basket data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Basket> getBasket(
            @PathVariable
            @Parameter(description = "RoomDB internal basket Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validateBasketAccess(id, req);

        return new ResponseEntity<>(basketManager.getBasketById(id), HttpStatus.OK);
    }

    private void validateBasketAccess(Integer basketId, HttpServletRequest req) {
        if (basketManager.basketNotExistsById(basketId)) {
            throw new BadRequestException("Basket with provided id does not exists in a system.");
        }

        Integer propertyId = basketManager.getPropertyIdByBasketId(basketId);

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);
    }

    // TODO add API methods for descriptions

}
