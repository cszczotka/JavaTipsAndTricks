import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FibonacciTest {
//idx - 0, 1, 2, 3, 4, 5, 6,  7,  8,  9, 10
//val - 0, 1, 1, 2 ,3 ,5 ,8, 13, 21, 34, 55

    /**
     * O(n-2)
     */
    public static int getFibElementByIdx(int idx) {
        int value = 0;
        int prevVal = 1;
        int prevPrevVal = 0;
        if(idx == 0) {
            return 0;
        } else if (idx == 1) {
            return 1;
        } else {
            for (int i = 2; i <= idx; i++) {
                if (value > 0) {
                    prevPrevVal = prevVal;
                    prevVal = value;
                }
                value = prevVal + prevPrevVal;
            }
            return value;
        }
    }

    /**
     * O(n^2)
     */
    public static int getFibElementByIdxRec(int idx) {
        if(idx == 0) {
            return 0;
        } else if (idx == 1) {
            return 1;
        } else {
            return getFibElementByIdx(idx-2) + getFibElementByIdx(idx-1);

        }
    }

    @Test
    void getFibElementByIdxTest() {
        int val = getFibElementByIdx(2);
        Assertions.assertEquals(1, val);
        val = getFibElementByIdx(3);
        Assertions.assertEquals(2, val);
        val = getFibElementByIdx(4);
        Assertions.assertEquals(3, val);
        val = getFibElementByIdx(5);
        Assertions.assertEquals(5, val);
        val = getFibElementByIdx(6);
        Assertions.assertEquals(8, val);
        val = getFibElementByIdx(7);
        Assertions.assertEquals(13, val);
        val = getFibElementByIdx(8);
        Assertions.assertEquals(21, val);
        val = getFibElementByIdx(9);
        Assertions.assertEquals(34, val);
        val = getFibElementByIdx(10);
        Assertions.assertEquals(55, val);
    }

    @Test
    void getFibElementByIdxTestRec() {
        int val = getFibElementByIdxRec(2);
        Assertions.assertEquals(1, val);
        val = getFibElementByIdxRec(3);
        Assertions.assertEquals(2, val);
        val = getFibElementByIdxRec(4);
        Assertions.assertEquals(3, val);
        val = getFibElementByIdxRec(5);
        Assertions.assertEquals(5, val);
        val = getFibElementByIdxRec(6);
        Assertions.assertEquals(8, val);
        val = getFibElementByIdxRec(7);
        Assertions.assertEquals(13, val);
        val = getFibElementByIdxRec(8);
        Assertions.assertEquals(21, val);
        val = getFibElementByIdxRec(9);
        Assertions.assertEquals(34, val);
        val = getFibElementByIdxRec(10);
        Assertions.assertEquals(55, val);
    }
}
