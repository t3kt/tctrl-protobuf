#pragma once

#include <memory>
#include <string>
#include <vector>

class BaseSchemaNode {
	virtual const std::string& key() const = 0;
	virtual const std::string& label() const = 0;
	virtual const std::string& path() const = 0;
};

template<typename S>
class SchemaNode : public BaseSchemaNode {
public:
	SchemaNode(const S& node) : _node(node) {}

	const S& spec() const { return _node; }

	const std::string& key() const override { return _node.key(); }
	const std::string& label() const override { return _node.label(); }
	const std::path& key() const override { return _node.path(); }
private:
	const S& _node;
};

class ModuleSchema;
using ModuleSchemaPtr = std::shared_ptr<ModuleSchema>;
using ModuleSchemaList = std::vector<ModuleSchemaPtr>;

template<typename S>
class ParentSchemaNode : public SchemaNode<S> {
public:
	ParentSchemaNode(const S& node) : SchemaNode(node) {}

	//TODO: child groups

	ModuleSchemaList& childModules() { return _childModules; }
	const ModuleSchemaList& childModules() const { return _childModules; }

protected:
	ModuleSchemaList _childModules;
};
