package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.ftpclient.FTPFileWriter;
import com.cs.roomdbapi.mapper.*;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import com.cs.roomdbapi.utilities.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaManagerImpl implements MediaManager {

    private final MediaRepository mediaRepository;

    private final MediaTypeRepository mediaTypeRepository;

    private final MediaAttributeTypeRepository mediaAttributeTypeRepository;

    private final MediaAttributeRepository mediaAttributeRepository;

    private final LicenseTypeRepository licenseTypeRepository;

    private final PredefinedTagRepository predefinedTagRepository;

    private final NotificationManager notificationManager;

    private final FTPFileWriter ftpFileWriter;

    private final PropertyRepository propertyRepository;

    private final MediaTagRepository mediaTagRepository;

    private final DescriptionManager descriptionManager;

    @Value("${media.access-url}")
    String accessUrl;

    @Override
    public List<Media> getAllMedia() {
        List<MediaEntity> all = mediaRepository.findAll();

        return MediaMapper.MAPPER.toListDTO(all);
    }

    @Override
    public boolean mediaNotExistsById(Integer mediaId) {
        return !mediaRepository.existsById(mediaId);
    }

    @Override
    public Media getMediaById(Integer id) {
        MediaEntity entity = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA, ID, id));

        return MediaMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<MediaAttributeType> getAllMediaAttributeTypes() {
        List<MediaAttributeTypeEntity> all = mediaAttributeTypeRepository.findAll();

        return MediaAttributeTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<MediaAttributeType> getAllMediaAttributeTypesByMediaType(Integer mediaTypeId) {
        List<MediaAttributeTypeEntity> all = mediaAttributeTypeRepository.findAllByMediaType_Id(mediaTypeId);

        return MediaAttributeTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public boolean mediaAttributeTypeExistsById(Integer mediaAttributeTypeId) {
        return mediaAttributeTypeRepository.existsById(mediaAttributeTypeId);
    }

    @Override
    public List<MediaType> getAllMediaTypes() {
        List<MediaTypeEntity> all = mediaTypeRepository.findAll();

        return MediaTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public boolean mediaTypeNotExistsById(Integer mediaTypeId) {
        return !mediaTypeRepository.existsById(mediaTypeId);
    }

    @Override
    public Integer getMediaTypeIdForLogo() {
        return mediaTypeRepository.getMediaTypeIdByCode(AppUtils.DEFAULT_LOGO_MEDIA_TYPE_CODE);
    }

    @Override
    public List<LicenseType> getAllLicenseType() {
        List<LicenseTypeEntity> all = licenseTypeRepository.findAll();

        return LicenseTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public boolean licenseTypeNotExistsById(Integer licenseTypeId) {
        return !licenseTypeRepository.existsById(licenseTypeId);
    }

    @Override
    public List<Media> getAllMediaByPropertyId(Integer propertyId) {
        List<MediaEntity> all = mediaRepository.findAllByProperty_Id(propertyId);

        return MediaMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<Media> getAllLogosByPropertyId(Integer propertyId) {
        List<MediaEntity> all = mediaRepository.findAllByProperty_IdAndIsLogo(propertyId, true);

        return MediaMapper.MAPPER.toListDTO(all);
    }

    @Override
    public String saveFile(MultipartFile file) {
        String folder = RandomStringUtils.randomAlphanumeric(24);

        boolean saveFileResult = false;

        ftpFileWriter.open();
        if (ftpFileWriter.isConnected()) {
            try {
                InputStream inputstream = new BufferedInputStream(file.getInputStream());

                saveFileResult = ftpFileWriter.saveFile(inputstream, folder, file.getOriginalFilename());

                inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ftpFileWriter.close();

        if (saveFileResult) {
            return accessUrl + folder + "/" + file.getOriginalFilename();
        } else {
            return "";
        }
    }

    @Override
    public Media addMedia(MediaSaveRequest media) {
        PropertyEntity propertyEntity = propertyRepository.findById(media.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, media.getPropertyId()));

        MediaEntity mediaEntity = MediaMapper.MAPPER.saveRequestToEntity(media);

        if (media.getMediaTypeId() != null) {
            MediaTypeEntity mediaTypeEntity = mediaTypeRepository.findById(media.getMediaTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(MEDIA_TYPE, ID, media.getMediaTypeId()));

            mediaEntity.setMediaType(mediaTypeEntity);
        }

        if (media.getLicenseTypeId() != null) {
            LicenseTypeEntity licenseTypeEntity = licenseTypeRepository.findById(media.getLicenseTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(LICENSE_TYPE, ID, media.getLicenseTypeId()));

            mediaEntity.setLicenseType(licenseTypeEntity);
        }

        mediaEntity.setProperty(propertyEntity);
        mediaEntity.setUrl(media.getUrl());


        MediaEntity save = mediaRepository.save(mediaEntity);
        log.info("Media added: '{}'", save.toString());

        return MediaMapper.MAPPER.toDTO(save);
    }

    @Override
    public Integer getPropertyIdByMediaId(Integer mediaId) {
        return mediaRepository.getPropertyIdByMediaId(mediaId);
    }

    @Override
    public Integer getMediaIdByDescriptionId(Integer descriptionId) {
        return mediaRepository.getMediaIdByDescriptionId(descriptionId);
    }

    @Override
    public List<MediaTag> setMediaTags(MediaTagRequest mediaTags) {
        Integer mediaId = mediaTags.getMediaId();

        mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA, ID, mediaId));

        List<MediaTagEntity> tags = new ArrayList<>();
        for (MediaTag tag : mediaTags.getTags()) {
            if ((tag.getText() == null || tag.getText().isBlank()) && (tag.getPredefinedTagId() == null || tag.getPredefinedTagId() <= 0)) {
                throw new BadRequestException("Tag Text and Predefined Tag Id both are empty. One of those two fields required to be not blank.");
            }
            MediaTagEntity entity = new MediaTagEntity();
            if (tag.getPredefinedTagId() != null) {
                PredefinedTagEntity predefinedTag = predefinedTagRepository.findById(tag.getPredefinedTagId())
                        .orElseThrow(() -> new ResourceNotFoundException(PREDEFINED_TAG, ID, tag.getPredefinedTagId()));
                entity.setPredefinedTag(predefinedTag);
            } else {
                entity.setText(tag.getText());
            }
            entity.setMediaId(mediaId);

            tags.add(entity);
        }

        mediaTagRepository.deleteByMediaId(mediaId);

        List<MediaTagEntity> mediaTagEntities = null;
        if (tags.size() > 0) {
            mediaTagEntities = mediaTagRepository.saveAll(tags);
        }

        return MediaTagMapper.MAPPER.toListDTO(mediaTagEntities);
    }

    @Override
    public List<MediaAttribute> getAllMediaAttributesByMediaId(Integer mediaId) {
        List<MediaAttributeEntity> all = mediaAttributeRepository.findAllByMediaId(mediaId);

        return MediaAttributeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<MediaAttribute> setMediaAttributes(MediaAttributeRequest mediaAttributes) {
        Integer mediaId = mediaAttributes.getMediaId();

        List<MediaAttributeEntity> attributes = new ArrayList<>();
        for (MediaAttributeSave attr : mediaAttributes.getAttributes()) {

            MediaAttributeTypeEntity mediaAttributeType = mediaAttributeTypeRepository.findById(attr.getMediaAttributeTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(MEDIA_ATTRIBUTE_TYPE, ID, attr.getMediaAttributeTypeId()));

            if (attr.getValue().isBlank()) {
                throw new BadRequestException(String.format("Value for media attribute type '%s' should not be blank.", mediaAttributeType.getName()));
            }

            MediaAttributeEntity entity = new MediaAttributeEntity();
            entity.setMediaId(mediaId);
            entity.setMediaAttributeType(mediaAttributeType);
            entity.setValue(attr.getValue());

            if (!attr.getDimension().isBlank()) {
                entity.setDimension(attr.getDimension());
            }

            attributes.add(entity);
        }

        mediaAttributeRepository.deleteByMediaId(mediaId);

        List<MediaAttributeEntity> attributeEntities = null;
        if (attributes.size() > 0) {
            attributeEntities = mediaAttributeRepository.saveAll(attributes);
        }

        return MediaAttributeMapper.MAPPER.toListDTO(attributeEntities);
    }

    @Override
    @Transactional
    public Description addMediaDescription(Integer mediaId, DescriptionSave descriptionToSave) {
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, DEFAULT_MEDIA_DESCRIPTION_TYPE_CODE);

        MediaEntity mediaEntity = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA, ID, mediaId));

        mediaEntity.getDescriptions().add(savedDescription);
        mediaRepository.save(mediaEntity);

        return DescriptionMapper.MAPPER.toDTO(savedDescription);
    }

    @Override
    public MediaAttribute addMediaAttribute(MediaAttributeSaveOne req) {
        Integer mediaId = req.getMediaId();

        Integer mediaAttributeTypeId = req.getMediaAttributeTypeId();
        MediaAttributeTypeEntity mediaAttributeType = mediaAttributeTypeRepository.findById(mediaAttributeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA_ATTRIBUTE_TYPE, ID, mediaAttributeTypeId));

        Optional<MediaAttributeEntity> existing = mediaAttributeRepository.findTopByMediaIdAndMediaAttributeType_Id(mediaId, mediaAttributeTypeId);

        if (req.getValue().isBlank()) {
            throw new BadRequestException(String.format("Value for media attribute type '%s' should not be blank.", mediaAttributeType.getName()));
        }

        MediaAttributeEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();

            entity.setValue(req.getValue());
            if (req.getDimension() != null && !req.getDimension().isBlank()) {
                entity.setDimension(req.getDimension());
            } else {
                entity.setDimension("");
            }
        } else {
            entity = new MediaAttributeEntity();
            entity.setMediaId(mediaId);
            entity.setMediaAttributeType(mediaAttributeType);
            entity.setValue(req.getValue());

            if (req.getDimension() != null && !req.getDimension().isBlank()) {
                entity.setDimension(req.getDimension());
            }
        }

        MediaAttributeEntity save = mediaAttributeRepository.save(entity);
        save.getMediaAttributeType().setPredefinedValues(null); // avoid return predefined values

        return MediaAttributeMapper.MAPPER.toDTO(save);
    }

    @Override
    @Transactional
    public void deleteMedia(Integer id) {
        MediaEntity mediaEntity = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA, ID, id));
        String url = mediaEntity.getUrl();

        mediaAttributeRepository.deleteByMediaId(id);
        mediaRepository.delete(mediaEntity); // TODO check that descriptions are removed

        if (url != null && !url.isBlank()) {
            String filename = url.replace(accessUrl, "");

            ftpFileWriter.open();
            if (ftpFileWriter.isConnected()) {
                ftpFileWriter.removeFile(filename);
            }
            ftpFileWriter.close();
        }
    }
}
