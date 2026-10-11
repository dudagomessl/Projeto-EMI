package ensino.emi.api.domain.insignia;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "insignia")
@Entity(name = "Insignia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idInsignia")
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insignia")
    private Long idInsignia;

    @Column(name = "nome_insignia", nullable = false, length = 100)
    private String nomeInsignia;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "icone_url", length = 255)
    private String iconeUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "raridade", nullable = false)
    private RaridadeInsignia raridade;
}