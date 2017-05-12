package net.t3kt.tctrl.model.params;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public interface VectorParamModel<T> extends ParamModel {

    ParamType getPartType();

    ImmutableList<ScalarParamModel<T>> getParts();

    int getPartCount();
}
