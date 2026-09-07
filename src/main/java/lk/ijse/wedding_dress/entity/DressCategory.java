package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dress_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DressCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;
}