#include "AppSchema.h"
#include "Common.h"
#include "SchemaUtil.h"

AppSchemaPtr AppSchema::createFromJson(const std::string& jsonText) {
	auto appSpec = readMessageFromJson<Spec>(jsonText);

	return std::make_shared<AppSchema>(appSpec);
}

AppSchema::AppSchema(const Spec& spec)
	: ParentSchemaNode(spec) {
	_optionLists = keyedFieldToMap(spec.optionlist());
	_connections = keyedFieldToMap(spec.connection());

	addChildModules(spec, this);

	registerModules(childModules());
}

void AppSchema::registerModules(const ModuleSchemaList& modules) {
	for (const auto& mod : modules) {
		registerModule(mod);
	}
}

void AppSchema::registerModule(ModuleSchemaPtr mod) {
	const auto& path = mod->path();
	if (!path.empty()) {
		_allModulesByPath.insert(std::make_pair(path, mod));
	}
	registerModules(mod->childModules());
}

const ParamOptionList* AppSchema::getOptionList(const std::string& key) const {
	return tryGetFromMap(_optionLists, key);
}

const ConnectionInfo* AppSchema::getConnectionInfo(const std::string& key) const {
	return tryGetFromMap(_connections, key);
}
