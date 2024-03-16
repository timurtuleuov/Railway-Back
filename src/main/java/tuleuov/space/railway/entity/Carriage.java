package tuleuov.space.railway.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private String type;
    private boolean status = false;
    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();
}
