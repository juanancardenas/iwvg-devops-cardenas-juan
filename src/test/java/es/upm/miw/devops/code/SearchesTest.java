package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import java.util.List;
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
        // 1 | Oscar Fernandez | 0/1 1/1 2/1      -> (YES) 0/1
        // 2 | Ana Blanco      | 2/1 -1/5 2/4 4/3 -> (YES) -1/5 2/4
        // 3 | Óscar López     | 1/5 3/-6 1/2 4/4 -> (YES) 1/5 1/2
        // 4 | Paula Torres    | 2/2 4/4          -> No proper fractions
        // 5 | Antonio Blanco  | 0/1 0/-2 0/3     -> (YES) 0/1 and 0/3
        // 6 | Paula Torres    | 0/0 1/0 1/1      -> No proper fractions
        assertThat(new Searches().findUserIdByAllProperFraction().toList())
                .containsExactly("1", "2", "3", "5"); // Ordering attending to UsersDatabase
    }

    /* Issue #12 - Function 4 */
    @Test
    void testFindFirstDecimalFractionByUserName_withAna() {
        // 2 | Ana Blanco      | 2/1 -1/5 2/4 4/3 -> First Decimal Fraction -1/5 (OK)
        assertThat(new Searches().findFirstDecimalFractionByUserName("Ana"))
                .isEqualTo(-0.2);
    }

    @Test
    void testFindFirstDecimalFractionByUserName_withOscar() {
        // 3 | Óscar López     | 1/5 3/-6 1/2 4/4 -> First Decimal Fraction 1/5 (OK)
        assertThat(new Searches().findFirstDecimalFractionByUserName("Oscar"))
                .isEqualTo(0.2);
    }

    @Test
    void testFindFirstDecimalFractionByUserName_withPaula() {
        // 4 | Paula Torres    | 2/2 4/4          -> No any decimal fraction (OK)
        // 6 | Paula Torres    | 0/0 1/0 1/1      -> No any decimal fraction (OK)
        assertThat(new Searches().findFirstDecimalFractionByUserName("Paula"))
                .isNull();
    }

    /* Issue #13 - Function 6 */
    @Test
    void testFindDecimalImproperFractionByUserName_withAna() {
        // Ana has fractions: 2/1 (improper whole number), -1/5 (proper), 2/4 (proper), 4/3 (improper decimal)
        // Only 4/3 = 1.333... should appear.
        assertThat(new Searches().findDecimalImproperFractionByUserName("Ana").toList())
                .containsExactly(4.0 / 3.0);
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withOscarFernandez() {
        // Oscar Fernandez has fractions: 0/1, 1/1, 2/1
        // No improper decimals -> empty list
        assertThat(new Searches().findDecimalImproperFractionByUserName("Oscar").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withOscarLopez() {
        // Oscar López has: 1/5 (proper), 3/-6 (proper), 1/2 (proper), 4/4 (improper whole)
        // No improper decimals -> empty list
        assertThat(new Searches().findDecimalImproperFractionByUserName("Oscar").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withPaula() {
        // Paula has in one case: 2/2, 4/4 (all improper integers) => empty
        // In the other: 0/0 (invalid), 1/0 (invalid), 1/1 (integer)
        // => also empty
        assertThat(new Searches().findDecimalImproperFractionByUserName("Paula").toList())
                .isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_withAntonio() {
        // Antonio has: 0/1, 0/-2, 0/3 -> all 0
        // No improper decimals
        assertThat(new Searches().findDecimalImproperFractionByUserName("Antonio").toList())
                .isEmpty();
    }

    /* Issue #14 - Function B */

    // 1 | Oscar Fernandez | 0/1 1/1 2/1      -> Doesn't contain negative sign fraction
    // 2 | Ana Blanco      | 2/1 -1/5 2/4 4/3 -> (YES) -1/5
    // 3 | Óscar López     | 1/5 3/-6 1/2 4/4 -> (YES) 3/-6
    // 4 | Paula Torres    | 2/2 4/4          -> Doesn't contain negative sign fraction
    // 5 | Antonio Blanco  | 0/1 0/-2 0/3     -> (YES) 0/-2
    // 6 | Paula Torres    | 0/0 1/0 1/1      -> Doesn't contain negative sign fraction
    @Test
    void testFindUserFamilyNameByAllNegativeSignFractionDistinct_expectedBlancoLopez() {
        // According to UsersDatabase:
        // - Ana Blanco has the fraction -1/5
        // - Oscar López has the fraction 3/-6
        assertThat(new Searches().findUserFamilyNameByAllNegativeSignFractionDistinct().toList())
                .containsExactly("Blanco", "López");
    }

    @Test
    void testFindUserFamilyNameByAllNegativeSignFractionDistinct_noFernandezNoTorres() {
        // Either Oscar Fernandez (all fractions positives) or
        // Paula Torres (none a negative fraction) must appear
        assertThat(new Searches().findUserFamilyNameByAllNegativeSignFractionDistinct().toList())
                .doesNotContain("Fernandez", "Torres");
    }

    @Test
    void testFindUserFamilyNameByAllNegativeSignFractionDistinct_distinctness() {
        List<String> result = new Searches().findUserFamilyNameByAllNegativeSignFractionDistinct().toList();

        // Checking there are no duplicates: -1/5 | 3/-6 | 0/-2
        long distinctCount = result.stream().distinct().count();
        assertThat(result).hasSize((int) distinctCount);
    }
}