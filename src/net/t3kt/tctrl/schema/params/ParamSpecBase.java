package net.t3kt.tctrl.schema.params;

import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import java.util.List;

public abstract class ParamSpecBase {
    final ParamSpec spec;

    ParamSpecBase(ParamSpec spec) {
        this.spec = spec;
    }

    public String getKey() {
        return spec.getKey();
    }

    public String getLabel() {
        return spec.getLabel();
    }

    public String getPath() {
        return spec.getPath();
    }

    public String getOtherType() {
        return spec.getOtherType();
    }

    public String getStyle() {
        return spec.getStyle();
    }

    public String getGroup() {
        return spec.getGroup();
    }

    public List<String> getTags() {
        return spec.getTagList();
    }

    public String getHelp() {
        return spec.getHelp();
    }
}
