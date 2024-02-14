package tuleuov.space.railway.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberCarriage;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
}
