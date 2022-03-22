package com.janboerman.jargenerator;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.OptionalInt;

public class RegexTest {

    @Test
    public void testZero() {
        final OptionalInt expected = OptionalInt.of(0);
        final OptionalInt actual = JarGeneratorLingo.interpret("You have 0 charges left in your jar generator.");

        assertEquals(expected, actual);
    }

    @Test
    public void testOne() {
        final OptionalInt expected = OptionalInt.of(1);
        final OptionalInt actual = JarGeneratorLingo.interpret("You have 1 charge left in your jar generator.");

        assertEquals(expected, actual);
    }

    @Test
    public void testFourtyTwo() {
        final OptionalInt expected = OptionalInt.of(42);
        final OptionalInt actual = JarGeneratorLingo.interpret("You have 42 charges left in your jar generator.");

        assertEquals(expected, actual);
    }

}
