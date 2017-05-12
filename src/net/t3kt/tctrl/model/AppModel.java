package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.AppSchema;

public final class AppModel extends ModelNode<AppSchema> {
    private AppModel(AppSchema schemaNode) {
        super(schemaNode);
    }

    public ImmutableList<ModuleModel> getAllModules() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public ModuleModel getModuleByPath(String path) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public ParamModel getParamByPath(String path) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public static final class Builder {
        private final AppSchema appSchema;

        private Builder(AppSchema appSchema) {
            this.appSchema = appSchema;
        }

        public AppModel build() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }
}
