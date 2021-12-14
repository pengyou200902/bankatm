package model;

public enum AccountTypes {
    CHECKING("CHECKING"),
    SAVING("SAVING"),
    SECURITY("SECURITY");

    private String accountTypeString;

    private AccountTypes(String typeStr) {
        this.accountTypeString = typeStr;
    }

    public String getTypeString() {
        return accountTypeString;
    }
}
