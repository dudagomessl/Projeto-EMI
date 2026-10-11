package ensino.emi.api.domain.trilha;

import ensino.emi.api.domain.modulo.Modulo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "trilha")
@Entity(name = "Trilha")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idTrilha")
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trilha")
    private Long idTrilha;

    @Column(name = "nome_trilha", nullable = false, length = 100)
    private String nomeTrilha;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modulo> modulos = new ArrayList<>();
}