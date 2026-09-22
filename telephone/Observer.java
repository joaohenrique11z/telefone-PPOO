
/**
 * Prints things out to the screen, when needed.
 * A Screen não é chamada diretamente por ninguém — ela se registra
 * como observadora do PhoneModel e reage automaticamente às mudanças.
 */
public class Screen {

    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;

        // --- Observador 1: imprime cada dígito recebido ---
        // Cria uma classe anônima que implementa Observer.
        // Toda vez que um dígito é adicionado no PhoneModel,
        // o método update() será chamado e imprime o dígito na tela.
        Observer digitPrinter = new Observer() {
            @Override
            public void update(int digit) {
                System.out.println(digit);
            }
        };
        model.registerObserver(digitPrinter);
    }
}
