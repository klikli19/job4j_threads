package pool;

import org.junit.jupiter.api.Test;
import ru.job4j.pool.ParallelSearch;

import static org.junit.jupiter.api.Assertions.*;

public class ParallelSearchTest {
    @Test
    void testNullValue() {
        Integer[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> ParallelSearch.sort(null, array));
    }

    @Test
    void testNullArray() {
        assertThrows(IllegalArgumentException.class, () -> ParallelSearch.sort(1, null));
    }

    @Test
    void testValueNotFound() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertEquals(-1, ParallelSearch.sort(6, array));
    }

    @Test
    void testValueFoundAtBeginning() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertEquals(0, ParallelSearch.sort(1, array));
    }

    @Test
    void testValueFoundInMiddle() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertEquals(2, ParallelSearch.sort(3, array));
    }

    @Test
    void testValueFoundAtEnd() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertEquals(4, ParallelSearch.sort(5, array));
    }

    @Test
    void testLargeArray() {
        Integer[] array = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            array[i] = i;
        }
        assertEquals(5000, ParallelSearch.sort(5000, array));
    }

    @Test
    void testDuplicateValues() {
        Integer[] array = {1, 2, 3, 3, 4, 5};
        assertEquals(2, ParallelSearch.sort(3, array));
    }


    @Test
    void testStringArray() {
        String[] array = {"bmw", "audi", "volvo"};
        assertEquals(1, ParallelSearch.sort("audi", array));
    }

    @Test
    void testTypeMismatch() {
        Integer[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> ParallelSearch.sort("a", array));
    }

    @Test
    void testSmallArraySequential(){
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, ParallelSearch.sort(5, array));
    }
}
