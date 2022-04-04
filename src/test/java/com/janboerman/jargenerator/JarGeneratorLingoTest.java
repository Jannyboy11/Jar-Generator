package com.janboerman.jargenerator;

import static org.junit.Assert.*;
import org.junit.Test;

public class JarGeneratorLingoTest {

    @Test
    public void testZero() {
        final Charges expected = Charges.empty();
        final Charges actual = JarGeneratorLingo.interpret("You have 0 charges left in your jar generator.");

        assertEquals(expected, actual);
    }

    @Test
    public void testOne() {
        final Charges expected = Charges.known(1);
        final Charges actual = JarGeneratorLingo.interpret("You have 1 charge left in your jar generator.");

        assertEquals(expected, actual);
    }

    @Test
    public void testFortyTwo() {
        final Charges expected = Charges.known(42);
        final Charges actual = JarGeneratorLingo.interpret("You have 42 charges left in your jar generator.");

        assertEquals(expected, actual);
    }

    @Test
    public void testNew() {
        final Charges expected = Charges.full();
        final Charges actual = JarGeneratorLingo.interpret("You have purchased: Jar generator x1");

        assertEquals(expected, actual);
    }

    @Test
    public void testRecharge() {
        final Charges expected = Charges.full();
        final Charges actual = JarGeneratorLingo.interpret("Elnock recharges your magical jar generator. You now have one hundred charges.");

        assertEquals(expected, actual);
    }

    @Test
    public void testDeplete() {
        final Charges expected = Charges.empty();
        final Charges actual = JarGeneratorLingo.interpret("Your jar generator runs out of charges and disappears.");

        assertEquals(expected, actual);
    }

}
