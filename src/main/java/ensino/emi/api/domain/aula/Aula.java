package ensino.emi.api.domain.aula;

import ensino.emi.api.domain.modulo.Modulo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "aula")
@Entity(name = "Aula")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idAula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long idAula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;

    @Column(name = "nome_aula", nullable = false, length = 100)
    private String nomeAula;

    @Column(name = "previa_do_conteudo", nullable = false, length = 500)
    private String previaDoConteudo;

    /** Texto completo da aula. */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    /** Link do vídeo (ex.: YouTube). Opcional. */
    @Column(name = "video_url", length = 255)
    private String videoUrl;

    /** Posição na linha do módulo (compartilhada com os desafios). */
    @Column(nullable = false)
    private Integer ordem;
}
