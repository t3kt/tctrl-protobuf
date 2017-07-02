#pragma once

#include <memory>
#include <string>
#include <unordered_set>
#include <vector>

using StringSet = std::unordered_set<std::string>;

class SchemaNode {
public:
	virtual const std::string& key() const = 0;
	virtual const std::string& label() const = 0;
	virtual const std::string& path() const = 0;

	const StringSet& tags() const { return _tags; }
	bool hasTag(const std::string& tag) const {
		return _tags.find(tag) != _tags.end();
	}

protected:
	StringSet _tags;
};

template<typename S>
class TypedSchemaNode : public SchemaNode {
public:
	TypedSchemaNode(const S& node) : _node(node) {}

	const S& spec() const { return _node; }

	const std::string& key() const override { return _node.key(); }
	const std::string& label() const override { return _node.label(); }
	const std::string& path() const override { return _node.path(); }

private:
	const S& _node;
};

class ModuleSchema;
using ModuleSchemaPtr = std::shared_ptr<ModuleSchema>;
using ModuleSchemaList = std::vector<ModuleSchemaPtr>;

template<typename S>
class ParentSchemaNode : public TypedSchemaNode<S> {
public:
	ParentSchemaNode(const S& node) : TypedSchemaNode(node) {}

	ModuleSchemaList& childModules() { return _childModules; }
	const ModuleSchemaList& childModules() const { return _childModules; }

protected:
	ModuleSchemaList _childModules;
};
