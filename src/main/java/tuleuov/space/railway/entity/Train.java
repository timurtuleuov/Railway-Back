package tuleuov.space.railway.entity;

import lombok.Data;
import jakarta.persistence.*;
@Data
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
