package net.t3kt.tctrl.model.params;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.VectorParamSchema;

public interface VectorParamModel<T extends Number & Comparable> extends ParamModel<VectorParamSchema<T>> {

    ParamType getPartType();

    ImmutableList<NumericParamModel<T>> getParts();

    int getPartCount();
}
