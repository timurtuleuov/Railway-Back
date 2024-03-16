package tuleuov.space.railway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Carriage> carriages = new ArrayList<>();

}
