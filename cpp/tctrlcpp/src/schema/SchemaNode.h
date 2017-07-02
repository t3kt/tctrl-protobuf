#pragma once

#include <memory>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <utility>
#include <vector>
#include <tctrl-schema.pb.h>
#include <exception>

using StringSet = std::unordered_set<std::string>;

class SchemaNode {
public:
	const std::string& key() const { return _key; }
	const std::string& label() const { return _label; }
	const std::string& path() const { return _path; }

	const StringSet& tags() const { return _tags; }
	bool hasTag(const std::string& tag) const {
		return _tags.find(tag) != _tags.end();
	}

protected:
	std::string _key;
	std::string _label;
	std::string _path;
	StringSet _tags;
};

template<typename S>
class TypedSchemaNode : public SchemaNode {
public:
	using Spec = S;

	TypedSchemaNode(const Spec& spec) : _spec(spec) {}

	const Spec& spec() const { return _spec; }

private:
	const Spec& _spec;
};

class SchemaGroupInfo : public SchemaNode {
public:
	SchemaGroupInfo(const tctrl::schema::GroupInfo& spec);
	SchemaGroupInfo(const SchemaGroupInfo& group) = default;
	SchemaGroupInfo(std::string key);
	SchemaGroupInfo();

	bool isDefault() const { return _isDefault; }
private:
	bool _isDefault;
};

template<typename N>
class SchemaNodeGroup : public SchemaGroupInfo {
public:
	using NodePtr = std::shared_ptr<N>;
	using NodeList = std::vector<NodePtr>;

	SchemaNodeGroup(const SchemaGroupInfo& group)
		: SchemaGroupInfo(group) {}


	SchemaNodeGroup(std::string key) : SchemaGroupInfo(key) {}
	SchemaNodeGroup(const tctrl::schema::GroupInfo& spec) : SchemaGroupInfo(spec) {}
	SchemaNodeGroup() : SchemaGroupInfo() {}

	const NodeList& nodes() const { return _nodes; }

	void addNode(NodePtr node) {
		_nodes.push_back(node);
	}
private:
	NodeList _nodes;
};

template<typename N>
using SchemaNodeGroupPtr = std::shared_ptr<SchemaNodeGroup<N>>;
template<typename N>
using SchemaNodeGroupList = std::vector<SchemaNodeGroupPtr<N>>;
template<typename N>
using SchemaNodeGroupMap = std::unordered_map<std::string, SchemaNodeGroupPtr<N>>;

template<typename N>
class SchemaNodeGroupCollection {
public:
	using NodeGroup = SchemaNodeGroup<N>;
	using NodeGroupPtr = SchemaNodeGroupPtr<N>;
	using NodeGroupList = SchemaNodeGroupList<N>;
	using NodeGroupMap = SchemaNodeGroupMap<N>;

	using NodePtr = typename NodeGroup::NodePtr;
	using NodeList = typename NodeGroup::NodeList;

	SchemaNodeGroupCollection() {}

	SchemaNodeGroupCollection(NodeGroupList groupList, NodeGroupMap groupMap)
		: _groupList(groupList)
		, _groupMap(groupMap) {}

	const NodeGroupList& groups() const { return _groupList; }

	bool containsGroup(const std::string& key) { return _groupMap.find(key) != _groupMap.end(); }

	const NodeGroupPtr& getGroup(const std::string& key) {
		return _groupMap[key];
	}

	class Builder {
	public:
		void addDeclaredGroups(const ::google::protobuf::RepeatedPtrField< ::tctrl::schema::GroupInfo>& groups) {
			for (const auto& groupInfo : groups) {
				if (_groupMap.find(groupInfo.key()) != _groupMap.end()) {
					throw std::exception(std::string("Duplicate group: " + groupInfo.key()).c_str());
				}
				auto group = std::make_shared<NodeGroup>(groupInfo);
				_groupList.push_back(group);
				_groupMap.insert(std::make_pair(groupInfo.key(), group));
			}
		}

		void addNode(NodePtr node) {
			const auto& groupKey = node->group();
			if (groupKey.empty()) {
				if (!_defaultGroup) {
					_defaultGroup = std::make_shared<NodeGroup>();
					if (_groupList.empty()) {
						_groupList.push_back(_defaultGroup);
					}
					else {
						_groupList.insert(_groupList.begin(), _defaultGroup);
					}
					_groupMap[""] = _defaultGroup;
				}
				_defaultGroup->addNode(node);
			}
			else if (_groupMap.find(groupKey) == _groupMap.end()) {
				auto group = std::make_shared<NodeGroup>(groupKey);
				_groupList.push_back(group);
				_groupMap.insert(std::make_pair(groupKey, group));
				group->addNode(node);
			}
			else {
				_groupMap[groupKey]->addNode(node);
			}
		}

		SchemaNodeGroupCollection<N> build() {
			return SchemaNodeGroupCollection<N>(_groupList, _groupMap);
		}
	private:
		NodeGroupList _groupList;
		NodeGroupMap _groupMap;
		NodeGroupPtr _defaultGroup;
	};

private:
	NodeGroupList _groupList;
	NodeGroupMap _groupMap;
};

class ModuleSchema;
using ModuleSchemaPtr = std::shared_ptr<ModuleSchema>;
using ModuleSchemaList = std::vector<ModuleSchemaPtr>;
using ModuleGroupCollection = SchemaNodeGroupCollection<ModuleSchema>;

class OptionListProvider;

template<typename S>
class ParentSchemaNode : public TypedSchemaNode<S> {
public:
	ParentSchemaNode(const Spec& spec, const OptionListProvider* optionListProvider = nullptr) : TypedSchemaNode(spec) {
		_key = spec.key();
		_label = spec.label();
		_path = spec.path();
		_tags = StringSet{ spec.tag().begin(), spec.tag().end() };

		ModuleGroupCollection::Builder childGroupBuilder;
		childGroupBuilder.addDeclaredGroups(spec.childgroup());

		for (const auto& childSpec : spec.childmodule()) {
			auto childModule = std::make_shared<ModuleSchema>(childSpec, optionListProvider);
			_childModules.push_back(childModule);
			childGroupBuilder.addNode(childModule);
		}
		_childModuleGroups = childGroupBuilder.build();
	}

	const ModuleSchemaList& childModules() const { return _childModules; }
	const ModuleGroupCollection& childModuleGroups() const { return _childModuleGroups; }

protected:
	ModuleSchemaList _childModules;
	ModuleGroupCollection _childModuleGroups;
};
