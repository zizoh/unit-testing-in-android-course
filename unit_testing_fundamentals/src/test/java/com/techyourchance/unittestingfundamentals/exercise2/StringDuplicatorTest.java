package com.techyourchance.unittestingfundamentals.exercise2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringDuplicatorTest {

    private StringDuplicator SUT;

    @Before
    public void setup() {
        SUT = new StringDuplicator();
    }

    @Test
    public void duplicate_withEmptyString_returnsEmptyString() {
        String expectedResult = "";
        String actualResult = SUT.duplicate("");

        Assert.assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void duplicate_withSingleCharacter_returnsDuplicatedString() {
        String expectedResult = "zz";
        String actualResult = SUT.duplicate("z");

        Assert.assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void duplicate_withLongString_returnsDuplicatedString() {
        String expectedResult = "yoyo";
        String actualResult = SUT.duplicate("yo");

        Assert.assertThat(actualResult, is(expectedResult));
    }

}