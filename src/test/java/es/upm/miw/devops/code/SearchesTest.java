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
    void testFindFirstDecimalFractionByUserName() {
        // Ana → decimal first fraction is -1/5 = -0.2
        assertThat(new Searches().findFirstDecimalFractionByUserName("Ana"))
                .isEqualTo(-0.2);

        // Oscar (id=1) → No any decimal fraction → null
        assertThat(new Searches().findFirstDecimalFractionByUserName("Oscar"))
                .isNull();
    }
}
