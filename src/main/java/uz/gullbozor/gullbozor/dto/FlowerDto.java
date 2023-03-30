package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;
import uz.gullbozor.gullbozor.entity.Attach;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class FlowerDto {
    private double diameter;
    private double height;
    private boolean isWithPot;
    private boolean isWithFertilizer;
    private String description;
}
