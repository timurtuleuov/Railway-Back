package tuleuov.space.railway.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;
}
