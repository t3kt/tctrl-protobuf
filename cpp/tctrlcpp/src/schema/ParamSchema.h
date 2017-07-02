#pragma once
#include "SchemaNode.h"
#include "SchemaUtil.h"
#include <iterator>
#include <tctrl-schema.pb.h>
#include <optional>
#include <vector>

using ParamType = tctrl::schema::ParamType;

class ParamSchema : public TypedSchemaNode<tctrl::schema::ParamSpec> {
public:
	using Spec = tctrl::schema::ParamSpec;
	ParamSchema(const Spec& spec) : TypedSchemaNode(spec) {}

	const ParamType& type() const { return spec().type(); }
	const std::string& otherType() const { return spec().othertype(); }
	const std::string& style() const { return spec().style(); }
	const std::string& group() const { return spec().group(); }
	const std::string& help() const { return spec().help(); }

	bool isSingle() const { return _isSingle; }

	bool isVector() const { return !isSingle(); }
protected:

	bool _isSingle = true;
};

template<typename T>
class ScalarParamSchema : public ParamSchema {
public:
	using ValT = T;
	using OptionalVal = std::optional<ValT>;

	ScalarParamSchema(const Spec& spec)
		: ParamSchema(spec)
		, _value(spec.has_value() ? convertOptionalValue<T>(spec.value()) : std::optional<T>())
		, _defaultValue(spec.has_defaultval() ? convertOptionalValue<T>(spec.defaultval()) : std::optional<T>()) {}

	const OptionalVal& value() const { return _value; }
	const OptionalVal& defaultValue() const { return _defaultValue; }

protected:
	OptionalVal _value;
	OptionalVal _defaultValue;
};

class BoolParamSchema : public ScalarParamSchema<bool> {
public:
	BoolParamSchema(const Spec& spec) : ScalarParamSchema(spec) {}

	const std::string& offHelp() const { return spec().offhelp(); }
	const std::string& buttonText() const { return spec().buttontext(); }
	const std::string& buttonOffText() const { return spec().buttonofftext(); }
};

class TriggerParamSchema : public ParamSchema {
public:
	TriggerParamSchema(const Spec& spec) : ParamSchema(spec) {}

	const std::string& buttonText() const { return spec().buttontext(); }
};

template<typename T>
class NumericParamSchema : public ScalarParamSchema<T> {
public:
	NumericParamSchema(const Spec& spec)
		: ScalarParamSchema(spec)
		, _minLimit(spec.has_minlimit() ? convertOptionalValue<T>(spec.minlimit()) : std::optional<T>())
		, _maxLimit(spec.has_maxlimit() ? convertOptionalValue<T>(spec.maxlimit()) : std::optional<T>())
		, _minNorm(spec.has_minnorm() ? convertOptionalValue<T>(spec.minnorm()) : std::optional<T>())
		, _maxNorm(spec.has_maxnorm() ? convertOptionalValue<T>(spec.maxnorm()) : std::optional<T>()) {}

	const OptionalVal& minLimit() const { return _minLimit; }
	const OptionalVal& maxLimit() const { return _maxLimit; }
	const OptionalVal& minNorm() const { return _minNorm; }
	const OptionalVal& maxNorm() const { return _maxNorm; }
private:
	OptionalVal _minLimit;
	OptionalVal _maxLimit;
	OptionalVal _minNorm;
	OptionalVal _maxNorm;
};

using IntParamSchema = NumericParamSchema<int>;
using FloatParamSchema = NumericParamSchema<double>;

using tctrl::schema::ParamOption;
using ParamOptionList = std::vector<ParamOption>;

class MenuParamSchema : public ScalarParamSchema<std::string> {
public:
	MenuParamSchema(const Spec& spec)
		: ScalarParamSchema(spec)
		, _options{ std::begin(spec.option()), std::end(spec.option()) }
		, _valueIndex(spec.has_valueindex() ? std::optional<int>(spec.valueindex().value()) : std::optional<int>()) {}

	const ParamOptionList& options() const { return _options; }
private:
	const ParamOptionList _options;
	std::optional<int> _valueIndex;
};