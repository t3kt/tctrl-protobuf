#pragma once

#include "SchemaNode.h"
#include <tctrl-schema.pb.h>

class ModuleSchema : public ParentSchemaNode<tctrl::schema::ModuleSpec> {
public:
	using Spec = tctrl::schema::ModuleSpec;
	ModuleSchema(const Spec& spec) : ParentSchemaNode(spec) {}

private:
};

