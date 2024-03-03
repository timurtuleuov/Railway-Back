package tuleuov.space.railway.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String trainName;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carriage> carriages = new ArrayList<>();
}
