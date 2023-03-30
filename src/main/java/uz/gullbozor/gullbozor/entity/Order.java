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
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private String orderHolderName;
    private String orderCreateId;
    private String orderHolderPhone;

    private Double totalPrice;

    private Double cost;

    private Double partOfCompany;

    private Double discount;

    private Double lastTotalPrice;

    private Byte status;

    @Column(name = "create_at", updatable = false)
    private Long createAt;

    @Column(name = "finish_at", updatable = false)
    private Long finishAt;



}
