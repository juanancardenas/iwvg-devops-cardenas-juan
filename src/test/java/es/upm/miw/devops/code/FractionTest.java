package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FractionTest {

    @Test
    void testConstructorWithParameters() {
        Fraction fraction = new Fraction(3, 4);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    void testDefaultConstructor() {
        Fraction fraction = new Fraction();
        assertEquals(1, fraction.getNumerator());
        assertEquals(1, fraction.getDenominator());
    }

    @Test
    void testSettersAndGetters() {
        Fraction fraction = new Fraction();
        fraction.setNumerator(5);
        fraction.setDenominator(8);

        assertEquals(5, fraction.getNumerator());
        assertEquals(8, fraction.getDenominator());
    }

    @Test
    void testDecimal() {
        Fraction fraction = new Fraction(1, 2);
        assertEquals(0.5, fraction.decimal(), 0.0001);

        Fraction fraction2 = new Fraction(3, 4);
        assertEquals(0.75, fraction2.decimal(), 0.0001);
    }

    @Test
    void testToString() {
        Fraction fraction = new Fraction(7, 9);
        String expected = "Fraction{numerator=7, denominator=9}";
        assertEquals(expected, fraction.toString());
    }

    // ---------- Casos límite ----------

    @Test
    void testZeroNumerator() {
        Fraction fraction = new Fraction(0, 5);
        assertEquals(0.0, fraction.decimal(), 0.0001);
    }

    @Test
    void testZeroDenominatorThrowsExceptionInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Fraction(5, 0));
    }

    @Test
    void testZeroDenominatorThrowsExceptionInSetter() {
        Fraction fraction = new Fraction(1, 2);
        assertThrows(IllegalArgumentException.class, () -> fraction.setDenominator(0));
    }

    @Test
    void testNegativeNumerator() {
        Fraction fraction = new Fraction(-3, 4);
        assertEquals(-0.75, fraction.decimal(), 0.0001);
    }

    @Test
    void testNegativeDenominator() {
        Fraction fraction = new Fraction(3, -4);
        assertEquals(-0.75, fraction.decimal(), 0.0001);
    }

    @Test
    void testBothNegative() {
        Fraction fraction = new Fraction(-3, -4);
        assertEquals(0.75, fraction.decimal(), 0.0001);
    }

    // ---------- Nuevos métodos ----------

    @Test
    void testIsProper() {
        assertTrue(new Fraction(1, 2).isProper());
        assertFalse(new Fraction(5, 3).isProper());
    }

    @Test
    void testIsImproper() {
        assertTrue(new Fraction(5, 3).isImproper());
        assertFalse(new Fraction(1, 2).isImproper());
    }

    @Test
    void testIsEquivalent() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 5);

        assertTrue(f1.isEquivalent(f2));
        assertFalse(f1.isEquivalent(f3));
    }

    @Test
    void testAdd() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction result = f1.add(f2); // 1/2 + 1/3 = 5/6

        assertEquals(5, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    @Test
    void testMultiply() {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(3, 5);
        Fraction result = f1.multiply(f2); // 2/3 * 3/5 = 6/15

        assertEquals(6, result.getNumerator());
        assertEquals(15, result.getDenominator());
    }

    @Test
    void testDivide() {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(3, 4);
        Fraction result = f1.divide(f2); // (2/3) / (3/4) = 8/9

        assertEquals(8, result.getNumerator());
        assertEquals(9, result.getDenominator());
    }

    @Test
    void testDivideByZeroFractionThrowsException() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(0, 5); // 0/5

        assertThrows(ArithmeticException.class, () -> f1.divide(f2));
    }
}
