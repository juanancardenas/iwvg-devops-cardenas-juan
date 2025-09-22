package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    public void testConstructorWithParameters() {
        Fraction fraction = new Fraction(3, 4);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    public void testDefaultConstructor() {
        Fraction fraction = new Fraction();
        assertEquals(1, fraction.getNumerator());
        assertEquals(1, fraction.getDenominator());
    }

    @Test
    public void testSettersAndGetters() {
        Fraction fraction = new Fraction();
        fraction.setNumerator(5);
        fraction.setDenominator(8);

        assertEquals(5, fraction.getNumerator());
        assertEquals(8, fraction.getDenominator());
    }

    @Test
    public void testDecimal() {
        Fraction fraction = new Fraction(1, 2);
        assertEquals(0.5, fraction.decimal(), 0.0001);

        Fraction fraction2 = new Fraction(3, 4);
        assertEquals(0.75, fraction2.decimal(), 0.0001);
    }

    @Test
    public void testToString() {
        Fraction fraction = new Fraction(7, 9);
        String expected = "Fraction{numerator=7, denominator=9}";
        assertEquals(expected, fraction.toString());
    }

    // ---------- Casos límite ----------

    @Test
    public void testZeroNumerator() {
        Fraction fraction = new Fraction(0, 5);
        assertEquals(0.0, fraction.decimal(), 0.0001);
    }

    @Test
    public void testZeroDenominatorThrowsExceptionInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Fraction(5, 0));
    }

    @Test
    public void testZeroDenominatorThrowsExceptionInSetter() {
        Fraction fraction = new Fraction(1, 2);
        assertThrows(IllegalArgumentException.class, () -> fraction.setDenominator(0));
    }

    @Test
    public void testNegativeNumerator() {
        Fraction fraction = new Fraction(-3, 4);
        assertEquals(-0.75, fraction.decimal(), 0.0001);
    }

    @Test
    public void testNegativeDenominator() {
        Fraction fraction = new Fraction(3, -4);
        assertEquals(-0.75, fraction.decimal(), 0.0001);
    }

    @Test
    public void testBothNegative() {
        Fraction fraction = new Fraction(-3, -4);
        assertEquals(0.75, fraction.decimal(), 0.0001);
    }
}

