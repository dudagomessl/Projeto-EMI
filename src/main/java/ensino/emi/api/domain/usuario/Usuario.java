package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.chat.SessaoChat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
public class Usuario {

    /** XP necessário para subir de nível. */
    public static final int XP_POR_NIVEL = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sessao_chat", unique = true)
    private SessaoChat sessaoChat;

    @Column(name = "nome_usuario", nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /** Hash BCrypt, nunca a senha em texto puro. */
    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false)
    private int experiencia = 0;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    /** Controle de concorrência: evita perder XP quando duas requisições chegam juntas. */
    @Version
    @Column(name = "versao", nullable = false)
    private Long versao;

    public void adicionarXp(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade de XP não pode ser negativa.");
        }
        this.experiencia += quantidade;
    }

    /** Nível 1 começa em 0 XP; 1000 XP leva ao nível 2, e assim por diante. */
    public int getNivelAtual() {
        return (experiencia / XP_POR_NIVEL) + 1;
    }

    /** XP acumulado dentro do nível atual (usado na barra de progresso). */
    public int getXpNoNivelAtual() {
        return experiencia % XP_POR_NIVEL;
    }
}
