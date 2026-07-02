package ensino.emi.api.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private double notaMedia;
    private int nivelDaConta;

    @Enumerated(EnumType.STRING)
    private MateriaFavorita materiaFavorita;

    public Usuario(DadosCadastroUsuario dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.nivelDaConta = dados.nivelDaConta();
        this.notaMedia = dados.notaMedia();
        this.materiaFavorita = dados.materiaFavorita();
    }

    //@Embedded private Endereco endereco
}
