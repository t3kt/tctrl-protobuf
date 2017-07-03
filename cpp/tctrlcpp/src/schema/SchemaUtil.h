#pragma once

#include "Common.h"
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/struct.pb.h>
#include <google/protobuf/util/json_util.h>
#include <exception>
#include <string>
#include <unordered_map>

template<typename S>
S readMessageFromJson(const std::string& input) {
	S message;
	google::protobuf::util::JsonParseOptions options;
	options.ignore_unknown_fields = true;
	auto status = google::protobuf::util::JsonStringToMessage(input, &message, options);
	if (!status.ok()) {
		throw std::exception(status.ToString().c_str());
	}
	return message;
}

template<typename T>
std::optional<T> convertOptionalValue(const ::google::protobuf::Value& value);

template<>
std::optional<bool> convertOptionalValue(const ::google::protobuf::Value& value);

template<>
std::optional<double> convertOptionalValue(const ::google::protobuf::Value& value);

template<>
std::optional<int> convertOptionalValue(const ::google::protobuf::Value& value);

template<>
std::optional<std::string> convertOptionalValue(const ::google::protobuf::Value& value);

template<typename S>
std::unordered_map<std::string, const S&> keyedFieldToMap(const ::google::protobuf::RepeatedPtrField<S>& values) {
	std::unordered_map<std::string, const S&> results;
	for (const auto& value : values) {
		results.insert(std::make_pair(value.key(), value));
	}
	return results;
}
