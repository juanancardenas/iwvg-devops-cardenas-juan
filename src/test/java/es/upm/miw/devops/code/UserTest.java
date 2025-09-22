package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user.getFractions()); // lista inicializada
        assertTrue(user.getFractions().isEmpty());
    }

    @Test
    public void testConstructorWithParameters() {
        List<Fraction> fractions = new ArrayList<>();
        fractions.add(new Fraction(1, 2));

        User user = new User("u1", "Alice", "Smith", fractions);

        assertEquals("u1", user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("Smith", user.getFamilyName());
        assertEquals(1, user.getFractions().size());
        assertEquals(0.5, user.getFractions().get(0).decimal(), 0.0001);
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setName("Bob");
        user.setFamilyName("Johnson");

        assertEquals("Bob", user.getName());
        assertEquals("Johnson", user.getFamilyName());

        List<Fraction> fractions = new ArrayList<>();
        fractions.add(new Fraction(3, 4));
        user.setFractions(fractions);

        assertEquals(1, user.getFractions().size());
        assertEquals(0.75, user.getFractions().get(0).decimal(), 0.0001);
    }

    @Test
    public void testAddFraction() {
        User user = new User();
        assertTrue(user.getFractions().isEmpty());

        Fraction fraction = new Fraction(2, 3);
        user.addFraction(fraction);

        assertEquals(1, user.getFractions().size());
        assertSame(fraction, user.getFractions().get(0));
    }

    @Test
    public void testFullName() {
        User user = new User("u2", "Carlos", "Lopez", new ArrayList<>());
        assertEquals("Carlos Lopez", user.fullName());
    }

    @Test
    public void testInitials() {
        User user = new User("u3", "Daniel", "Martinez", new ArrayList<>());
        assertEquals("D.", user.initials());
    }

    @Test
    public void testToString() {
        List<Fraction> fractions = new ArrayList<>();
        fractions.add(new Fraction(1, 3));

        User user = new User("u4", "Eva", "Garcia", fractions);

        String result = user.toString();
        assertTrue(result.contains("id='u4'"));
        assertTrue(result.contains("name='Eva'"));
        assertTrue(result.contains("familyName='Garcia'"));
        assertTrue(result.contains("fractions=[Fraction{numerator=1, denominator=3}]"));
    }

    // ---------- Casos límite ----------

    @Test
    public void testEmptyNameAndFamilyName() {
        User user = new User("u5", "", "", new ArrayList<>());
        assertEquals(" ", user.fullName()); // nombre vacío + espacio + apellido vacío
        assertThrows(StringIndexOutOfBoundsException.class, user::initials);
    }

    @Test
    public void testNoFractionsInitially() {
        User user = new User("u6", "Luis", "Perez", new ArrayList<>());
        assertTrue(user.getFractions().isEmpty());
    }
}