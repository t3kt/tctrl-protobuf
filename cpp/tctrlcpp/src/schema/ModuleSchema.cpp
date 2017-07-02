#include "ModuleSchema.h"
#include "SchemaUtil.h"

namespace CreateSchema {
	ModuleSchemaList moduleSchemaList(const ::google::protobuf::RepeatedPtrField< ::tctrl::schema::ModuleSpec >& specs) {
		ModuleSchemaList nodes;
		for (const auto& spec : specs) {
			nodes.push_back(std::make_unique<ModuleSchema>(spec));
		}
		return nodes;
	}
}

ModuleSchema::ModuleSchema(const Spec& spec)
: ParentSchemaNode(spec) {
	_childModules = CreateSchema::moduleSchemaList(spec.childmodule());
	_tags = createStringSet(spec.tag());
}
