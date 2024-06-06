import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    @Test
    void shouldThrowExceptionIfFirstParamConstructorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1d, 1d));
    }

    @Test
    void ifFirstParamConstructorIsNullExceptionShouldContainMessage() {
        String expectedMessage = "Name cannot be null.";
        try {
            new Horse(null, 1d, 1d);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\t", "\f", "\r", "\u000B", "\u001C", "\u001D", "\u001E", "\u001F"})
    void shouldThrowIllegalArgumentExceptionIfFirstParamIsBlanc(String paramString) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(paramString, 1d, 1d));

    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\t", "\f", "\r", "\u000B", "\u001C", "\u001D", "\u001E", "\u001F"})
    void ifFirstParamConstructorIsBlancThenExceptionShouldContainMessage(String paramString) {
        String expectedMessage = "Name cannot be blank.";
        try {
            new Horse(paramString, 1d, 1d);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionIfSecondParamConstructorIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("string", -5d, 1d));
    }

    @Test
    void ifSecondParamConstructorIsNegativeThenExceptionShouldContainMessage() {
        String expectedMessage = "Speed cannot be negative.";
        try {
            new Horse("string", -5d, 1d);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionIfThirdParamConstructorIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("string", 1d, -5d));
    }

    @Test
    void ifThirdParamConstructorIsNegativeThenExceptionShouldContainMessage() {
        String expectedMessage = "Distance cannot be negative.";
        try {
            new Horse("string", 1d, -5d);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void shouldReturnCorrectFirstParamString() {
        String expected = "Horse";
        String actual = new Horse("Horse", 1d, 1d).getName();
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnCorrectSecondParamDouble() {
        double expected = 1d;
        double actual = new Horse("Horse", 1d, 2d).getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnCorrectThirdParamDouble() {
        double expected = 2d;
        double actual = new Horse("Horse", 1d, 2d).getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroDoubleIfConstructorHasTwoParam() {
        double expected = 0d;
        double actual = new Horse("Horse", 1d).getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvokeGetRandomDoubleOnlyOnce() {
        try (MockedStatic<Horse> mockStatic = mockStatic(Horse.class)) {
            new Horse("Horse", 10d, 15d).move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2d, 0.9d), times(1));
        }
    }

    @ParameterizedTest(name = "method getDistance() should return correct value:\n" +
                              " distance + speed * getRandomDouble(0.2, 0.9).")
    @CsvSource({
            "1, 2, 2.2, 0.2d", "10, 20, 22, 0.2d", "50, 60, 70, 0.2d",
            "1, 2, 2.3, 0.3d", "10, 20, 23, 0.3d", "50, 200, 215, 0.3d",
            "1, 2, 2.4, 0.4d", "10, 20, 24, 0.4d", "50, 1, 21, 0.4d",
            "1, 2, 2.5, 0.5d", "10, 50, 55, 0.5d", "70, 26654, 26689, 0.5d",
            "1, 2, 2.6, 0.6d", "40, 50, 74, 0.6d", "351654, 51654, 262646.4, 0.6d",
            "1, 2, 2.7, 0.7d", "40, 50, 78, 0.7d", "1.126546, 2.4351402, 3.2237224, 0.7d",
            "1, 2, 2.8, 0.8d", "40, 50, 82, 0.8d", "0.254654, 0.355646, 0.573747983456, 0.856464d",
            "1, 2, 2.9, 0.9d", "40, 50, 86, 0.9d", "0.001, 0.001, 0.001009, 0.009d"})
    void shouldReturnCorrectValue(double speed, double distance, double expectedDistance, double randomDouble) {
        try (MockedStatic<Horse> mockStatic = mockStatic(Horse.class)) {
            mockStatic.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(randomDouble);
            Horse horse = new Horse("Horse", speed, distance);
            horse.move();

            double actual = horse.getDistance();

            assertEquals(expectedDistance, actual);
        }
    }
}
