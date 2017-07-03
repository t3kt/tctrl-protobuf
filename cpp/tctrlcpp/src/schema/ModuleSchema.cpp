#include "ModuleSchema.h"

ModuleSchema::ModuleSchema(const Spec& spec, const OptionListProvider* optionListProvider)
	: ParentSchemaNode(spec, optionListProvider) {

	addChildModules(spec, optionListProvider);

	ParamGroupCollection::Builder paramGroupBuilder;
	paramGroupBuilder.addDeclaredGroups(spec.paramgroup());

	for (const auto& paramSpec : spec.param()) {
		auto param = ParamSchema::createParamSchema(paramSpec, optionListProvider);
		_params.push_back(param);
		paramGroupBuilder.addNode(param);
	}
	_paramGroups = paramGroupBuilder.build();
}
