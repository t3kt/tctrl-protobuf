package net.t3kt.tctrl.schema.json;

import static net.t3kt.tctrl.schema.json.ParserUtil.getInt;
import static net.t3kt.tctrl.schema.json.ParserUtil.getInt32Value;
import static net.t3kt.tctrl.schema.json.ParserUtil.getObjects;
import static net.t3kt.tctrl.schema.json.ParserUtil.getString;
import static net.t3kt.tctrl.schema.json.ParserUtil.getStrings;
import static net.t3kt.tctrl.schema.json.ParserUtil.getValue;
import static net.t3kt.tctrl.schema.json.ParserUtil.has;

import com.google.common.base.Enums;
import javax.json.JsonObject;
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

public final class LegacyJsonParser {

    public static ParamOption parseParamOption(JsonObject obj) {
        ParamOption.Builder result = ParamOption.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        return result.build();
    }

    public static OptionList parseOptionList(JsonObject obj) {
        OptionList.Builder result = OptionList.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getObjects(obj, "options", LegacyJsonParser::parseParamOption).ifPresent(result::addAllOption);
        return result.build();
    }

    public static ParamPartSpec parseParamPartSpec(JsonObject obj) {
        ParamPartSpec.Builder result = ParamPartSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getString(obj, "path").ifPresent(result::setPath);
        getValue(obj, "default").ifPresent(result::setDefaultVal);
        getValue(obj, "value").ifPresent(result::setValue);
        getValue(obj, "minLimit").ifPresent(result::setMinLimit);
        getValue(obj, "maxLimit").ifPresent(result::setMaxLimit);
        getValue(obj, "minNorm").ifPresent(result::setMinNorm);
        getValue(obj, "maxNorm").ifPresent(result::setMaxNorm);
        return result.build();
    }

    public static ParamSpec parseParamSpec(JsonObject obj) {
        ParamSpec.Builder result = ParamSpec.newBuilder();
        result.setKey(obj.getString("key"));
        result.setPath(obj.getString("path"));
        getString(obj, "label").ifPresent(result::setLabel);
        getString(obj, "style").ifPresent(result::setStyle);
        getString(obj, "group").ifPresent(result::setGroup);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        getString(obj, "help").ifPresent(result::setHelp);
        getString(obj, "offHelp").ifPresent(result::setOffHelp);
        getString(obj, "buttonText").ifPresent(result::setButtonText);
        getString(obj, "buttonOffText").ifPresent(result::setButtonOffText);
        String typeStr = getString(obj, "type").orElse(null);
        ParamType type = typeStr == null ? ParamType.OTHER : Enums.getIfPresent(ParamType.class, typeStr).or(ParamType.OTHER);
        result.setType(type);
        if (type == ParamType.OTHER) {
            result.setOtherType(obj.getString("otherType", typeStr));
        }
        getValue(obj, "default").ifPresent(result::setDefaultVal);
        getValue(obj, "value").ifPresent(result::setValue);
        getInt32Value(obj, "valueIndex").ifPresent(result::setValueIndex);
        getValue(obj, "minLimit").ifPresent(result::setMinLimit);
        getValue(obj, "maxLimit").ifPresent(result::setMaxLimit);
        getValue(obj, "minNorm").ifPresent(result::setMinNorm);
        getValue(obj, "maxNorm").ifPresent(result::setMaxNorm);
        getObjects(obj, "options", LegacyJsonParser::parseParamOption).ifPresent(result::addAllOption);
        getString(obj, "optionsList").ifPresent(result::setOptionListKey);
        getObjects(obj, "parts", LegacyJsonParser::parseParamPartSpec).ifPresent(result::addAllPart);
        return result.build();
    }

    public static ModuleTypeSpec parseModuleTypeSpec(JsonObject obj) {
        ModuleTypeSpec.Builder result = ModuleTypeSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getObjects(obj, "paramGroups", LegacyJsonParser::parseGroupInfo).ifPresent(result::addAllParamGroup);
        getObjects(obj, "params", LegacyJsonParser::parseParamSpec).ifPresent(result::addAllParam);
        return result.build();
    }

    public static ModuleSpec parseModuleSpec(JsonObject obj) {
        ModuleSpec.Builder result = ModuleSpec.newBuilder()
                .setKey(obj.getString("key"))
                .setPath(obj.getString("path"));
        getString(obj, "label").ifPresent(result::setLabel);
        getString(obj, "moduleType").ifPresent(result::setModuleType);
        getString(obj, "group").ifPresent(result::setGroup);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        getObjects(obj, "paramGroups", LegacyJsonParser::parseGroupInfo).ifPresent(result::addAllParamGroup);
        getObjects(obj, "params", LegacyJsonParser::parseParamSpec).ifPresent(result::addAllParam);
        getObjects(obj, "childGroups", LegacyJsonParser::parseGroupInfo).ifPresent(result::addAllChildGroup);
        getObjects(obj, "children", LegacyJsonParser::parseModuleSpec).ifPresent(result::addAllChildModule);
        return result.build();
    }

    public static ConnectionInfo parseConnectionInfo(JsonObject obj) {
        ConnectionInfo.Builder result = ConnectionInfo.newBuilder();
        getString(obj, "key").ifPresent(result::setKey);
        getString(obj, "label").ifPresent(result::setLabel);
        getString(obj, "type").ifPresent(result::setType);
        getString(obj, "host").ifPresent(result::setHost);
        getInt(obj, "port").ifPresent(result::setPort);
        return result.build();
    }

    public static GroupInfo parseGroupInfo(JsonObject obj) {
        GroupInfo.Builder result = GroupInfo.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        return result.build();
    }

    public static AppSpec parseAppSpec(JsonObject obj) {
        AppSpec.Builder result = AppSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        getString(obj, "description").ifPresent(result::setDescription);
        getObjects(obj, "childGroups", LegacyJsonParser::parseGroupInfo).ifPresent(result::addAllChildGroup);
        if (has(obj, "path")) {
            result.setPath(obj.getString("path"));
        } else {
            result.setPath("/" + result.getKey());
        }
        getObjects(obj, "optionLists", LegacyJsonParser::parseOptionList).ifPresent(result::addAllOptionList);
        getObjects(obj, "moduleTypes", LegacyJsonParser::parseModuleTypeSpec).ifPresent(result::addAllModuleType);
        getObjects(obj, "children", LegacyJsonParser::parseModuleSpec).ifPresent(result::addAllChildModule);
        getObjects(obj, "connections", LegacyJsonParser::parseConnectionInfo).ifPresent(result::addAllConnection);
        return result.build();
    }
}
