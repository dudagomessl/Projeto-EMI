package ensino.emi.api.infra.gemini;

/** Uma fala da conversa enviada ao Gemini. */
public record Turno(Papel papel, String texto) {

    public enum Papel {
        USUARIO,
        MODELO
    }
}
