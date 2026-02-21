package ru.job4j.algo.slidingwindow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

class MaxOverlapIntervalTest {

    @Test
    public void whenIntervalsOverlapThenFindMaxOverlapInterval() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 4));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(7, 8));

        int[] result = MaxOverlapInterval.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(3, 4);
    }

    @Test
    public void whenSequentialIntervalsThenFindMaxOverlapInterval() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(2, 4));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(4, 6));

        int[] result = MaxOverlapInterval.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(2, 3);
    }

    @Test
    public void whenNonOverlappingIntervalsThenFindMaxOverlapInterval() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 10));
        intervals.add(new Interval(2, 3));
        intervals.add(new Interval(4, 5));
        intervals.add(new Interval(6, 7));
        intervals.add(new Interval(8, 9));

        int[] result = MaxOverlapInterval.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(2, 3);
    }

    @Test
    public void whenSingleIntervalThenFindMaxOverlapInterval() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 10));

        int[] result = MaxOverlapInterval.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(1, 10);
    }

    @Test
    public void whenNoIntervalsThenFindMaxOverlapInterval() {
        List<Interval> intervals = new ArrayList<>();

        int[] result = MaxOverlapInterval.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(-1, -1);
    }
}