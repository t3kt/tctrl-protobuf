package net.t3kt.tctrl.schema;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class SchemaGroupList {
    private final ImmutableList<GroupInfo> groups;
    private final ImmutableMap<String, GroupInfo> groupsByKey;

    public SchemaGroupList(List<GroupInfo> declaredGroups, @Nullable Stream<String> referencedGroupNames) {
        ImmutableList.Builder<GroupInfo> groups = ImmutableList.builder();
        Map<String, GroupInfo> groupsByKey = new HashMap<>();
        for (GroupInfo group : declaredGroups) {
            if (groupsByKey.containsKey(group.getKey())) {
                throw new SchemaValidationException("Duplicate group declaration: " + group.getKey());
            }
            groupsByKey.put(group.getKey(), group);
            groups.add(group);
        }
        if (referencedGroupNames != null) {
            referencedGroupNames.forEach(name -> {
                if (!Strings.isNullOrEmpty(name) && !groupsByKey.containsKey(name)) {
                    GroupInfo group = GroupInfo.newBuilder()
                            .setKey(name)
                            .setLabel(name)
                            .build();
                    groupsByKey.put(name, group);
                    groups.add(group);
                }
            });
        }
        this.groups = groups.build();
        this.groupsByKey = ImmutableMap.copyOf(groupsByKey);
    }

    public ImmutableList<GroupInfo> getGroups() {
        return groups;
    }

    public GroupInfo getGroup(String key) {
        return groupsByKey.get(key);
    }
}
