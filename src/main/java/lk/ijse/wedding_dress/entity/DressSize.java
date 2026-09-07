package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dress_sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DressSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_name", nullable = false, unique = true, length = 20)
    private String sizeName;
}