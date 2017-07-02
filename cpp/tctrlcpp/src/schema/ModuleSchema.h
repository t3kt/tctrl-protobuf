#pragma once

#include "SchemaNode.h"
#include <tctrl-schema.pb.h>

class ModuleSchema : public ParentSchemaNode<tctrl::schema::ModuleSpec> {
public:
	ModuleSchema(const Spec& spec) : ParentSchemaNode(spec) {};

	const std::string& group() const { return spec().group(); }
};

