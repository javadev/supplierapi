package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaManager {

    List<Media> getAllMedia();

    boolean mediaExistsById(Integer mediaId);

    Media getMediaById(Integer id);

    List<MediaAttributeType> getAllMediaAttributeTypes();

    List<MediaAttributeType> getAllMediaAttributeTypesByMediaType(Integer mediaTypeId);

    boolean mediaAttributeTypeExistsById(Integer mediaAttributeTypeId);

    List<MediaType> getAllMediaTypes();

    boolean mediaTypeExistsById(Integer mediaTypeId);

    List<LicenseType> getAllLicenseType();

    boolean licenseTypeExistsById(Integer licenseTypeId);

    List<Media> getAllMediaByPropertyId(Integer propertyId);

    String saveFile(MultipartFile file);

    Media addMedia(MediaSaveRequest media);

    Integer getPropertyIdByMediaId(Integer mediaId);

    List<MediaTag> setMediaTags(MediaTagRequest mediaTags);

    List<MediaAttribute> getAllMediaAttributesByMediaId(Integer mediaId);

    List<MediaAttribute> setMediaAttributes(MediaAttributeRequest mediaAttributes);

    MediaAttribute addMediaAttribute(MediaAttributeSaveOne req);

}
