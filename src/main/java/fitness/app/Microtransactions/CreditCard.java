package fitness.app.Microtransactions;

public class CreditCard {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;
    private String zipCode;

    CreditCard() {
        this.cardNumber = null;
        this.expiryDate = null;
        this.cvv = null;
        this.cardHolder = null;
        this.zipCode = null;
    }

    CreditCard(String cardNumber, String expiryDate, String cvv,
               String cardHolder, String zipCode) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.zipCode = zipCode;
    }

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
            if ( ! card.getCvv().matches(("\\d{3}" ))) {
                isValid = false;
            }
        }

        return isValid;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}