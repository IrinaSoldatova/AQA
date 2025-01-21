import org.example.lesson_14.Factorial;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FactorialTest {

    @DataProvider(name = "factorialData")
    public static Object[][] factorialData() {
        return new Object[][] {
                {0, 1L},
                {1, 1L},
                {2, 2L},
                {3, 6L},
                {4, 24L},
                {5, 120L},
                {10, 3628800L},
                {20, 2432902008176640000L}
        };
    }

    @Test(dataProvider = "factorialData")
    public void testCalculateFactorialWithValidInput(int n, long expected) {
        long result = Factorial.calculateFactorial(n);
        assertEquals(result, expected, "Факториал от " + n + " должен быть равен " + expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Факториал не определен для отрицательных чисел.")
    public void testCalculateFactorialWithNegativeInput() {
        Factorial.calculateFactorial(-5);
    }


    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Факториал слишком большого числа.")
    public void testCalculateFactorialWithTooLargeInput() {
        Factorial.calculateFactorial(21);
    }
}