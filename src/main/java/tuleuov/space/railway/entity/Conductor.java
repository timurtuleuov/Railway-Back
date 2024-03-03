package tuleuov.space.railway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
}

