package de.fhws.fiw.fachschaft.studiapp.models;


public enum Role {
    ADMIN(10),
    USER(20),
    COFFEE_MACHINE(30);

    public int value;

    Role(int value) {
        this.value = value;
    }

    public static Role getByValue(int value) {
        switch (value) {
            case 10:
                return Role.ADMIN;
            case 20:
                return Role.USER;
            case 30:
                return Role.COFFEE_MACHINE;

            default:
                throw new IllegalArgumentException(String.valueOf(value));
        }
    }

    public int getValue() {
        return value;
    }


}
