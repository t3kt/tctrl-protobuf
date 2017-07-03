#pragma once

#include "SchemaNode.h"
#include "ModuleSchema.h"
#include <unordered_map>

using tctrl::schema::ConnectionInfo;

class AppSchema;
using AppSchemaPtr = std::shared_ptr<AppSchema>;

class AppSchema : public ParentSchemaNode<tctrl::schema::AppSpec>
	, public OptionListProvider {
public:
	static AppSchemaPtr createFromJson(const std::string& jsonText);

	AppSchema(const Spec& spec);

	const std::string& description() const { return spec().description(); }

	const ParamOptionList* getOptionList(const std::string& key) const override;
	const ConnectionInfo* getConnectionInfo(const std::string& key) const;

	const ModuleSchemaMap& allModulesByPath() const { return _allModulesByPath; }

	ModuleSchemaPtr getModuleByPath(const std::string& path) { return _allModulesByPath[path]; }
private:
	void registerModules(const ModuleSchemaList& modules);
	void registerModule(ModuleSchemaPtr mod);

	std::unordered_map<std::string, const ParamOptionList&> _optionLists;
	std::unordered_map<std::string, const ConnectionInfo&> _connections;
	ModuleSchemaMap _allModulesByPath;
};
