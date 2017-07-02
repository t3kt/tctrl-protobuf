#pragma once
#include "SchemaNode.h"
#include <tctrl-schema.pb.h>
#include <optional>

using ParamType = tctrl::schema::ParamType;

class ParamSchema : public TypedSchemaNode<tctrl::schema::ParamSpec> {
public:
	using Spec = tctrl::schema::ParamSpec;
	ParamSchema(const Spec& spec);

	const ParamType& type() const { return spec().type(); }

	const std::string& otherType() const { return spec().othertype(); }

	const std::string& style() const { return spec().style(); }

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

	ScalarParamSchema(const Spec& spec) : ParamSchema(spec) {}

	const OptionalVal& value() const { return _value; }
	const OptionalVal& defaultValue() const { return _defaultValue; }

protected:
	OptionalVal _value;
	OptionalVal _defaultValue;
};

class BoolParamSchema : public ScalarParamSchema<bool> {
public:
	BoolParamSchema(const Spec& spec);

	const std::string& offHelp() const { return spec().offhelp(); }
	const std::string& buttonText() const { return spec().buttontext(); }
	const std::string& buttonOffText() const { return spec().buttonofftext(); }
private:
};
