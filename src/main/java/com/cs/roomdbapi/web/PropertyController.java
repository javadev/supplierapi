package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.manager.PropertyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Property API", description = "Property API - some long description will be here.")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    PropertyManager propertyManager;

    public PropertyController(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

//    @Operation(
//            summary = "Get a list of properties",
//            description = "A detailed description of the operation.\n" +
//                    "        Use markdown for rich text representation,\n" +
//                    "        such as **bold**, *italic*, and [links](https://swagger.io)."
//    )
//    @GetMapping
//    public ResponseEntity<List<Property>> getAllProperties() {
//        List<Property> properties = propertyManager.getProperties();
//        return new ResponseEntity<>(properties, HttpStatus.OK);
//    }
//
//    @GetMapping({"/{propertyId}"})
//    public ResponseEntity<Property> getProperty(@PathVariable Long propertyId) {
//
//        return new ResponseEntity<Property>(propertyManager.getPropertyById(propertyId), HttpStatus.OK);
//
//    }
//
//    @PostMapping
//    public ResponseEntity<Property> saveProperty(@RequestBody Property property) {
//        Property propertyCreated = propertyManager.insert(property);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
////        httpHeaders.add("property", "/api/v1/properties/" + propertyCreated.getId().toString());
//
//        return new ResponseEntity<>(propertyCreated, httpHeaders, HttpStatus.CREATED);
//    }
//
//    @PutMapping({"/{propertyId}"})
//    public ResponseEntity<Property> updateProperty(@PathVariable("propertyId") Long propertyId, @RequestBody Property property) {
//        propertyManager.updateProperty(propertyId, property);
//        return new ResponseEntity<>(propertyManager.getPropertyById(propertyId), HttpStatus.OK);
//    }
//
//    @DeleteMapping({"/{propertyId}"})
//    public ResponseEntity<Property> deleteProperty(@PathVariable("propertyId") Long propertyId) {
//        propertyManager.deleteProperty(propertyId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
