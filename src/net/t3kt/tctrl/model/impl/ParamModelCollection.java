package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.model.params.NumericParamModel;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.model.params.SingleParamModel;
import net.t3kt.tctrl.model.params.VectorParamModel;

final class ParamModelCollection {
    private final ImmutableMap<String, ParamModel> params;
    private final ImmutableMap<String, SingleParamModel> flatParams;

    private ParamModelCollection(
            ImmutableMap<String, ParamModel> params,
            ImmutableMap<String, SingleParamModel> flatParams) {
        this.params = params;
        this.flatParams = flatParams;
    }

    ImmutableCollection<? extends ParamModel> getParams() {
        return params.values();
    }

    ImmutableCollection<? extends SingleParamModel> getFlatParams() {
        return flatParams.values();
    }

    ParamModel getParam(String key) {
        ParamModel param = params.get(key);
        if (param != null) {
            return param;
        }
        return flatParams.get(key);
    }

    static Builder builderByKey() {
        return new Builder() {
            @Override
            protected String getKey(ParamModel param) {
                return param.getKey();
            }
        };
    }

    static Builder builderByPath() {
        return new Builder() {
            @Override
            protected String getKey(ParamModel param) {
                return param.getPath();
            }
        };
    }

    static abstract class Builder {
        private final ImmutableMap.Builder<String, ParamModel> params = ImmutableMap.builder();
        private final ImmutableMap.Builder<String, SingleParamModel> flatParams = ImmutableMap.builder();

        private Builder() {}

        protected abstract String getKey(ParamModel param);

        Builder add(ParamModel param) {
            String key = getKey(param);
            params.put(key, param);
            if (param.isSingle()) {
                flatParams.put(key, (SingleParamModel) param);
            } else {
                for (NumericParamModel<?> paramPart : ((VectorParamModel<?>) param).getParts()) {
                    flatParams.put(getKey(paramPart), paramPart);
                }
            }
            return this;
        }

        ParamModelCollection build() {
            return new ParamModelCollection(params.build(), flatParams.build());
        }
    }
}
