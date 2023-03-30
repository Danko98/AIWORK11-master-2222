package uz.gullbozor.gullbozor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "parent_category_id")
    private Long parentCategoryId;    // agar qiymat null bo'lsa bu Supper Ota categorya hisoblanadi

    @Column(name = "name",nullable = false)
    private String name;


}
