package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dress_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DressImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dress_id", nullable = false)
    private WeddingDress dress;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "is_primary", nullable = false)
    private Boolean primary = false;
}