package net.t3kt.tctrl.model.impl;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MiscTest {
    @Test
    public void immutableMapOrdering() {
        ImmutableMap<String, Integer> map = ImmutableMap.<String, Integer>builder()
                .put("M", 0)
                .put("A", 1)
                .put("B", 2)
                .put("Z", 3)
                .put("R", 4)
                .build();
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Entry<String, Integer> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        assertThat(keys)
                .containsExactly("M", "A", "B", "Z", "R")
                .inOrder();
        assertThat(values)
                .containsExactly(0, 1, 2, 3, 4)
                .inOrder();
    }

}