package com.janboerman.jargenerator;

import java.util.Arrays;

public abstract class Charges {

    private static final int MAX_CHARGES = 100;

    public Charges() {
    }

    public static Charges unknown() {
        return Unknown.INSTANCE;
    }

    public static Charges empty() {
        return known(0);
    }

    public static Charges full() {
        return known(MAX_CHARGES);
    }

    public static Charges probably(int charges) {
        return new BestGuess(charges);
    }

    public static Charges known(int charges) {
        return new ExactlyKnown(charges);
    }

    public abstract boolean isKnown();
    public abstract int getAmount();
}

final class Unknown extends Charges {

    static final Unknown INSTANCE = new Unknown();

    private Unknown() {}

    @Override public boolean isKnown() { return false; }
    @Override public int getAmount() { throw new IllegalStateException("Unknown number of charges"); }

    @Override public String toString() { return "Unknown"; }
}

final class BestGuess extends Charges {

    private final int charges;

    BestGuess(int charges) {
        this.charges = charges;
    }

    @Override public boolean isKnown() { return false; }
    @Override public int getAmount() { return charges; }

    @Override public String toString() { return "BestGuess(" + charges + ")"; }
    @Override public int hashCode() { return charges; }
    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BestGuess)) return false;

        BestGuess that = (BestGuess) o;
        return this.charges == that.charges;
    }
}

final class ExactlyKnown extends Charges {

    private final int charges;

    ExactlyKnown(int charge) {
        this.charges = charge;
    }

    @Override public boolean isKnown() { return true; }
    @Override public int getAmount() { return charges; }

    @Override public String toString() { return "ExactlyKnown(" + charges + ")"; }
    @Override public int hashCode() { return charges; }
    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ExactlyKnown)) return false;

        ExactlyKnown that = (ExactlyKnown) o;
        return this.charges == that.charges;
    }
}

