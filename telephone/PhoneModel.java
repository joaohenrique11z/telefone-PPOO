import java.util.ArrayList;
import java.util.List;

/**
 * Store a phone number, digit-by-digit
 */
public class PhoneModel {
    private List<Integer> digits = new ArrayList<>();

    public void addDigit(int newDigit) {
        digits.add(newDigit);
    }

    public List<Integer> getDigits() {
        return digits;
    }
}
