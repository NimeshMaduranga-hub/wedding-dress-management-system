package lk.ijse.wedding_dress.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wedding_dresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WeddingDress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    private String image;

    @Column(nullable = false)
    private boolean available;
}
