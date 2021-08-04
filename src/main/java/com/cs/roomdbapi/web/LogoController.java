package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Media;
import com.cs.roomdbapi.dto.MediaSaveRequest;
import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.MediaManager;
import com.cs.roomdbapi.manager.PropertyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Tag(
        name = "Logo",
        description = "API endpoints to access property logo(s) additional information about it. <br/>" +
                "This endpoints are simplified mirror of the media endpoints. <br/>" +
                "Made for convenience, Media endpoints still can be used to get and remove logo(s)"
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/logo", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogoController {

    private final MediaManager mediaManager;

    private final PropertyManager propertyManager;

    @Operation(
            summary = "Get list of all logo(s), by property id.",
            description = "All fields of the logo entity will be included in result."
    )
    @GetMapping({"/{propertyId}"})
    public ResponseEntity<List<Media>> getAllLogoForProperty(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId
    ) {
        List<Media> all = mediaManager.getAllLogosByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Upload logo file with additional information."
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Media> upload(
            @RequestParam("file")
                    MultipartFile file,
            @Valid @RequestParam
            @Min(1000000)
            @NotNull(message = "Property id should be provided.")
            @Schema(description = "Room DB internal property id.", example = "1000003")
                    Integer propertyId,
            @Valid @RequestParam(required = false)
            @Min(1)
            @Schema(description = "Room DB internal license type id.", example = "5")
                    Integer licenseTypeId,
            @Valid @RequestParam(required = false)
            @Min(1)
            @Max(1000)
            @Schema(description = "Numeric value that should be used to order logo. Max value is 1000", example = "3")
                    Integer sortOrder,
            HttpServletRequest req
    ) {
        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);

        if (file.isEmpty()) {
            throw new BadRequestException("File not provided.");
        }

        if (licenseTypeId != null && mediaManager.licenseTypeNotExistsById(licenseTypeId)) {
            throw new BadRequestException("License type with provided id does not exists in a system.");
        }

        String fileUrl = mediaManager.saveFile(file);
        if (fileUrl.isEmpty()) {
            throw new BadRequestException("Error while file saving.");
        }

        MediaSaveRequest media = new MediaSaveRequest();
        media.setPropertyId(propertyId);
        media.setMediaTypeId(mediaManager.getMediaTypeIdForLogo());
        media.setLicenseTypeId(licenseTypeId);
        media.setIsMain(false);
        media.setIsLogo(true);
        media.setSortOrder(sortOrder);
        media.setUrl(fileUrl);

        Media newMedia = mediaManager.addMedia(media);

        return new ResponseEntity<>(newMedia, HttpStatus.CREATED);
    }

    private void validateLogoAccess(Integer logoId, HttpServletRequest req) {
        if (mediaManager.mediaNotExistsById(logoId)) {
            throw new BadRequestException("Logo with provided id does not exists in a system.");
        }

        Integer propertyId = mediaManager.getPropertyIdByMediaId(logoId);

        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);
    }

    @Operation(
            summary = "Delete logo by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogo(
            @PathVariable
            @Parameter(description = "RoomDB internal logo Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete logo called with id: {}.", id);

        validateLogoAccess(id, req);

        mediaManager.deleteMedia(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
