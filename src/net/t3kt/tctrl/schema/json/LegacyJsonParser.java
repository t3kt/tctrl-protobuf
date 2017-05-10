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

import static net.t3kt.tctrl.schema.json.ParserUtil.getString;
import static net.t3kt.tctrl.schema.json.ParserUtil.getStrings;

public final class LegacyJsonParser {

    public ParamOption parseParamOption(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public OptionList parseOptionList(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ParamPartSpec parseParamPartSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ParamSpec parseParamSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ModuleTypeSpec parseModuleTypeSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ModuleSpec parseModuleSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public ConnectionInfo parseConnectionInfo(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }

    public GroupInfo parseGroupInfo(JsonObject obj) {
        GroupInfo.Builder group = GroupInfo.newBuilder()
                .setKey(obj.getString("key"));
        getString(obj, "label").ifPresent(group::setLabel);
        getStrings(obj, "tags").ifPresent(group::addAllTag);
        return group.build();
    }

    public AppSpec parseAppSpec(JsonObject obj) {
        throw new RuntimeException("NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED! NOT IMPLEMENTED!");
    }
}
