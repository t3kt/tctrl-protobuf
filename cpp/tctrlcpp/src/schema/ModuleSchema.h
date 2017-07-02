#pragma once

#include "SchemaNode.h"
#include <tctrl-schema.pb.h>
#include <google/protobuf/stubs/common.h>

namespace CreateSchema {
	ModuleSchemaList moduleSchemaList(const ::google::protobuf::RepeatedPtrField< ::tctrl::schema::ModuleSpec >& specs);
}

class ModuleSchema : public ParentSchemaNode<tctrl::schema::ModuleSpec> {
public:
	using Spec = tctrl::schema::ModuleSpec;
	ModuleSchema(const Spec& spec);

private:
};

