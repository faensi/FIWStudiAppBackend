package de.fhws.fiw.fachschaft.studiapp.models;

public enum State {

    READY(10),
    EMPTY(20),
    SWITCHED_OFF(30),
    BROKEN(40);

    public int value;

    State(int value) {
        this.value = value;
    }

    public static State getByValue(int value) {
        switch (value) {
            case 10:
                return State.READY;
            case 20:
                return State.EMPTY;
            case 30:
                return State.SWITCHED_OFF;
            case 40:
                return State.BROKEN;
            default:
                throw new IllegalArgumentException(String.valueOf(value));
        }
    }

	public int getValue() {
		return value;
	}
}

