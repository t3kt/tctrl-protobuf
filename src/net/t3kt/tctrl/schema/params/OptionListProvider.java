package net.t3kt.tctrl.schema.params;

import net.t3kt.tctrl.schema.TctrlSchemaProto.OptionList;

public interface OptionListProvider {
    OptionList getOptionList(String key);
}
