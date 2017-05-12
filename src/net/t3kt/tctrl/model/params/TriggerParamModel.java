package net.t3kt.tctrl.model.params;

import net.t3kt.tctrl.schema.params.TriggerParamSchema;

public interface TriggerParamModel extends SingleParamModel<TriggerParamSchema> {
    void trigger();
}
