package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NegativeNumberValidatorTest {

    private NegativeNumberValidator SUT;

    @Before
    public void setup() {
        SUT = new NegativeNumberValidator();
    }

    @Test
    public void isNegative_withZero_returnsFalse() {
        boolean result = SUT.isNegative(0);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isNegative_withPositiveNumber_returnsFalse() {
        boolean result = SUT.isNegative(1);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isNegative_withNegativeNumber_returnsTrue() {
        boolean result = SUT.isNegative(-1);

        Assert.assertThat(result, is(true));
    }

}