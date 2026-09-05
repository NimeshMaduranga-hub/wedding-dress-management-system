package lk.ijse.wedding_dress.service.impl;


import lk.ijse.wedding_dress.entity.WeddingDress;
import lk.ijse.wedding_dress.repository.WeddingDressRepository;
import lk.ijse.wedding_dress.service.WeddingDressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeddingDressServiceImpl implements WeddingDressService {

    private static final Logger logger =
            LoggerFactory.getLogger(WeddingDressServiceImpl.class);

    private final WeddingDressRepository weddingDressRepository;

    @Override
    public WeddingDress saveDress(WeddingDress weddingDress) {

        logger.info("Saving new wedding dress: {}",
                weddingDress.getName());

        return weddingDressRepository.save(weddingDress);
    }

    @Override
    public List<WeddingDress> getAllDresses() {

        logger.info("Fetching all wedding dresses");

        return weddingDressRepository.findAll();
    }

    @Override
    public WeddingDress getDressById(Long id) {

        logger.info("Fetching wedding dress with id: {}", id);

        return weddingDressRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Dress not found with id: {}", id);

                    return new RuntimeException(
                            "Dress not found with id: " + id
                    );
                });
    }

    @Override
    public WeddingDress updateDress(
            Long id,
            WeddingDress weddingDress) {

        logger.info("Updating wedding dress with id: {}", id);

        WeddingDress existingDress =
                weddingDressRepository.findById(id)
                        .orElseThrow(() -> {

                            logger.error(
                                    "Dress not found for update with id: {}",
                                    id
                            );

                            return new RuntimeException(
                                    "Dress not found with id: " + id
                            );
                        });

        existingDress.setName(weddingDress.getName());
        existingDress.setDescription(weddingDress.getDescription());
        existingDress.setPrice(weddingDress.getPrice());
        existingDress.setSize(weddingDress.getSize());
        existingDress.setColor(weddingDress.getColor());
        existingDress.setImage(weddingDress.getImage());
        existingDress.setAvailable(weddingDress.getAvailable());

        WeddingDress updatedDress =
                weddingDressRepository.save(existingDress);

        logger.info(
                "Wedding dress updated successfully with id: {}",
                id
        );

        return updatedDress;
    }

    @Override
    public void deleteDress(Long id) {

        logger.info("Deleting wedding dress with id: {}", id);

        if (!weddingDressRepository.existsById(id)) {

            logger.error(
                    "Dress not found for deletion with id: {}",
                    id
            );

            throw new RuntimeException(
                    "Dress not found with id: " + id
            );
        }

        weddingDressRepository.deleteById(id);

        logger.info(
                "Wedding dress deleted successfully with id: {}",
                id
        );
    }

    @Override
    public WeddingDress patchDress(
            Long id,
            WeddingDress weddingDress) {

        logger.info(
                "Partially updating wedding dress with id: {}",
                id
        );

        WeddingDress existingDress =
                weddingDressRepository.findById(id)
                        .orElseThrow(() -> {

                            logger.error(
                                    "Dress not found for patch with id: {}",
                                    id
                            );

                            return new RuntimeException(
                                    "Dress not found with id: " + id
                            );
                        });

        if (weddingDress.getName() != null) {
            existingDress.setName(weddingDress.getName());
        }

        if (weddingDress.getDescription() != null) {
            existingDress.setDescription(
                    weddingDress.getDescription()
            );
        }

        if (weddingDress.getPrice() != null) {
            existingDress.setPrice(weddingDress.getPrice());
        }

        if (weddingDress.getSize() != null) {
            existingDress.setSize(weddingDress.getSize());
        }

        if (weddingDress.getColor() != null) {
            existingDress.setColor(weddingDress.getColor());
        }

        if (weddingDress.getImage() != null) {
            existingDress.setImage(weddingDress.getImage());
        }

        if (weddingDress.getAvailable() != null) {
            existingDress.setAvailable(
                    weddingDress.getAvailable()
            );
        }

        WeddingDress patchedDress =
                weddingDressRepository.save(existingDress);

        logger.info(
                "Wedding dress patched successfully with id: {}",
                id
        );

        return patchedDress;
    }
}