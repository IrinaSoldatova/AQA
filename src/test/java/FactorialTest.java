import org.example.lesson_14.Factorial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class FactorialTest {

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "5, 120",
            "10, 3628800",
            "20, 2432902008176640000"
    })
    void testCalculateFactorialWithValidInput(int n, long expected) {
        long result = Factorial.calculateFactorial(n);
        assertEquals(expected, result, "Факториал от " + n + " должен быть равен " + expected);
    }


    @Test
    void testCalculateFactorialWithNegativeInput() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(-5));
        assertEquals("Факториал не определен для отрицательных чисел.",thrown.getMessage());
    }

    @Test
    void testCalculateFactorialWithTooLargeInput() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(21));
        assertEquals("Факториал слишком большого числа.",thrown.getMessage());
    }
}