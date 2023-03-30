package uz.gullbozor.gullbozor.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_body_pvh_win")
public class OrderBodyPvhWin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productHeadId;
    private Integer width;
    private Integer height;
    private Integer pvhL;
    private Integer pvhT;
    private Integer pvhZ;
    private Integer shtapik;
    private Integer shelf_length;
    private Integer categoryNum;
    private Byte shelf_size;
    private Byte color;
    private Byte glassTypeNum;
    private Long glass1;
    private Long glass2;
    private Long glass3;
    private Long glass4;
    private Long glass5;
    private Byte ruchkaTypeNum;
    private Byte petlya;
    private Byte saydinitel;
    private Byte qoraBurchak;
    private Byte sariqBurchak;
    private Byte ispandiletCount;
    private Integer poroshok;
    private Integer tikol;
    private Short samarez;
    private Double glassKvadrat;
    private Boolean glassTwoFloor;
    private Integer chit;
    private Integer rezinaPvh;

    private Integer widthMini;

    private Double shelfSum;
    private Double glassSum;
    private Double pvhLSum;
    private Double pvhTSum;
    private Double pvhZSum;
    private Double shtapikSum;

    private Double ruchkaSum;
    private Double petlyaSum;
    private Double saydinitelSum;
    private Double chitSum;
    private Double qoraBurchakSum;
    private Double sariqBurchakSum;
    private Double poroshokSum;
    private Double tikolSum;
    private Double rezinaPvhSum;
    private Double samarezSum;
    private Double ispandiletSum;
    private Double ikkitalikMexanizm;
    private Double ikkitalikMexanizmSum;

    private Double totalPrice;
    private Double companyPartPrice;
    private Double orderCost;
    private Double salePrice;






}
