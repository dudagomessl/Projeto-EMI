package ensino.emi.api.domain.conteudo;

import ensino.emi.api.domain.aula.Aula;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "conteudo")
@Entity(name = "Conteudo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idConteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conteudo")
    private Long idConteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula aula;

    @Column(name = "nome_conteudo", nullable = false, length = 100) // <-- Alinhado com o banco
    private String tituloSecao; // ou nomeConteudo, dependendo de como preferir chamar no Java

    @Column(name = "texto", columnDefinition = "TEXT", nullable = false)
    private String corpoTexto;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "video", length = 255)
    private String video;
}