package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchesTest {

    @Test
    void testFindUserFamilyNameByUserNameDistinct() {
        assertThat(new Searches().findUserFamilyNameByUserNameDistinct("Paula").toList())
                .containsExactly("Torres");
    }

    @Test
    void testFindUserFractionNumeratorByFamilyName() {
        assertThat(new Searches().findFractionNumeratorByUserFamilyName("Torres").toList())
                .containsExactly(2, 4, 0, 1, 1);
    }

    @Test
    void testFindFamilyNameByFractionDenominator() {
        assertThat(new Searches().findUserFamilyNameByFractionDenominator(2).toList())
                .containsExactly("López", "Torres");
    }

    /* Issue #11 - Function 5 */
    @Test
    void testFindUserIdByAllProperFraction() {
        assertThat(new Searches().findUserIdByAllProperFraction().toList())
                .containsExactly("1", "2", "3", "5"); // Ordering attending to UsersDatabase
    }

    /* Issue #12 - Function 4 */
    @Test
    void testFindFirstDecimalFractionByUserName_withAna() {
        assertThat(new Searches().findFirstDecimalFractionByUserName("Ana"))
                .isEqualTo(-0.2);
    }

    @Test
    void testFindFirstDecimalFractionByUserName_withOscar() {
        assertThat(new Searches().findFirstDecimalFractionByUserName("Oscar"))
                .isEqualTo(0.2);
    }

    @Test
    void testFindFirstDecimalFractionByUserName_withPaula() {
        assertThat(new Searches().findFirstDecimalFractionByUserName("Paula"))
                .isNull();
    }

    /* Issue #13 - Function 6 */
    @Test
    void testFindDecimalImproperFractionByUserName_withAna() {
        // Ana tiene fracciones: 2/1 (entera impropia), -1/5 (propia), 2/4 (propia), 4/3 (impropia decimal)
        // Solo debería salir 4/3 = 1.333...
        assertThat(new Searches().findDecimalImproperFractionByUserName("Ana").toList())
                .containsExactly(4.0 / 3.0);
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withOscarFernandez() {
        // Oscar Fernandez tiene fracciones: 0/1, 1/1, 2/1
        // Ninguna impropia decimal -> lista vacía
        assertThat(new Searches().findDecimalImproperFractionByUserName("Oscar").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withOscarLopez() {
        // Oscar López tiene: 1/5 (propia), 3/-6 (propia), 1/2 (propia), 4/4 (entera impropia)
        // Ninguna impropia decimal -> lista vacía
        assertThat(new Searches().findDecimalImproperFractionByUserName("Oscar").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withPaula() {
        // Paula tiene en un caso: 2/2, 4/4 (todas enteras impropias) => vacío
        // En el otro: 0/0 (inválida), 1/0 (inválida), 1/1 (entera)
        // => también vacío
        assertThat(new Searches().findDecimalImproperFractionByUserName("Paula").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withAntonio() {
        // Antonio tiene: 0/1, 0/-2, 0/3 -> todas 0
        // Ninguna impropia decimal
        assertThat(new Searches().findDecimalImproperFractionByUserName("Antonio").toList())
                .isEmpty();
    }
}