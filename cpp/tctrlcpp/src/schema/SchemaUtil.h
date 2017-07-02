#pragma once

#include <google/protobuf/struct.pb.h>
#include <optional>
#include <string>

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
