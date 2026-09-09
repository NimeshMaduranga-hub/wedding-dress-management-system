package lk.ijse.wedding_dress.repository;

import lk.ijse.wedding_dress.entity.DressColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DressColorRepository extends JpaRepository<DressColor, Long> {
    Optional<DressColor> findByColorName(String colorName);
}