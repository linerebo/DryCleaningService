package Domain.LaundryItemTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarpetTest {

    @Test
    void price() {
        Carpet test1 = new Carpet("red", false, 0);
        Carpet.squareMeterPrice = 20;
        int result = test1.price();
        assertEquals(0, result);
    }
}