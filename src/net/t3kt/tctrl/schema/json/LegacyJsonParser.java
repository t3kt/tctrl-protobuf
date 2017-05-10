package net.t3kt.tctrl.schema.json;

import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ConnectionInfo;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ModuleTypeSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.OptionList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamOption;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import javax.json.JsonObject;

import static net.t3kt.tctrl.schema.json.ParserUtil.getInt;
import static net.t3kt.tctrl.schema.json.ParserUtil.getObjects;
import static net.t3kt.tctrl.schema.json.ParserUtil.getString;
import static net.t3kt.tctrl.schema.json.ParserUtil.getStrings;
import static net.t3kt.tctrl.schema.json.ParserUtil.has;

public final class LegacyJsonParser {

    public ParamOption parseParamOption(JsonObject obj) {
        ParamOption.Builder result = ParamOption.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        return result.build();
    }

    public OptionList parseOptionList(JsonObject obj) {
        OptionList.Builder result = OptionList.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getObjects(obj, "options", this::parseParamOption).ifPresent(result::addAllOption);
        return result.build();
    }

    public ParamPartSpec parseParamPartSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ParamSpec parseParamSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ModuleTypeSpec parseModuleTypeSpec(JsonObject obj) {
        ModuleTypeSpec.Builder result = ModuleTypeSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getObjects(obj, "paramGroups", this::parseGroupInfo).ifPresent(result::addAllParamGroup);
        getObjects(obj, "params", this::parseParamSpec).ifPresent(result::addAllParam);
        return result.build();
    }

    public ModuleSpec parseModuleSpec(JsonObject obj) {
        ModuleSpec.Builder result = ModuleSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getObjects(obj, "paramGroups", this::parseGroupInfo).ifPresent(result::addAllParamGroup);
        getObjects(obj, "params", this::parseParamSpec).ifPresent(result::addAllParam);
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ConnectionInfo parseConnectionInfo(JsonObject obj) {
        ConnectionInfo.Builder result = ConnectionInfo.newBuilder();
        getString(obj, "key").ifPresent(result::setKey);
        getString(obj, "label").ifPresent(result::setLabel);
        getString(obj, "type").ifPresent(result::setType);
        getString(obj, "host").ifPresent(result::setHost);
        getInt(obj, "port").ifPresent(result::setPort);
        return result.build();
    }

    public GroupInfo parseGroupInfo(JsonObject obj) {
        GroupInfo.Builder result = GroupInfo.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        return result.build();
    }

    public AppSpec parseAppSpec(JsonObject obj) {
        AppSpec.Builder result = AppSpec.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(result::setLabel);
        getStrings(obj, "tags").ifPresent(result::addAllTag);
        getString(obj, "description").ifPresent(result::setDescription);
        getObjects(obj, "childGroups", this::parseGroupInfo).ifPresent(result::addAllChildGroup);
        if (has(obj, "path")) {
            result.setPath(obj.getString("path"));
        } else {
            result.setPath("/" + result.getKey());
        }
        getObjects(obj, "optionLists", this::parseOptionList).ifPresent(result::addAllOptionList);
        getObjects(obj, "moduleTypes", this::parseModuleTypeSpec).ifPresent(result::addAllModuleType);
        getObjects(obj, "children", this::parseModuleSpec).ifPresent(result::addAllChildModule);
        getObjects(obj, "connections", this::parseConnectionInfo).ifPresent(result::addAllConnection);
        return result.build();
    }
}
