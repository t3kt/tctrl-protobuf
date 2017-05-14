package net.t3kt.tctrl.gui.schematree;

import com.google.common.base.MoreObjects;
import net.t3kt.tctrl.schema.AppSchema;

final class Formatters {
    private Formatters() {
    }

    static String formatAppSchema(AppSchema schema) {
        return MoreObjects.toStringHelper("AppSchema")
                .add("key", schema.getKey())
                .add("label", schema.getLabel())
                .toString();
    }
}
