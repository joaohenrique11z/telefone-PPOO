/**
 * Interface Subject do padrão Observer.
 * O Subject é quem detém os dados e avisa os observadores
 * registrados quando há uma mudança de estado.
 */
public interface Subject {

    /**
     * Adiciona um observador à lista de notificação.
     */
    void registerObserver(Observer o);

    /**
     * Remove um observador da lista de notificação.
     */
    void removeObserver(Observer o);

    /**
     * Percorre todos os observadores registrados chamando update().
     * @param digit o dígito que gerou a notificação
     */
    void notifyObservers(int digit);
}
