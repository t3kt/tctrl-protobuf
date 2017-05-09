package net.t3kt.tctrl.schema.params;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.OptionList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamOption;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class MenuParamSchema extends ScalarParamSchema<String> {
    private final List<ParamOption> options;

    MenuParamSchema(ParamSpec spec, @Nullable OptionListProvider optionListProvider) {
        super(spec);
        if (spec.getOptionCount() > 0) {
            this.options = spec.getOptionList();
        } else {
            OptionList options = optionListProvider == null || Strings.isNullOrEmpty(spec.getOptionListKey()) ? null : optionListProvider.getOptionList(spec.getOptionListKey());
            this.options = options == null ? ImmutableList.of() : options.getOptionList();
        }
    }

    public List<ParamOption> getOptions() {
        return options;
    }

    public Optional<Integer> getValueIndex() {
        if (!spec.hasValueIndex() || spec.getValueIndex().getValue() == -1) {
            return Optional.empty();
        }
        return Optional.of(spec.getValueIndex().getValue());
    }

    @Override
    protected Optional<String> convert(Value value) {
        return Values.toString(value);
    }
}
