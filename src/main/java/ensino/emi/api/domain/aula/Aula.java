package ensino.emi.api.domain.aula;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "aula")
@Entity(name = "Aula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idAula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long idAula;

    @Column(name = "nome_aula", nullable = false, length = 100)
    private String nomeAula;

    @Column(name = "status_de_conclusao", nullable = false)
    private Integer statusDeConclusao;

    @Column(name = "previa_do_conteudo", nullable = false, columnDefinition = "TEXT")
    private String previaDoConteudo;
}