package es.upm.miw.devops.code;

import java.util.Objects;
import java.util.stream.Stream;

public class Searches {

    public Stream<String> findUserFamilyNameByUserNameDistinct(String userName) {
        return new UsersDatabase().findAll()
                .filter(user -> userName.equals(user.getName()))
                .map(User::getFamilyName)
                .distinct();
    }

    public Stream<Integer> findFractionNumeratorByUserFamilyName(String userFamilyName) {
        return new UsersDatabase().findAll()
                .filter(user -> userFamilyName.equals(user.getFamilyName()))
                .flatMap(user -> user.getFractions().stream()
                        .filter(Objects::nonNull)
                )
                .map(Fraction::getNumerator);
    }

    public Stream<String> findUserFamilyNameByFractionDenominator(int fractionDenominator) {
        return new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .anyMatch(fraction -> fractionDenominator == fraction.getDenominator()))
                .map(User::getFamilyName);
    }

    /* Issue #11 - Function 5 */
    public Stream<String> findUserIdByAllProperFraction() {
        return new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .filter(f -> f.getDenominator() != 0) // Avoid dividing by 0
                        .anyMatch(Fraction::isProper)                 // Check if is a properFraction
                )
                .map(User::getId);
    }

    /* Issue #12 - Function 4 */
    public Double findFirstDecimalFractionByUserName(String name) {
        return new UsersDatabase().findAll()
                .filter(user -> name.equals(user.getName()))
                .flatMap(user -> user.getFractions().stream())
                .filter(Objects::nonNull)
                .filter(f -> f.getDenominator() != 0) // Avoid dividing by 0
                .filter(f -> Math.abs(f.getNumerator()) % Math.abs(f.getDenominator()) != 0) // Check if it is decimal
                .map(Fraction::decimal)
                .findFirst()
                .orElse(null);
    }

    /* Issue #13 - Function 6 */
    public Stream<Double> findDecimalImproperFractionByUserName(String name) {
        return new UsersDatabase().findAll()
                .filter(user -> name.equals(user.getName()))
                .flatMap(user -> user.getFractions().stream())
                .filter(Objects::nonNull)
                .filter(f -> f.getDenominator() != 0) // Avoid dividing by 0
                .filter(f -> Math.abs(f.getNumerator()) % Math.abs(f.getDenominator()) != 0) // Check if it is decimal
                .filter(Fraction::isImproper) // Check the fraction is improper
                .map(Fraction::decimal); //BUG #15
    }

    /* Issue #14 - Function B */
    public Stream<String> findUserFamilyNameByAllNegativeSignFractionDistinct() {
        return new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .filter(fraction -> fraction.getDenominator() != 0) // Avoid dividing by 0
                        .anyMatch(fraction -> fraction.decimal() < 0)
                )
                .map(User::getFamilyName)
                .distinct();
    }
}