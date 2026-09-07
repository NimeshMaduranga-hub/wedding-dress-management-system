package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dress_colors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DressColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color_name", nullable = false, unique = true, length = 50)
    private String colorName;
}