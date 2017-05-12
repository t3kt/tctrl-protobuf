package net.t3kt.tctrl.model.params;

import java.util.Optional;
import net.t3kt.tctrl.schema.params.SingleParamSchema;

public interface SingleParamModel<S extends SingleParamSchema> extends ParamModel<S> {

    Optional<VectorParamModel<?>> getParentParam();
}
