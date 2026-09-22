/**
 * Prints things out to the screen, when needed.
 * A Screen não é chamada diretamente por ninguém — ela se registra
 * como observadora do PhoneModel e reage automaticamente às mudanças.
 *
 * Usa o padrão Observer com dois observadores:
 *   1) digitPrinter: imprime cada dígito assim que chega
 *   2) dialChecker: verifica se o número está completo (12 dígitos) e simula a discagem
 */
public class Screen {
    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;

        // --- Observador 1: imprime cada dígito recebido ---
        // Cria uma classe anônima que implementa Observer.
        // Toda vez que um dígito é adicionado no PhoneModel,
        // o método update() será chamado e imprime o dígito na tela.
        // A IA sugeriu usar classe anônima para manter cada observer isolado.
        Observer digitPrinter = new Observer() {
            @Override
            public void update(int digit) {
                System.out.println(digit);
            }
        };
        model.registerObserver(digitPrinter);

        // --- Observador 2: verifica se completou a discagem ---
        // Quando o número de dígitos chega a 12, imprime a mensagem
        // "Agora discando ..." com todos os dígitos concatenados.
        // Ele consulta model.getDigits() para saber a quantidade atual.
        Observer dialChecker = new Observer() {
            @Override
            public void update(int digit) {
                // Verifica se o número está completo (12 dígitos)
                if (model.getDigits().size() == 12) {
                    System.out.print("Agora discando ");
                    // Percorre todos os dígitos armazenados e imprime na mesma linha
                    for (Integer d : model.getDigits()) {
                        System.out.print(d);
                    }
                    System.out.println("...");
                }
            }
        };
        model.registerObserver(dialChecker);
    }
}
