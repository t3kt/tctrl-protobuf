package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Optional;
import net.t3kt.tctrl.model.params.MenuParamModel;
import net.t3kt.tctrl.model.params.ParamOptionModel;
import net.t3kt.tctrl.schema.params.MenuParamSchema;

class MenuParamModelImpl extends ScalarParamModelImpl<String, MenuParamSchema> implements MenuParamModel {

    private final ImmutableList<ParamOptionModel> options;
    private final ImmutableMap<String, ParamOptionModel> optionsByKey;
    Optional<Integer> valueIndex = Optional.empty();

    private MenuParamModelImpl(MenuParamSchema schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
        this.options = ParamOptionModel.createList(schema.getOptions());
        this.optionsByKey = Maps.uniqueIndex(options, (ParamOptionModel o) -> o.getKey());
    }

    @Override
    public Optional<Integer> getValueIndex() {
        return valueIndex;
    }

    @Override
    public boolean trySetValueIndex(int valueIndex) {
        if (!isValidValueIndex(valueIndex)) {
            return false;
        }
        value = options.get(valueIndex).getKey();
        this.valueIndex = Optional.of(valueIndex);
        return true;
    }

    @Override
    public boolean trySetValue(String value) {
        ParamOptionModel option = optionsByKey.get(value);
        if (option == null) {
            return false;
        }
        this.value = value;
        this.valueIndex = Optional.of(option.getIndex());
        return true;
    }

    @Override
    public boolean isValidValueIndex(int valueIndex) {
        return valueIndex >= 0 && valueIndex < options.size();
    }

    @Override
    public boolean hasCustomValue() {
        return !getValueIndex().isPresent();
    }

    @Override
    public boolean allowsCustomValue() {
        return false;
    }

    @Override
    public boolean hasOptions() {
        return !options.isEmpty();
    }

    @Override
    public ImmutableList<ParamOptionModel> getOptions() {
        return options;
    }

    @Override
    public ParamOptionModel getOption(String key) {
        return optionsByKey.get(key);
    }

    @Override
    public boolean isValidValue(String value) {
        return optionsByKey.containsKey(value);
    }

    static MenuParamModelImpl forMenu(MenuParamSchema schema, ModuleModelImpl parentModule) {
        return new MenuParamModelImpl(schema, parentModule);
    }

    static MenuParamModelImpl forString(MenuParamSchema schema, ModuleModelImpl parentModule) {
        return new MenuParamModelImpl(schema, parentModule) {

            @Override
            public boolean trySetValue(String value) {
                if (super.trySetValue(value)) {
                    return true;
                }
                this.value = value;
                this.valueIndex = Optional.empty();
                return true;
            }

            @Override
            public boolean allowsCustomValue() {
                return true;
            }

            @Override
            public boolean isValidValue(String value) {
                return true;
            }
        };
    }
}
