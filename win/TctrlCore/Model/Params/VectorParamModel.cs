using System;
using System.Collections.Generic;
using Tctrl.Core.Schema.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model.Params {
    internal class VectorParamModel<TValue, TSchema> : ParamModel<TSchema>, IVectorParamModel<TValue>
        where TSchema : ParamSchema
        where TValue : struct, IComparable<TValue> {

        public VectorParamModel(TSchema schema) : base(schema) { }

        public override bool IsSingle => false;

        public ParamType PartType { get; internal set; }
        public IReadOnlyList<INumericParamModel<TValue>> Parts { get; internal set; }

        IReadOnlyList<INumericParamModel> IVectorParamModel.Parts => Parts;

    }
}