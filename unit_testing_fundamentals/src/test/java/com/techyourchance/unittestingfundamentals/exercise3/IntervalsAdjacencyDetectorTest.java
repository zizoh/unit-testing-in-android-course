package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class IntervalsAdjacencyDetectorTest {

    private IntervalsAdjacencyDetector SUT;

    @Before
    public void setup() {
        SUT = new IntervalsAdjacencyDetector();
    }

    @Test
    public void isAdjacent_endOfInterval1BeforeStartOfInterval2_returnsFalse() {
        Interval interval1 = new Interval(1, 5);
        Interval interval2 = new Interval(7, 9);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_endOfInterval1SameAsStartOfInterval2_returnsTrue() {
        Interval interval1 = new Interval(1, 5);
        Interval interval2 = new Interval(5, 9);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval1OverlapsInterval2AtStart_returnsFalse() {
        Interval interval1 = new Interval(1, 6);
        Interval interval2 = new Interval(5, 9);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_Interval1SameAsInterval2_returnsFalse() {
        Interval interval1 = new Interval(1, 6);
        Interval interval2 = new Interval(1, 6);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_Interval1ContainsInterval2_returnsFalse() {
        Interval interval1 = new Interval(1, 6);
        Interval interval2 = new Interval(2, 4);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_Interval1ContainedInInterval2_returnsFalse() {
        Interval interval1 = new Interval(2, 4);
        Interval interval2 = new Interval(1, 6);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1OverlapsInterval2AtEnd_returnsFalse() {
        Interval interval1 = new Interval(2, 7);
        Interval interval2 = new Interval(1, 6);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_startOfInterval1SameAsEndOfInterval2_returnsTrue() {
        Interval interval1 = new Interval(2, 7);
        Interval interval2 = new Interval(1, 2);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_startOfInterval1AfterEndOfInterval2_returnsFalse() {
        Interval interval1 = new Interval(3, 7);
        Interval interval2 = new Interval(1, 2);

        boolean result = SUT.isAdjacent(interval1, interval2);

        Assert.assertThat(result, is(false));
    }
}