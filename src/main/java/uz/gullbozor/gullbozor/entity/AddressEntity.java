package uz.gullbozor.gullbozor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_and_home")
    private String streetAndHome;

    @ManyToOne
    private RegionEntity regionEntity;

    @ManyToOne
    private CityEntity city;


}
