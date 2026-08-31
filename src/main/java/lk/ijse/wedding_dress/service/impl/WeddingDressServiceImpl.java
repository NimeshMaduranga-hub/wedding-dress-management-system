package lk.ijse.wedding_dress.service.impl;


import lk.ijse.wedding_dress.entity.WeddingDress;
import lk.ijse.wedding_dress.repository.WeddingDressRepository;
import lk.ijse.wedding_dress.service.WeddingDressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeddingDressServiceImpl implements WeddingDressService {

    private final WeddingDressRepository weddingDressRepository;

    @Override
    public WeddingDress saveDress(WeddingDress weddingDress) {
        return weddingDressRepository.save(weddingDress);
    }

    @Override
    public List<WeddingDress> getAllDresses() {
        return weddingDressRepository.findAll();
    }

    @Override
    public WeddingDress getDressById(Long id) {

        return weddingDressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Dress not found with id: " + id
                        )
                );
    }

    @Override
    public WeddingDress updateDress(
            Long id,
            WeddingDress weddingDress) {

        WeddingDress existingDress =
                weddingDressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Dress not found with id: " + id
                                )
                        );

        existingDress.setName(weddingDress.getName());
        existingDress.setDescription(weddingDress.getDescription());
        existingDress.setPrice(weddingDress.getPrice());
        existingDress.setSize(weddingDress.getSize());
        existingDress.setColor(weddingDress.getColor());
        existingDress.setImage(weddingDress.getImage());
        existingDress.setAvailable(weddingDress.isAvailable());

        return weddingDressRepository.save(existingDress);
    }

    @Override
    public void deleteDress(Long id) {

        if (!weddingDressRepository.existsById(id)) {
            throw new RuntimeException(
                    "Dress not found with id: " + id
            );
        }

        weddingDressRepository.deleteById(id);
    }
}