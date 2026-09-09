package lk.ijse.wedding_dress.service.impl;

import lk.ijse.wedding_dress.dto.WeddingDressPatchDTO;
import lk.ijse.wedding_dress.dto.WeddingDressRequestDTO;
import lk.ijse.wedding_dress.dto.WeddingDressResponseDTO;
import lk.ijse.wedding_dress.entity.DressCategory;
import lk.ijse.wedding_dress.entity.DressColor;
import lk.ijse.wedding_dress.entity.DressImage;
import lk.ijse.wedding_dress.entity.DressInventory;
import lk.ijse.wedding_dress.entity.DressSize;
import lk.ijse.wedding_dress.entity.WeddingDress;
import lk.ijse.wedding_dress.exception.ResourceNotFoundException;
import lk.ijse.wedding_dress.repository.DressCategoryRepository;
import lk.ijse.wedding_dress.repository.DressColorRepository;
import lk.ijse.wedding_dress.repository.DressSizeRepository;
import lk.ijse.wedding_dress.repository.WeddingDressRepository;
import lk.ijse.wedding_dress.service.WeddingDressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WeddingDressServiceImpl
        implements WeddingDressService {

    private static final Logger logger =
            LoggerFactory.getLogger(WeddingDressServiceImpl.class);

    private final WeddingDressRepository weddingDressRepository;

    private final DressCategoryRepository dressCategoryRepository;

    private final DressSizeRepository dressSizeRepository;

    private final DressColorRepository dressColorRepository;


    // =====================================================
    // SAVE
    // =====================================================

    @Override
    public WeddingDressResponseDTO saveDress(
            WeddingDressRequestDTO requestDTO) {

        logger.info(
                "Saving new wedding dress: {}",
                requestDTO.getName()
        );

        WeddingDress weddingDress =
                new WeddingDress();

        // DTO -> Entity

        weddingDress.setName(
                requestDTO.getName()
        );

        weddingDress.setDescription(
                requestDTO.getDescription()
        );

        weddingDress.setPrice(
                requestDTO.getPrice()
        );

        weddingDress.setSize(
                requestDTO.getSize()
        );

        weddingDress.setColor(
                requestDTO.getColor()
        );

        weddingDress.setImage(
                requestDTO.getImage()
        );

        weddingDress.setAvailable(
                requestDTO.getAvailable()
        );


        // Set normalized relationships

        setNormalizedRelationships(
                weddingDress
        );


        WeddingDress savedDress =
                weddingDressRepository.save(
                        weddingDress
                );


        logger.info(
                "Wedding dress saved successfully with id: {}",
                savedDress.getId()
        );


        // Entity -> Response DTO

        return convertToResponseDTO(
                savedDress
        );
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public List<WeddingDressResponseDTO> getAllDresses() {

        logger.info(
                "Fetching all wedding dresses"
        );

        return weddingDressRepository
                .findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public WeddingDressResponseDTO getDressById(
            Long id) {

        logger.info(
                "Fetching wedding dress with id: {}",
                id
        );

        WeddingDress weddingDress =
                weddingDressRepository
                        .findById(id)
                        .orElseThrow(() -> {

                            logger.error(
                                    "Dress not found with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Dress not found with id: " + id
                            );
                        });


        return convertToResponseDTO(
                weddingDress
        );
    }


    // =====================================================
    // PUT
    // =====================================================

    @Override
    public WeddingDressResponseDTO updateDress(
            Long id,
            WeddingDressRequestDTO requestDTO) {

        logger.info(
                "Updating wedding dress with id: {}",
                id
        );

        WeddingDress existingDress =
                weddingDressRepository
                        .findById(id)
                        .orElseThrow(() -> {

                            logger.error(
                                    "Dress not found for update with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Dress not found with id: " + id
                            );
                        });


        // Update normal fields

        existingDress.setName(
                requestDTO.getName()
        );

        existingDress.setDescription(
                requestDTO.getDescription()
        );

        existingDress.setPrice(
                requestDTO.getPrice()
        );

        existingDress.setSize(
                requestDTO.getSize()
        );

        existingDress.setColor(
                requestDTO.getColor()
        );

        existingDress.setImage(
                requestDTO.getImage()
        );

        existingDress.setAvailable(
                requestDTO.getAvailable()
        );


        // Update normalized relationships

        setNormalizedRelationships(
                existingDress
        );


        WeddingDress updatedDress =
                weddingDressRepository.save(
                        existingDress
                );


        logger.info(
                "Wedding dress updated successfully with id: {}",
                id
        );


        return convertToResponseDTO(
                updatedDress
        );
    }


    // =====================================================
    // DELETE
    // =====================================================

    @Override
    public void deleteDress(Long id) {

        logger.info(
                "Deleting wedding dress with id: {}",
                id
        );

        if (!weddingDressRepository.existsById(id)) {

            logger.error(
                    "Dress not found for deletion with id: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Dress not found with id: " + id
            );
        }


        weddingDressRepository.deleteById(
                id
        );


        logger.info(
                "Wedding dress deleted successfully with id: {}",
                id
        );
    }


    // =====================================================
    // PATCH
    // =====================================================

    @Override
    public WeddingDressResponseDTO patchDress(
            Long id,
            WeddingDressPatchDTO patchDTO) {

        logger.info(
                "Partially updating wedding dress with id: {}",
                id
        );

        WeddingDress existingDress =
                weddingDressRepository
                        .findById(id)
                        .orElseThrow(() -> {

                            logger.error(
                                    "Dress not found for patch with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Dress not found with id: " + id
                            );
                        });


        // =================================================
        // PARTIAL UPDATE
        // =================================================

        if (patchDTO.getName() != null) {

            existingDress.setName(
                    patchDTO.getName()
            );
        }


        if (patchDTO.getDescription() != null) {

            existingDress.setDescription(
                    patchDTO.getDescription()
            );
        }


        if (patchDTO.getPrice() != null) {

            existingDress.setPrice(
                    patchDTO.getPrice()
            );
        }


        if (patchDTO.getSize() != null) {

            existingDress.setSize(
                    patchDTO.getSize()
            );
        }


        if (patchDTO.getColor() != null) {

            existingDress.setColor(
                    patchDTO.getColor()
            );
        }


        if (patchDTO.getImage() != null) {

            existingDress.setImage(
                    patchDTO.getImage()
            );
        }


        if (patchDTO.getAvailable() != null) {

            existingDress.setAvailable(
                    patchDTO.getAvailable()
            );
        }


        // Synchronize normalized relationships

        setNormalizedRelationships(
                existingDress
        );


        WeddingDress patchedDress =
                weddingDressRepository.save(
                        existingDress
                );


        logger.info(
                "Wedding dress patched successfully with id: {}",
                id
        );


        return convertToResponseDTO(
                patchedDress
        );
    }


    // =====================================================
    // NORMALIZED RELATIONSHIP HANDLER
    // =====================================================

    private void setNormalizedRelationships(
            WeddingDress dress) {


        // =================================================
        // CATEGORY
        // =================================================

        DressCategory category =
                dressCategoryRepository
                        .findByName("General")
                        .orElseGet(() -> {

                            DressCategory newCategory =
                                    new DressCategory();

                            newCategory.setName(
                                    "General"
                            );

                            newCategory.setDescription(
                                    "General wedding dress category"
                            );

                            return dressCategoryRepository.save(
                                    newCategory
                            );
                        });


        dress.setCategory(
                category
        );


        // =================================================
        // SIZE
        // =================================================

        if (dress.getSize() != null &&
                !dress.getSize().trim().isEmpty()) {

            String sizeName =
                    dress.getSize().trim();


            DressSize size =
                    dressSizeRepository
                            .findBySizeName(sizeName)
                            .orElseGet(() -> {

                                DressSize newSize =
                                        new DressSize();

                                newSize.setSizeName(
                                        sizeName
                                );

                                return dressSizeRepository.save(
                                        newSize
                                );
                            });


            dress.setSizeEntity(
                    size
            );
        }


        // =================================================
        // COLOR
        // =================================================

        if (dress.getColor() != null &&
                !dress.getColor().trim().isEmpty()) {

            String colorName =
                    dress.getColor().trim();


            DressColor color =
                    dressColorRepository
                            .findByColorName(colorName)
                            .orElseGet(() -> {

                                DressColor newColor =
                                        new DressColor();

                                newColor.setColorName(
                                        colorName
                                );

                                return dressColorRepository.save(
                                        newColor
                                );
                            });


            dress.setColorEntity(
                    color
            );
        }


        // =================================================
        // IMAGE
        // =================================================

        if (dress.getImage() != null &&
                !dress.getImage().trim().isEmpty()) {

            String imageUrl =
                    dress.getImage().trim();


            List<DressImage> images =
                    dress.getImages();


            /*
             * Do NOT replace the existing collection.
             */

            if (images.isEmpty()) {

                DressImage image =
                        new DressImage();

                image.setDress(
                        dress
                );

                image.setImageUrl(
                        imageUrl
                );

                image.setPrimary(
                        true
                );

                images.add(
                        image
                );

            } else {

                DressImage image =
                        images.get(0);

                image.setDress(
                        dress
                );

                image.setImageUrl(
                        imageUrl
                );

                image.setPrimary(
                        true
                );
            }
        }


        // =================================================
        // INVENTORY
        // =================================================

        DressInventory inventory =
                dress.getInventory();


        /*
         * Do NOT replace existing inventory.
         */

        if (inventory == null) {

            inventory =
                    new DressInventory();

            inventory.setDress(
                    dress
            );

            inventory.setQuantity(
                    1
            );

            inventory.setConditionStatus(
                    "GOOD"
            );

            dress.setInventory(
                    inventory
            );
        }


        inventory.setAvailableQuantity(
                Boolean.TRUE.equals(
                        dress.getAvailable()
                )
                        ? inventory.getQuantity()
                        : 0
        );
    }


    // =====================================================
    // ENTITY -> RESPONSE DTO
    // =====================================================

    private WeddingDressResponseDTO convertToResponseDTO(
            WeddingDress dress) {

        WeddingDressResponseDTO responseDTO =
                new WeddingDressResponseDTO();


        responseDTO.setId(
                dress.getId()
        );

        responseDTO.setName(
                dress.getName()
        );

        responseDTO.setDescription(
                dress.getDescription()
        );

        responseDTO.setPrice(
                dress.getPrice()
        );

        responseDTO.setSize(
                dress.getSize()
        );

        responseDTO.setColor(
                dress.getColor()
        );

        responseDTO.setImage(
                dress.getImage()
        );

        responseDTO.setAvailable(
                dress.getAvailable()
        );


        // =================================================
        // CATEGORY
        // =================================================

        if (dress.getCategory() != null) {

            responseDTO.setCategory(
                    dress.getCategory().getName()
            );
        }


        // =================================================
        // INVENTORY
        // =================================================

        if (dress.getInventory() != null) {

            responseDTO.setInventoryCondition(
                    dress.getInventory()
                            .getConditionStatus()
            );

            responseDTO.setQuantity(
                    dress.getInventory()
                            .getQuantity()
            );

            responseDTO.setAvailableQuantity(
                    dress.getInventory()
                            .getAvailableQuantity()
            );
        }


        return responseDTO;
    }
}