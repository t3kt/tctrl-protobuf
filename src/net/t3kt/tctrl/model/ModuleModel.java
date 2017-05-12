package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.ModuleSchema;

public final class ModuleModel extends ParentModelNode<ModuleSchema> implements Groupable {
    ModuleModel(ModuleSchema schemaNode) {
        super(schemaNode);
    }

    @Override
    public ImmutableList<ModuleModel> getChildModules() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public ImmutableMap<String, ModelNodeGroup<ModuleModel>> getChildGroups() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public ParamModel getParamByKey(String key) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public String getGroup() {
        return schemaNode.getGroup();
    }

    public static final class Builder {
        private final AppModel.Builder appModelBuilder;
        private final ModuleSchema moduleSchema;

        public Builder(AppModel.Builder appModelBuilder, ModuleSchema moduleSchema) {
            this.appModelBuilder = appModelBuilder;
            this.moduleSchema = moduleSchema;
        }

        public ModuleModel build() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }
}
