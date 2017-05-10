package net.t3kt.tctrl.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ConnectionInfo;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleTypeSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.OptionList;
import net.t3kt.tctrl.schema.modules.ModuleSchema;
import net.t3kt.tctrl.schema.params.OptionListProvider;

public final class AppSchema extends SchemaNode<AppSpec> implements OptionListProvider {
    private final ImmutableList<ModuleSchema> childModules;
    private final ImmutableMap<String, ModuleSchema> modulesByPath;

    public AppSchema(AppSpec spec) {
        super(spec);
        this.childModules = spec.getChildModuleList()
                .stream()
                .map(m -> new ModuleSchema(m, this))
                .collect(ImmutableList.toImmutableList());
        this.modulesByPath = Maps.uniqueIndex(childModules, ((ModuleSchema m) -> m.getKey()));
    }

    public String getKey() {
        return spec.getKey();
    }

    public String getLabel() {
        return spec.getLabel();
    }

    public String getDescription() {
        return spec.getDescription();
    }

    public List<String> getTags() {
        return spec.getTagList();
    }

    public List<OptionList> getOptionLists() {
        return spec.getOptionListList();
    }

    @Override
    public OptionList getOptionList(String key) {
        return getOptionLists()
                .stream()
                .filter(l -> key.equals(l.getKey()))
                .findFirst()
                .orElse(null);
    }

    public List<ModuleTypeSpec> getModuleTypes() {
        return spec.getModuleTypeList();
    }

    public ModuleTypeSpec getModuleType(String key) {
        return getModuleTypes()
                .stream()
                .filter(t -> key.equals(t.getKey()))
                .findFirst()
                .orElse(null);
    }

    public List<ConnectionInfo> getConnections() {
        return spec.getConnectionList();
    }

    public ConnectionInfo getConnection(String key) {
        return getConnections()
                .stream()
                .filter(c -> key.equals(c.getKey()))
                .findFirst()
                .orElse(null);
    }

    public ImmutableList<ModuleSchema> getChildModules() {
        return childModules;
    }

    public ModuleSchema getModuleByPath(String path) {
        return modulesByPath.get(path);
    }
}
