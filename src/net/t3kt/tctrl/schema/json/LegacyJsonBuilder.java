package net.t3kt.tctrl.schema.json;

import static net.t3kt.tctrl.schema.json.BuilderUtil.addField;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ConnectionInfo;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleTypeSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.OptionList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamOption;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public final class LegacyJsonBuilder {

    public static JsonObject toJsonObject(ParamOption spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        return obj.build();
    }

    public static JsonObject toJsonObject(OptionList spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        addField(obj, "options", spec.getOptionList(), LegacyJsonBuilder::toJsonObject);
        return obj.build();
    }

    public static JsonObject toJsonObject(ParamPartSpec spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        addField(obj, "path", spec.getPath());
        addField(obj, "default", spec.getDefaultVal());
        addField(obj, "value", spec.getValue());
        addField(obj, "minLimit", spec.getMinLimit());
        addField(obj, "maxLimit", spec.getMaxLimit());
        addField(obj, "minNorm", spec.getMinNorm());
        addField(obj, "maxNorm", spec.getMaxNorm());
        return obj.build();
    }

    private static String typeToString(ParamType type) {
        if (type == ParamType.UNRECOGNIZED) {
            return "other";
        }
        return type.name().toLowerCase();
    }

    public static JsonObject toJsonObject(ParamSpec spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey())
                .add("path", spec.getPath());
        addField(obj, "label", spec.getLabel());
        addField(obj, "style", spec.getStyle());
        addField(obj, "group", spec.getGroup());
        addField(obj, "tags", spec.getTagList());
        addField(obj, "help", spec.getHelp());
        addField(obj, "offHelp", spec.getOffHelp());
        addField(obj, "buttonText", spec.getButtonText());
        addField(obj, "buttonOffText", spec.getButtonOffText());
        addField(obj, "type", typeToString(spec.getType()));
        addField(obj, "otherType", spec.getOtherType());
        addField(obj, "default", spec.getDefaultVal());
        addField(obj, "value", spec.getValue());
        addField(obj, "valueIndex", spec.getValueIndex());
        addField(obj, "minLimit", spec.getMinLimit());
        addField(obj, "maxLimit", spec.getMaxLimit());
        addField(obj, "minNorm", spec.getMinNorm());
        addField(obj, "maxNorm", spec.getMaxNorm());
        addField(obj, "options", spec.getOptionList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "optionsList", spec.getOptionListKey());
        addField(obj, "parts", spec.getPartList(), LegacyJsonBuilder::toJsonObject);
        return obj.build();
    }

    public static JsonObject toJsonObject(ModuleTypeSpec spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        addField(obj, "paramGroups", spec.getParamGroupList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "params", spec.getParamList(), LegacyJsonBuilder::toJsonObject);
        return obj.build();
    }

    public static JsonObject toJsonObject(ModuleSpec spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey())
                .add("path", spec.getPath());
        addField(obj, "label", spec.getLabel());
        addField(obj, "moduleType", spec.getModuleType());
        addField(obj, "group", spec.getGroup());
        addField(obj, "tags", spec.getTagList());
        addField(obj, "paramGroups", spec.getParamGroupList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "params", spec.getParamList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "childGroups", spec.getChildGroupList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "children", spec.getChildModuleList(), LegacyJsonBuilder::toJsonObject);
        return obj.build();
    }

    public static JsonObject toJsonObject(ConnectionInfo spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        addField(obj, "key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        addField(obj, "type", spec.getLabel());
        addField(obj, "host", spec.getHost());
        if (spec.getPort() != 0) {
            obj.add("port", spec.getPort());
        }
        return obj.build();
    }

    public static JsonObject toJsonObject(GroupInfo spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey());
        addField(obj, "label", spec.getLabel());
        addField(obj, "tags", spec.getTagList());
        return obj.build();
    }

    public static JsonObject toJsonObject(AppSpec spec) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("key", spec.getKey())
                .add("path", spec.getPath());
        addField(obj, "label", spec.getLabel());
        addField(obj, "tags", spec.getTagList());
        addField(obj, "description", spec.getDescription());
        addField(obj, "moduleTypes", spec.getModuleTypeList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "optionLists", spec.getOptionListList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "childGroups", spec.getChildGroupList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "children", spec.getChildModuleList(), LegacyJsonBuilder::toJsonObject);
        addField(obj, "connections", spec.getConnectionList(), LegacyJsonBuilder::toJsonObject);
        return obj.build();
    }
}
