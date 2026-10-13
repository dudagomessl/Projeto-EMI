package ensino.emi.api.domain.modulo;

import ensino.emi.api.domain.trilha.Trilha;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "modulo")
@Entity(name = "Modulo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idModulo")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Long idModulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_trilha", nullable = false)
    private Trilha trilha;

    @Column(name = "nome_modulo", nullable = false, length = 100)
    private String nomeModulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
}
