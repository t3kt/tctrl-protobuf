#include "SchemaNode.h"

SchemaGroupInfo::SchemaGroupInfo(const tctrl::schema::GroupInfo& spec) {
	_key = spec.key();
	_label = spec.label();
	if (_label.empty()) {
		_label = _key;
	}
	_tags = StringSet{ spec.tag().begin(), spec.tag().end() };
}

SchemaGroupInfo::SchemaGroupInfo(std::string key) {
	_key = key;
	_label = key;
}

SchemaGroupInfo::SchemaGroupInfo() {
	_key = "<default>";
	_label = "<default>";
	_isDefault = true;
}
