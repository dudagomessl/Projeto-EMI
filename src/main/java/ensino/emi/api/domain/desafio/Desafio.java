package ensino.emi.api.domain.desafio;

import ensino.emi.api.domain.modulo.Modulo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "desafio")
@Entity(name = "Desafio")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idDesafio")
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desafio")
    private Long idDesafio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;

    @Column(name = "nome_desafio", nullable = false, length = 100)
    private String nomeDesafio;

    /** Percentual mínimo de acertos (0 a 100) para ser aprovado. */
    @Column(name = "nota_de_corte", nullable = false)
    private Integer notaDeCorte;

    @Column(name = "recompensa_xp", nullable = false)
    private Integer recompensaXp;

    /** Posição na linha do módulo (compartilhada com as aulas). */
    @Column(nullable = false)
    private Integer ordem;
}
