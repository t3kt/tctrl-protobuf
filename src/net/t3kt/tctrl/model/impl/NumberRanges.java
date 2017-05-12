package net.t3kt.tctrl.model.impl;

import com.google.common.collect.Range;
import java.util.Optional;

final class NumberRanges {
    private NumberRanges() {}

    static <T extends Number & Comparable> Range<T> create(Optional<T> low, Optional<T> high) {
        if (low.isPresent()) {
            if (high.isPresent()) {
                return Range.closed(low.get(), high.get());
            } else {
                return Range.atLeast(low.get());
            }
        } else {
            if (high.isPresent()) {
                return Range.atMost(high.get());
            } else {
                return Range.all();
            }
        }
    }
}
