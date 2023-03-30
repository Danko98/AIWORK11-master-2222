package uz.gullbozor.gullbozor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachment_content")
public class AttachContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private byte[] bytes;

    @OneToOne
    private Attach attach;


 }
