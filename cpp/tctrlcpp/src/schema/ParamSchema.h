#pragma once
#include "SchemaNode.h"
#include "SchemaUtil.h"
#include <iterator>
#include <tctrl-schema.pb.h>
#include <memory>
#include <optional>
#include <vector>

using tctrl::schema::ParamType;

class OptionListProvider;

class ParamSchema : public TypedSchemaNode<tctrl::schema::ParamSpec> {
public:
	std::shared_ptr<ParamSchema> createParamSchema(const Spec& spec, const OptionListProvider* optionListProvider = nullptr);

	ParamSchema(const Spec& spec) : TypedSchemaNode(spec) {
		_key = spec.key();
		_label = spec.label();
		if (_label.empty()) {
			_label = _key;
		}
		_path = spec.path();
	}

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

using ParamSchemaPtr = std::shared_ptr<ParamSchema>;

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

class OptionListProvider {
public:
	virtual bool hasOptionList(const std::string& key) const = 0;
	virtual const ParamOptionList& getOptionList(const std::string& key) const = 0;
};

class MenuParamSchema : public ScalarParamSchema<std::string> {
public:
	MenuParamSchema(const Spec& spec, const OptionListProvider* optionListProvider = nullptr)
		: ScalarParamSchema(spec)
		, _valueIndex(spec.has_valueindex() ? std::optional<int>(spec.valueindex().value()) : std::optional<int>()) {
		if (spec.option_size() > 0) {
			_options = { std::begin(spec.option()), std::end(spec.option()) };
		}
		else if (optionListProvider != nullptr && !spec.optionlistkey().empty() && optionListProvider->hasOptionList(spec.optionlistkey())) {
			_options = optionListProvider->getOptionList(spec.optionlistkey());
		}
	}

	const ParamOptionList& options() const { return _options; }
private:
	ParamOptionList _options;
	std::optional<int> _valueIndex;
};

using StringParamSchema = MenuParamSchema;

template<typename T>
class VectorParamSchema;

template<typename T>
class NumericParamPartSchema : public TypedSchemaNode<tctrl::schema::ParamPartSpec> {
public:
	using ParentSpec = tctrl::schema::ParamSpec;
	using ParentSchema = VectorParamSchema<T>;
	using ValT = T;
	using OptionalVal = std::optional<ValT>;

	NumericParamPartSchema(const Spec& spec, const ParentSchema& parentSchema)
		: TypedSchemaNode(spec)
		, _parentSchema(parentSchema)
		, _value(spec.has_value() ? convertOptionalValue<T>(spec.value()) : std::optional<T>())
		, _defaultValue(spec.has_defaultval() ? convertOptionalValue<T>(spec.defaultval()) : std::optional<T>())
		, _minLimit(spec.has_minlimit() ? convertOptionalValue<T>(spec.minlimit()) : std::optional<T>())
		, _maxLimit(spec.has_maxlimit() ? convertOptionalValue<T>(spec.maxlimit()) : std::optional<T>())
		, _minNorm(spec.has_minnorm() ? convertOptionalValue<T>(spec.minnorm()) : std::optional<T>())
		, _maxNorm(spec.has_maxnorm() ? convertOptionalValue<T>(spec.maxnorm()) : std::optional<T>()) {}

	const ParentSchema& parentSchema() const { return _parentSchema; }
	const ParentSpec& parentSpec() const { return _parentSchema.spec(); }

	const ParamType& type() const { return _parentSchema.type(); }
	const std::string& group() const { return _parentSchema.group(); }

	const OptionalVal& value() const { return _value; }
	const OptionalVal& defaultValue() const { return _defaultValue; }

	const OptionalVal& minLimit() const { return _minLimit; }
	const OptionalVal& maxLimit() const { return _maxLimit; }
	const OptionalVal& minNorm() const { return _minNorm; }
	const OptionalVal& maxNorm() const { return _maxNorm; }
private:
	const ParentSchema& _parentSchema;

	OptionalVal _value;
	OptionalVal _defaultValue;
	OptionalVal _minLimit;
	OptionalVal _maxLimit;
	OptionalVal _minNorm;
	OptionalVal _maxNorm;
};

template<typename T>
using NumericParamPartSchemaPtr = std::shared_ptr<NumericParamPartSchema<T>>;
template<typename T>
using NumericParamPartSchemaList = std::vector<NumericParamPartSchemaPtr<T>>;

template<typename T>
class VectorParamSchema : public ParamSchema {
public:
	using ValT = T;
	using PartSchema = NumericParamPartSchema<T>;
	using PartSchemaPtr = NumericParamPartSchemaPtr<T>;
	using PartSchemaList = NumericParamPartSchemaList<T>;

	VectorParamSchema(const Spec& spec)
		: ParamSchema(spec) {
		for (const auto& partSpec : spec.part()) {
			_parts.push_back(std::make_unique<PartSchema>(partSpec, *this));
		}
	}

	const PartSchemaList& parts() const { return _parts; }

private:
	PartSchemaList _parts;
};

using IntVectorParamSchema = VectorParamSchema<int>;
using FloatVectorParamSchema = VectorParamSchema<double>;
