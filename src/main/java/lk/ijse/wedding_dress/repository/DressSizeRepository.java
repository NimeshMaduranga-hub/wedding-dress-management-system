package lk.ijse.wedding_dress.repository;

import lk.ijse.wedding_dress.entity.DressSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DressSizeRepository extends JpaRepository<DressSize, Long> {
    Optional<DressSize> findBySizeName(String sizeName);
}