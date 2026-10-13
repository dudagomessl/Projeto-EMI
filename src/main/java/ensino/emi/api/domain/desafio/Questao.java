package ensino.emi.api.domain.desafio;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "questao")
@Entity(name = "Questao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idQuestao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_questao")
    private Long idQuestao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_desafio", nullable = false)
    private Desafio desafio;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "alternativa_a", nullable = false, length = 500)
    private String alternativaA;

    @Column(name = "alternativa_b", nullable = false, length = 500)
    private String alternativaB;

    @Column(name = "alternativa_c", nullable = false, length = 500)
    private String alternativaC;

    @Column(name = "alternativa_d", nullable = false, length = 500)
    private String alternativaD;

    /** Gabarito. Nunca é enviado ao front-end. */
    @Enumerated(EnumType.STRING)
    @Column(name = "resposta_correta", nullable = false, length = 1)
    private Alternativa respostaCorreta;
}
