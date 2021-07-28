package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.MediaManager;
import com.cs.roomdbapi.manager.PropertyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

@Tag(
        name = "Media",
        description = "API endpoints to access media and additional information about it."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/media", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediaController {

    private final MediaManager mediaManager;

    private final PropertyManager propertyManager;

    @Operation(
            summary = "Get list of all media, by property id.",
            description = "All fields of the media entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<Media>> getAllMedia(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId
    ) {
        List<Media> all = mediaManager.getAllMediaByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all media types."
    )
    @GetMapping({"/media-types"})
    public ResponseEntity<List<com.cs.roomdbapi.dto.MediaType>> getAllMediaTypes() {
        List<com.cs.roomdbapi.dto.MediaType> all = mediaManager.getAllMediaTypes();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all License types."
    )
    @GetMapping({"/license-types"})
    public ResponseEntity<List<LicenseType>> getAllLicenseTypes() {
        List<LicenseType> all = mediaManager.getAllLicenseType();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Upload media file with additional information."
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
            @Schema(description = "Room DB internal media type id.", example = "3")
                    Integer mediaTypeId,
            @Valid @RequestParam(required = false)
            @Min(1)
            @Schema(description = "Room DB internal license type id.", example = "5")
                    Integer licenseTypeId,
            @Valid @RequestParam(required = false)
            @Schema(description = "Specifies whether the image should be displayed as the main photo for the property", example = "false")
                    Boolean isMain,
            @Valid @RequestParam(required = false)
            @Schema(description = "Is the image a logo for the property", example = "false")
                    Boolean isLogo,
            @Valid @RequestParam(required = false)
            @Min(1)
            @Max(1000)
            @Schema(description = "Numeric value that should be used to order media. Max value is 1000", example = "3")
                    Integer sortOrder,
            HttpServletRequest req
    ) {
        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);

        if (file.isEmpty()) {
            throw new BadRequestException("File not provided.");
        }

        if (!mediaManager.mediaTypeExistsById(mediaTypeId)) {
            throw new BadRequestException("Media type with provided id does not exists in a system.");
        }

        if (!mediaManager.licenseTypeExistsById(licenseTypeId)) {
            throw new BadRequestException("License type with provided id does not exists in a system.");
        }

        String fileUrl = mediaManager.saveFile(file);
        if (fileUrl.isEmpty()) {
            throw new BadRequestException("Error while file saving.");
        }

        MediaSaveRequest media = new MediaSaveRequest();
        media.setPropertyId(propertyId);
        media.setMediaTypeId(mediaTypeId);
        media.setLicenseTypeId(licenseTypeId);
        media.setIsMain(isMain);
        media.setIsLogo(isLogo);
        media.setSortOrder(sortOrder);
        media.setUrl(fileUrl);

        Media newMedia = mediaManager.addMedia(media);

        return new ResponseEntity<>(newMedia, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get media data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Media> getMedia(
            @PathVariable
            @Parameter(description = "RoomDB internal media Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(mediaManager.getMediaById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Set tags to media.",
            description = "Previous tags will be removed and only new not blank tags will be attached to media. <br/>" +
                    "If provided tags array empty all existing tags will be removed."
    )
    @PostMapping({"/set-tags"})
    public ResponseEntity<List<MediaTag>> setTags(
            @Valid
            @RequestBody
                    MediaTagRequest mediaTagRequest,
            HttpServletRequest req
    ) {
        validateMediaAccess(mediaTagRequest.getMediaId(), req);

        List<MediaTag> mediaTags = mediaManager.setMediaTags(mediaTagRequest);

        return new ResponseEntity<>(mediaTags, HttpStatus.CREATED);
    }

    private void validateMediaAccess(Integer mediaId, HttpServletRequest req) {
        if (!mediaManager.mediaExistsById(mediaId)) {
            throw new BadRequestException("Media with provided id does not exists in a system.");
        }

        Integer propertyId = mediaManager.getPropertyIdByMediaId(mediaId);

        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);
    }

    @Operation(
            summary = "Get list of all media attribute types."
    )
    @GetMapping({"/media-attribute-types"})
    public ResponseEntity<List<MediaAttributeType>> getAllMediaAttributeTypes() {
        List<MediaAttributeType> all = mediaManager.getAllMediaAttributeTypes();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all media attribute types for provided media type."
    )
    @GetMapping({"/media-attribute-types-by-media-type/{mediaTypeId}"})
    public ResponseEntity<List<MediaAttributeType>> getAllMediaAttributeTypes(
            @PathVariable
            @Parameter(description = "RoomDB internal media type Id. Required.")
            @Min(1)
                    Integer mediaTypeId
    ) {
        if (!mediaManager.mediaTypeExistsById(mediaTypeId)) {
            throw new BadRequestException("Media type with provided id does not exists in a system.");
        }

        List<MediaAttributeType> all = mediaManager.getAllMediaAttributeTypesByMediaType(mediaTypeId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of media attributes for media."
    )
    @GetMapping({"/media-attribute-by-media/{mediaId}"})
    public ResponseEntity<List<MediaAttribute>> mediaAttributeByMedia(
            @PathVariable
            @Parameter(description = "RoomDB internal media Id. Required.")
            @Min(1)
                    Integer mediaId,
            HttpServletRequest req
    ) {
        validateMediaAccess(mediaId, req);

        List<MediaAttribute> all = mediaManager.getAllMediaAttributesByMediaId(mediaId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set attributes to media.",
            description = "Previous attributes will be removed and only new valid attributes will be added to media. <br/>" +
                    "If provided attributes array empty all existing attributes will be removed."
    )
    @PostMapping({"/set-attributes"})
    public ResponseEntity<List<MediaAttribute>> setAttributes(
            @Valid
            @RequestBody
                    MediaAttributeRequest mediaAttributeRequest,
            HttpServletRequest req
    ) {
        validateMediaAccess(mediaAttributeRequest.getMediaId(), req);

        List<MediaAttribute> mediaAttributes = mediaManager.setMediaAttributes(mediaAttributeRequest);

        return new ResponseEntity<>(mediaAttributes, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add or update Media Attribute.",
            description = "If Media Attribute already **exists** for the provided Media than it's value will be updated. <br/>" +
                    "If value does **not** exists for media, new attribute will be added."
    )
    @PatchMapping("/add-attribute")
    public ResponseEntity<MediaAttribute> addAttribute(
            @Valid
            @RequestBody
                    MediaAttributeSaveOne mediaAttributeSaveOne,
            HttpServletRequest req
    ) {
        validateMediaAccess(mediaAttributeSaveOne.getMediaId(), req);

        MediaAttribute mediaAttribute = mediaManager.addMediaAttribute(mediaAttributeSaveOne);

        return new ResponseEntity<>(mediaAttribute, HttpStatus.CREATED);
    }

}
