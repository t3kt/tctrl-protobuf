#pragma once

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/struct.pb.h>
#include <optional>
#include <string>
#include "SchemaNode.h"

StringSet createStringSet(const ::google::protobuf::RepeatedPtrField< ::std::string>& values) {
	StringSet results;
	for (const auto& value : values) {
		results.insert(value);
	}
	return results;
}

template<typename T>
std::optional<T> convertOptionalValue(const ::google::protobuf::Value& value);

template<>
std::optional<bool> convertOptionalValue(const ::google::protobuf::Value& value);
