#include "ParamSchema.h"

ParamSchemaPtr ParamSchema::createParamSchema(const Spec& spec, const OptionListProvider* optionListProvider) {
	switch (spec.type()) {
	case ParamType::BOOL:
		return std::make_shared<BoolParamSchema>(spec);
	case ParamType::FLOAT:
		return std::make_shared<FloatParamSchema>(spec);
	case ParamType::INT:
		return std::make_shared<IntParamSchema>(spec);
	case ParamType::FVEC:
		return std::make_shared<FloatVectorParamSchema>(spec);
	case ParamType::IVEC:
		return std::make_shared<IntVectorParamSchema>(spec);
	case ParamType::TRIGGER:
		return std::make_shared<TriggerParamSchema>(spec);
	case ParamType::STRING:
		return std::make_shared<StringParamSchema>(spec, optionListProvider);
	case ParamType::MENU:
		return std::make_shared<MenuParamSchema>(spec, optionListProvider);
	case ParamType::OTHER:
	default:
		return std::make_shared<ParamSchema>(spec);
	}
}
