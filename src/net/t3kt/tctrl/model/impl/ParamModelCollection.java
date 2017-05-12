package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.model.params.NumericParamModel;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.model.params.SingleParamModel;
import net.t3kt.tctrl.model.params.VectorParamModel;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

final class ParamModelCollection {
    private final ImmutableMap<String, ParamModel> params;
    private final ImmutableMap<String, SingleParamModel> flatParams;
    private final ModelNodeGroupList<? extends ParamModel<?>> paramGroups;

    private ParamModelCollection(
            ImmutableMap<String, ParamModel> params,
            ImmutableMap<String, SingleParamModel> flatParams,
            ModelNodeGroupList<? extends ParamModel<?>> paramGroups) {
        this.params = params;
        this.flatParams = flatParams;
        this.paramGroups = paramGroups;
    }

    ImmutableCollection<? extends ParamModel> getParams() {
        return params.values();
    }

    ImmutableCollection<? extends SingleParamModel> getFlatParams() {
        return flatParams.values();
    }

    ModelNodeGroupList<? extends ParamModel<?>> getParamGroups() {
        return paramGroups;
    }

    ParamModel getParam(String key) {
        ParamModel param = params.get(key);
        if (param != null) {
            return param;
        }
        return flatParams.get(key);
    }

    static Builder moduleLocalBuilder() {
        return new Builder(true) {
            @Override
            protected String getKey(ParamModel param) {
                return param.getKey();
            }
        };
    }

    static Builder globalBuilder() {
        return new Builder(false) {
            @Override
            protected String getKey(ParamModel param) {
                return param.getPath();
            }
        };
    }

    static abstract class Builder {
        private final ImmutableMap.Builder<String, ParamModel> params = ImmutableMap.builder();
        private final ImmutableMap.Builder<String, SingleParamModel> flatParams = ImmutableMap.builder();
        private final ModelNodeGroupList.Builder<ParamModel<?>> paramGroups;

        private Builder(boolean supportGroups) {
            paramGroups = supportGroups ? ModelNodeGroupList.builder() : null;
        }

        protected abstract String getKey(ParamModel param);

        Builder addGroup(GroupInfo group) {
            if (paramGroups != null) {
                paramGroups.addGroup(group);
            }
            return this;
        }

        Builder addGroups(Iterable<GroupInfo> groups) {
            if (paramGroups != null) {
                for (GroupInfo group : groups) {
                    paramGroups.addGroup(group);
                }
            }
            return this;
        }

        Builder addParam(ParamModel<?> param) {
            String key = getKey(param);
            params.put(key, param);
            if (paramGroups != null) {
                paramGroups.addNode(param);
            }

            if (param.isSingle()) {
                flatParams.put(key, (SingleParamModel) param);
            } else {
                for (NumericParamModel<?> paramPart : ((VectorParamModel<?>) param).getParts()) {
                    flatParams.put(getKey(paramPart), paramPart);
                    if (paramGroups != null) {
                        paramGroups.addNode(paramPart);
                    }
                }
            }
            return this;
        }

        Builder addParams(Iterable<? extends ParamModel> params) {
            for (ParamModel<?> param : params) {
                addParam(param);
            }
            return this;
        }

        ParamModelCollection build() {
            return new ParamModelCollection(params.build(), flatParams.build(), paramGroups.build());
        }
    }
}
