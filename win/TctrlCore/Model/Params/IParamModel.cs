using System;
using System.Collections.Generic;
using Tctrl.Core.Common;
using Tctrl.Schema;

namespace Tctrl.Core.Model.Params {
    public interface IParamModel : IModelNode {

        ParamType ParamType { get; }

        bool TryResetValue();
        bool CanResetValue { get; }

        bool IsSingle { get; }
        bool IsVector { get; }
        bool IsUnsupported { get; }

        ModuleModel ParentModule { get; }

    }

    public interface IUnsupportedParamModel : IParamModel {

        string OtherType { get; }

    }

    public interface IVectorParamModel : IParamModel {

        ParamType PartType { get; }
        IReadOnlyList<INumericParamModel> Parts { get; }

    }

    public interface IVectorParamModel<TValue> : IVectorParamModel
        where TValue : struct, IComparable<TValue> {

        new IReadOnlyList<INumericParamModel<TValue>> Parts { get; }

    }

    public interface ISingleParamModel : IParamModel {

        IVectorParamModel ParentParam { get; }

    }

    public interface ITriggerParamModel : IParamModel {

        bool CanTrigger { get; }
        bool TryTrigger();

    }

    public interface IScalarParamModel<TValue> : ISingleParamModel
        where TValue : struct {

        TValue? Value { get; }

        bool TrySetValue(TValue value);

        bool IsValidValue(TValue value);

        TValue? DefaultValue { get; }

        bool CanSetValue { get; }

    }

    public interface INumericParamModel : ISingleParamModel {

        double? NormalizedValue { get; }

        bool TrySetNormalizedValue(double value);

    }

    public interface INumericParamModel<TValue> : IScalarParamModel<TValue>, INumericParamModel
        where TValue : struct, IComparable<TValue> {

        ValueRange<TValue> NormalizedRange { get; }
        ValueRange<TValue> LimitRange { get; }

    }

    public interface IMenuParamModel : ISingleParamModel {

        // TODO: stuff

    }
}