package fitness.app.Microtransactions;

public class CardValidation {
    public boolean CardValidation(CreditCard card) {
        boolean isValid = true;
        if ( card != null ) {
            if ( ! card.getCardNumber().matches(("\\d{4} \\d{4} \\d{4} \\d{4}"))) {
                isValid = false;
            }
            if ( ! card.getZipCode().matches(("\\d{5}"))) {
                isValid = false;
            }
            if ( ! card.getExpiryDate().matches(("\\d{2}" + "/" + "\\d{2}" ))) {
                isValid = false;
            }
        }

        return isValid;
    }
}
