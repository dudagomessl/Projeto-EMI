package ensino.emi.api.domain.trilha;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "trilha")
@Entity(name = "Trilha")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idTrilha")
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trilha")
    private Long idTrilha;

    @Column(name = "nome_trilha", nullable = false, length = 100)
    private String nomeTrilha;
}
