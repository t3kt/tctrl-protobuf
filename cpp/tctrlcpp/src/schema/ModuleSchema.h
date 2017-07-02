#pragma once

#include "SchemaNode.h"
#include "ParamSchema.h"
#include <tctrl-schema.pb.h>

using ParamGroupCollection = SchemaNodeGroupCollection<ParamSchema>;

class ModuleSchema : public ParentSchemaNode<tctrl::schema::ModuleSpec> {
public:
	ModuleSchema(const Spec& spec, const OptionListProvider* optionListProvider = nullptr);

	const std::string& group() const { return spec().group(); }

	const ParamSchemaList& params() const { return _params; }
	const ParamGroupCollection& paramGroups() const { return _paramGroups; }
private:
	ParamSchemaList _params;
	ParamGroupCollection _paramGroups;
};

