package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public final class ParamTypes {
    private ParamTypes() {}

    private static final ImmutableSet<ParamType> NUMERIC_TYPES = Sets.immutableEnumSet(
            ParamType.INT,
            ParamType.FLOAT,
            ParamType.IVEC,
            ParamType.FVEC,
            ParamType.BOOL);

    private static final ImmutableSet<ParamType> SCALAR_TYPES = Sets.immutableEnumSet(
            ParamType.INT,
            ParamType.FLOAT,
            ParamType.BOOL,
            ParamType.STRING,
            ParamType.MENU);

    private static final ImmutableBiMap<ParamType, ParamType> CHILD_TYPES = ImmutableBiMap.of(
            ParamType.IVEC, ParamType.INT,
            ParamType.FVEC, ParamType.FLOAT);

    private static final ImmutableMap<ParamType, ParamType> PARENT_TYPES = CHILD_TYPES.inverse();

    public static boolean isNumeric(ParamType type) {
        return NUMERIC_TYPES.contains(type);
    }

    public static ParamType getPartType(ParamType parentType) {
        ParamType childType = CHILD_TYPES.get(parentType);
        if (childType == null) {
            throw new IllegalArgumentException("Type does not support parts: " + parentType);
        }
        return childType;
    }

    public static ParamType getParentType(ParamType childType) {
        ParamType parentType = PARENT_TYPES.get(childType);
        if (parentType == null) {
            throw new IllegalArgumentException("Type does not support parents: " + childType);
        }
        return parentType;
    }
}
