package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.insignia.Insignia;
import ensino.emi.api.domain.modulo.Modulo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "missao")
@Entity(name = "Missao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idMissao")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missao")
    private Long idMissao;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoMissao tipo;

    /** Quantidade necessária para cumprir a missão (ex.: 5 aulas). */
    @Column(nullable = false)
    private Integer meta;

    /** Opcional: restringe a contagem a um módulo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo")
    private Modulo modulo;

    @Column(name = "recompensa_xp", nullable = false)
    private Integer recompensaXp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insignia_recompensa")
    private Insignia insigniaRecompensa;
}
