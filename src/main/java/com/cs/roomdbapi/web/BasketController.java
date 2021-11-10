package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.BasketManager;
import com.cs.roomdbapi.manager.DescriptionManager;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@RequestMapping(value = "/api/v1/baskets", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class BasketController {

    private final BasketManager basketManager;

    private final PropertyManager propertyManager;

    private final ValidationManager validationManager;

    private final DescriptionManager descriptionManager;

    @Operation(
            summary = "Get list of all baskets, by property id.",
            description = "All fields of the basket entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<Basket>> getAllBaskets(
            @Valid
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
            @Valid
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

    @Operation(
            summary = "Add basket."
    )
    @PostMapping
    public ResponseEntity<Basket> addBasket(
            @Valid
            @RequestBody
                    Basket basket
    ) {
        Basket added = basketManager.addBasket(basket);

        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update basket."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal basket Id. Required.")
            @Min(1)
                    Integer id,
            @Valid
            @RequestBody
                    BasketUpdate basket
    ) {
        Basket updatedLang = basketManager.updateBasket(id, basket);

        return ResponseEntity.ok(updatedLang);
    }

    @Operation(
            summary = "Delete basket by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal basket Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validateBasketAccess(id, req);

        basketManager.deleteBasket(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Add description to basket.",
            description = "Description type is not required and will be set to 'Basket' if not provided."
    )
    @PostMapping({"/description/{basketId}"})
    public ResponseEntity<Description> addDescription(
            @Valid
            @PathVariable("basketId")
            @Parameter(description = "RoomDB internal basket Id. Required.")
            @Min(1)
                    Integer basketId,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        validateBasketAccess(basketId, req);

        Description description = basketManager.addBasketDescription(basketId, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update description for basket.",
            description = "Description type is not required and will be set to 'Basket' if not provided."
    )
    @PatchMapping("/description/{id}")
    public ResponseEntity<Description> updateDescription(
            @Valid
            @PathVariable("id")
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        Integer basketId = basketManager.getBasketIdByDescriptionId(id);
        if (basketId == null) {
            throw new BadRequestException(String.format("Description with id '%s' does not belong to Basket.", id));
        }
        validateBasketAccess(basketId, req);

        Description description = descriptionManager.updateDescription(id, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete description for basket."
    )
    @DeleteMapping("/description/{id}")
    public ResponseEntity<Void> deleteDescription(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        Integer basketId = basketManager.getBasketIdByDescriptionId(id);
        if (basketId == null) {
            throw new BadRequestException(String.format("Description with id '%s' does not belong to Basket.", id));
        }
        validateBasketAccess(basketId, req);

        descriptionManager.deleteBasketDescription(basketId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of sellable units that are in basket."
    )
    @GetMapping({"/sellable-units/{basketId}"})
    public ResponseEntity<List<BasketSellableUnit>> getSellableUnitsByBasket(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal basket Id. Required.")
            @Min(1)
                    Integer basketId,
            HttpServletRequest req
    ) {
        validateBasketAccess(basketId, req);

        List<BasketSellableUnit> all = basketManager.getSellableUnitsByBasketId(basketId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set sellable units to basket.",
            description = "Previous sellable units will be removed and only provided in request sellable units will be added to basket. <br/>" +
                    "If provided sellable units array empty all existing sellable units will be removed."
    )
    @PostMapping({"/sellable-units/set"})
    public ResponseEntity<List<BasketSellableUnit>> setSellableUnits(
            @Valid
            @RequestBody
                    BasketSellableUnitRequest basketSellableUnitRequest,
            HttpServletRequest req
    ) {
        validateBasketAccess(basketSellableUnitRequest.getBasketId(), req);

        List<BasketSellableUnit> all = basketManager.setSellableUnits(basketSellableUnitRequest);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add or update sellable unit in basket.",
            description = "If Sellable Unit already **in** the provided Basket than it's value will be updated. <br/>" +
                    "If sellable unit does **not in** basket, it will be added."
    )
    @PatchMapping("/sellable-units/add")
    public ResponseEntity<BasketSellableUnit> addSellableUnit(
            @Valid
            @RequestBody
                    BasketSellableUnitSaveOne basketSellableUnitSaveOne,
            HttpServletRequest req
    ) {
        validateBasketAccess(basketSellableUnitSaveOne.getBasketId(), req);

        BasketSellableUnit result = basketManager.addSellableUnit(basketSellableUnitSaveOne);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get list of baskets in which sellable unit is included."
    )
    @GetMapping("/sellable-units/baskets/{sellableUnitId}")
    public ResponseEntity<List<Basket>> getBasketsBySellableUnitId(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<Basket> result = basketManager.getBasketsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
