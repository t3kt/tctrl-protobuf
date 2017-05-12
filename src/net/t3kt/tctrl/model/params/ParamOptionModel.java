package net.t3kt.tctrl.model.params;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamOption;

public final class ParamOptionModel {
    private final String key;
    private final String label;
    private final int index;

    private ParamOptionModel(String key, String label, int index) {
        this.key = key;
        this.label = label;
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public static ParamOptionModel create(ParamOption spec, int index) {
        return new ParamOptionModel(
                spec.getKey(),
                Strings.isNullOrEmpty(spec.getLabel()) ? spec.getKey() : spec.getLabel(),
                index);
    }

    public static ImmutableList<ParamOptionModel> createList(List<ParamOption> specs) {
        ImmutableList.Builder<ParamOptionModel> options = ImmutableList.builder();
        for (int i = 0; i < specs.size(); i++) {
            options.add(create(specs.get(i), i));
        }
        return options.build();
    }
}
