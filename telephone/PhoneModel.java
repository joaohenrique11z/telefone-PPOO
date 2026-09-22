import java.util.ArrayList;
import java.util.List;

/**
 * Store a phone number, digit-by-digit.
 * Agora implementa Subject do padrão Observer: é a fonte da verdade
 * que notifica os observadores quando um novo dígito é adicionado.
 */
public class PhoneModel implements Subject {
    private List<Integer> digits = new ArrayList<>();

    // Lista de observadores registrados que serão notificados a cada novo dígito
    private List<Observer> observers = new ArrayList<>();

    public void addDigit(int newDigit) {
        digits.add(newDigit);

        // Notifica todos os observadores imediatamente após adicionar o dígito.
        // Isso garante que a UI (Screen) seja atualizada em tempo real,
        // sem que o PhoneModel precise conhecer a Screen diretamente.
        notifyObservers(newDigit);
    }

    public List<Integer> getDigits() {
        return digits;
    }

    // --- Métodos do padrão Observer (implementação da interface Subject) ---

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(int digit) {
        // Percorre todos os observadores e chama update() em cada um,
        // entregando o dígito que acabou de ser adicionado
        for (Observer observer : observers) {
            observer.update(digit);
        }
    }
}
