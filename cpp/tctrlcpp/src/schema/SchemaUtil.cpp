#include "SchemaUtil.h"
#include <google/protobuf/struct.pb.h>
#include <exception>
#include <math.h>

using namespace google::protobuf;

template<>
std::optional<bool> convertOptionalValue(const ::google::protobuf::Value& value) {
	switch (value.kind_case()) {
	case Value::KindCase::kNumberValue:
		return std::optional<bool>(value.number_value() != 0.0);
	case Value::KindCase::kBoolValue:
		return std::optional<bool>(value.bool_value());
	case Value::KindCase::KIND_NOT_SET:
	case Value::KindCase::kNullValue:
		return std::optional<bool>();
	default:
		throw std::exception("Unsupported value kind");
	}
}

template<>
std::optional<double> convertOptionalValue(const ::google::protobuf::Value& value) {
	switch (value.kind_case()) {
	case Value::KindCase::kNumberValue:
		return std::optional<double>(value.number_value());
	case Value::KindCase::kBoolValue:
		return std::optional<double>(value.bool_value() ? 1.0 : 0.0);
	case Value::KindCase::KIND_NOT_SET:
	case Value::KindCase::kNullValue:
		return std::optional<double>();
	default:
		throw std::exception("Unsupported value kind");
	}
}

template<>
std::optional<int> convertOptionalValue(const ::google::protobuf::Value& value) {
	switch (value.kind_case()) {
	case Value::KindCase::kNumberValue:
		return std::optional<int>(static_cast<int>(std::lround(value.number_value())));
	case Value::KindCase::kBoolValue:
		return std::optional<int>(value.bool_value() ? 1 : 0);
	case Value::KindCase::KIND_NOT_SET:
	case Value::KindCase::kNullValue:
		return std::optional<int>();
	default:
		throw std::exception("Unsupported value kind");
	}
}

template<>
std::optional<std::string> convertOptionalValue(const ::google::protobuf::Value& value) {
	switch (value.kind_case()) {
	case Value::KindCase::kNumberValue:
		return std::optional<std::string>(std::to_string(value.number_value()));
	case Value::KindCase::kBoolValue:
		return std::optional<std::string>(std::to_string(value.bool_value()));
	case Value::KindCase::kStringValue:
		return std::optional<std::string>(value.string_value());
	case Value::KindCase::KIND_NOT_SET:
	case Value::KindCase::kNullValue:
		return std::optional<std::string>();
	default:
		throw std::exception("Unsupported value kind");
	}
}
