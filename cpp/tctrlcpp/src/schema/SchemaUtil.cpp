#include "SchemaUtil.h"
#include <google/protobuf/struct.pb.h>
#include <exception>

using namespace google::protobuf;

std::optional<bool> convertOptionalValue(const ::google::protobuf::Value& value) {
	switch (value.kind_case()) {
	case Value::KindCase::kNumberValue:
		return std::make_optional<bool>(value.number_value() != 0.0);
	case Value::KindCase::kBoolValue:
		return std::make_optional<bool>(value.bool_value());
	case Value::KindCase::KIND_NOT_SET:
	case Value::KindCase::kNullValue:
		return std::optional<bool>();
	default:
		throw std::exception("Unsupported value kind");
	}
}
