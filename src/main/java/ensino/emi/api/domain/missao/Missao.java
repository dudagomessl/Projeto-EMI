package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.insignia.Insignia;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "missao")
@Entity(name = "Missao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idMissao")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missao")
    private Long idMissao;

    @Column(name = "nome_missao", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descricao_missao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "recompensa_xp", nullable = false)
    private Integer recompensaXp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insignia_recompensa", nullable = true)
    private Insignia insigniaRecompensa;
}