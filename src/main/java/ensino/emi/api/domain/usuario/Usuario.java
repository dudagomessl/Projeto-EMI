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
@AllArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sessao_chat", referencedColumnName = "id_chat", unique = true)
    private SessaoChat sessaoChat;

    @Column(name = "nome_usuario", nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    private Integer experiencia = 0;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    public void adicionarXp(int quantidade) {
        if (this.experiencia == null) {
            this.experiencia = 0;
        }
        this.experiencia += quantidade;
    }

    public int getNivelAtual() {
        if (this.experiencia == null || this.experiencia < 0) return 1;
        return (this.experiencia / 1000) + 1;
    }

    public int getXpNoNivelAtual() {
        if (this.experiencia == null) return 0;
        return this.experiencia % 1000;
    }
}