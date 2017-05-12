package net.t3kt.tctrl.model.params;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import net.t3kt.tctrl.schema.params.MenuParamSchema;

public interface MenuParamModel extends ScalarParamModel<String, MenuParamSchema> {
    Optional<Integer> getValueIndex();
    boolean trySetValueIndex(int valueIndex);
    boolean isValidValueIndex(int valueIndex);

    boolean allowsCustomValue();
    boolean hasCustomValue();

    boolean hasOptions();
    ImmutableList<ParamOptionModel> getOptions();
    ParamOptionModel getOption(String key);
}
