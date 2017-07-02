#include "ParamSchema.h"
#include "SchemaUtil.h"

ParamSchema::ParamSchema(const Spec& spec)
	: TypedSchemaNode(spec) {
}

BoolParamSchema::BoolParamSchema(const Spec& spec)
	: ScalarParamSchema(spec) {
	_value = convertOptionalValue<bool>(spec.value());
	_defaultValue = convertOptionalValue<bool>(spec.defaultval());
}
