public class Screen {
    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;

        // Observador 1: imprime cada dígito recebido
        Observer digitPrinter = new Observer() {
            @Override
            public void update(int digit) {
                System.out.println(digit);
            }
        };
        model.registerObserver(digitPrinter);

        // Observador 2: verifica se completou a discagem (12 dígitos)
        Observer dialChecker = new Observer() {
            @Override
            public void update(int digit) {
                if (model.getDigits().size() == 12) {
                    System.out.print("Agora discando ");
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