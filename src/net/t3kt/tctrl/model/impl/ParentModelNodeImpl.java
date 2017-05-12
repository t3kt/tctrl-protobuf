package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.model.ModelNodeGroup;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.ParentModelNode;
import net.t3kt.tctrl.schema.ParentSchemaNode;

abstract class ParentModelNodeImpl<S extends ParentSchemaNode<?>> extends ModelNodeImpl<S> implements ParentModelNode<S> {
    private ImmutableMap<String, ModuleModel> childModules;
    private ImmutableMap<String, ModelNodeGroup<ModuleModel>> childModuleGroups;

    ParentModelNodeImpl(S schema) {
        super(schema);
    }

    void setChildModules(ImmutableMap<String, ModuleModel> childModules) {
        this.childModules = childModules;
    }

    void setChildModuleGroups(ImmutableMap<String, ModelNodeGroup<ModuleModel>> childModuleGroups) {
        this.childModuleGroups = childModuleGroups;
    }

    @Override
    public ImmutableCollection<ModuleModel> getChildModules() {
        return childModules.values();
    }

    @Override
    public ModuleModel getChildModuleByKey(String key) {
        return childModules.get(key);
    }

    @Override
    public ImmutableMap<String, ModelNodeGroup<ModuleModel>> getChildGroups() {
        return childModuleGroups;
    }
}
