package lk.ijse.wedding_dress.repository;

import lk.ijse.wedding_dress.entity.DressCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DressCategoryRepository extends JpaRepository<DressCategory, Long> {
    Optional<DressCategory> findByName(String name);
}