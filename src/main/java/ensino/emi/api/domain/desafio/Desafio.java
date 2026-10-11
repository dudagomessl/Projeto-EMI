package ensino.emi.api.domain.desafio;

import ensino.emi.api.domain.aula.Aula;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "desafio")
@Entity(name = "Desafio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idDesafio")
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desafio")
    private Long idDesafio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aula", nullable = false, unique = true)
    private Aula aula;

    @Column(name = "nome_desafio", nullable = false, length = 100)
    private String nomeDesafio;

    @Column(name = "prompt_desafio", nullable = false, columnDefinition = "TEXT")
    private String promptDesafio;

    @Column(name = "pergunta_enunciado", nullable = false, columnDefinition = "TEXT")
    private String perguntaEnunciado;

    @Column(name = "resposta_correta", nullable = false, length = 255)
    private String respostaCorreta; // Gabarito para validação automática

    @Column(name = "recompensa_xp", nullable = false)
    private Integer recompensaXp;

    @Column(name = "nota_de_corte", nullable = false)
    private Integer notaDeCorte;


}