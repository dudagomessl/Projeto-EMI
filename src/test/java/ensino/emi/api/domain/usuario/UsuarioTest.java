package ensino.emi.api.domain.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioTest {

    @Test
    void novoUsuarioComecaNoNivelUmSemXp() {
        Usuario usuario = new Usuario();
        assertEquals(1, usuario.getNivelAtual());
        assertEquals(0, usuario.getXpNoNivelAtual());
    }

    @Test
    void cincoMilEQuinhentosXpEhNivelSeisComQuinhentosNaBarra() {
        Usuario usuario = new Usuario();
        usuario.adicionarXp(5500);
        assertEquals(6, usuario.getNivelAtual());
        assertEquals(500, usuario.getXpNoNivelAtual());
    }

    @Test
    void xpAcumulaEPassaDeNivelAosMilPontos() {
        Usuario usuario = new Usuario();
        usuario.adicionarXp(750);
        usuario.adicionarXp(250);
        assertEquals(2, usuario.getNivelAtual());
        assertEquals(0, usuario.getXpNoNivelAtual());
    }

    @Test
    void naoAceitaXpNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario().adicionarXp(-1));
    }
}
