package net.t3kt.tctrl.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import java.util.List;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.params.ParamSchema;

public final class ModuleSchema extends ParentSchemaNode<ModuleSpec> implements Groupable {
    private final SchemaGroupList childGroups;
    private final SchemaGroupList paramGroups;
    private final ImmutableList<ModuleSchema> childModules;
    private final ImmutableList<ParamSchema> params;
    private final ImmutableMap<String, ParamSchema> paramsByKey;
    private final ImmutableListMultimap<String, ParamSchema> paramsByGroup;

    public ModuleSchema(ModuleSpec spec, AppSchema appSchema) {
        super(spec);
        this.childGroups = new SchemaGroupList(spec.getChildGroupList(), spec.getChildModuleList().stream().map(ModuleSpec::getGroup));
        this.paramGroups = new SchemaGroupList(spec.getParamGroupList(), spec.getParamList().stream().map(ParamSpec::getGroup));
        this.childModules = spec.getChildModuleList()
                .stream()
                .map(m -> new ModuleSchema(m, appSchema))
                .collect(ImmutableList.toImmutableList());
        this.params = spec.getParamList()
                .stream()
                .map(p -> ParamSchema.forParam(p, appSchema))
                .collect(ImmutableList.toImmutableList());
        this.paramsByKey = Maps.uniqueIndex(params, (ParamSchema p) -> p.getKey());
        this.paramsByGroup = Multimaps.index(params, (ParamSchema p) -> p.getGroup() == null ? "" : p.getGroup());
        // TODO: handle external module type definitions
    }

    @Override
    public String getKey() {
        return spec.getKey();
    }

    @Override
    public String getLabel() {
        return spec.getLabel();
    }

    @Override
    public String getPath() {
        return spec.getPath();
    }

    public String getModuleTypeKey() {
        return spec.getModuleType();
    }

    @Override
    public String getGroup() {
        return spec.getGroup();
    }

    public List<String> getTags() {
        return spec.getTagList();
    }

    public ImmutableList<ParamSchema> getParams() {
        return params;
    }

    public ParamSchema getParamByKey(String key) {
        return paramsByKey.get(key);
    }

    public ImmutableList<ParamSchema> getParamsByGroup(String group) {
        return paramsByGroup.get(group);
    }

    @Override
    public SchemaGroupList getChildGroups() {
        return childGroups;
    }

    public SchemaGroupList getParamGroups() {
        return paramGroups;
    }

    @Override
    public ImmutableList<ModuleSchema> getChildModules() {
        return childModules;
    }
}
