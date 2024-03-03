package tuleuov.space.railway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber;

    @ManyToOne
    @JoinColumn(name = "carriage_id")
    private Carriage carriage;
    private int price;
    // Дополнительные поля, описывающие место в вагоне, например, тип места, стоимость и т.д.
}
