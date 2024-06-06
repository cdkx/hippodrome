import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HippodromeTest {

    @Test
    void shouldThrowExceptionIfParamConstructorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void ifParamConstructorIsNullExceptionShouldContainMessage() {
        String expectedMessage = "Horses cannot be null.";
        try {
            new Hippodrome(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionIfParamConstructorIsEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    void ifParamConstructorIsEmptyListExceptionShouldContainMessage() {
        String expectedMessage = "Horses cannot be empty.";
        try {
            new Hippodrome(Collections.emptyList());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void getHorsesShouldReturnSameListHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Horse horse = new Horse("horse" + i, i);
            horses.add(horse);
        }

        List<Horse> expected = new ArrayList<>(List.copyOf(horses));
        List<Horse> actual = new Hippodrome(horses).getHorses();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvokedOnAllHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Horse mockHorse = mock(Horse.class);
            horses.add(mockHorse);
        }
        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }
    }

    @Test
    void shouldReturnHorseWithMaxDistance() {
        Horse horse1 = new Horse("1", 2, 3);
        Horse horse2 = new Horse("2", 4, 1);
        Horse horse3 = new Horse("3", 6, 33);
        Horse horse4 = new Horse("4", 8, 45);
        Horse horse5 = new Horse("5", 10, 99);
        List<Horse> horses = new ArrayList<>(List.of(horse2, horse5, horse3, horse4, horse1));

        Horse actual = new Hippodrome(horses).getWinner();

        assertEquals(horse5, actual);
    }
}
