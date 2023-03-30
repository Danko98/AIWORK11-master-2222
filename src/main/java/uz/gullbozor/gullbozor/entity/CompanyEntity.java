package uz.gullbozor.gullbozor.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company_entity")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Long companyOwnerId;
    private String companyName;

    private String location;
    private String tgLink;
    private String phoneNumber;
    private Integer regionId;
    private Integer cityId;
    private Integer dealerId;
    private Double partOfWindow;
    private Double partOfDoor;
    private Double value;
    private Long tgClick;
    private Long mapClick;
    private Long phoneClick;
    private Long view;
    private Boolean installIsFree;
    private Byte stars;


}
