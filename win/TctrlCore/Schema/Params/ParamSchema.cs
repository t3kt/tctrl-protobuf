using System;
using System.Collections.Generic;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params
{
    public abstract class ParamSchema : SchemaNode<ParamSpec>
    {
        protected ParamSchema(ParamSpec spec) : base(spec)
        {
        }

        public override string Key => Spec.Key;
        public override string Label => Spec.Label;
        public override string Path => Spec.Path;

        public ParamType ParamType => Spec.Type;
        public String Style => Spec.Style;
        public String Group => Spec.Group;
        public IList<String> Tags => Spec.Tag;
        public String Help => Spec.Help;
        
        public abstract bool IsVector { get; }
        public bool IsSingle => !IsVector;
    }
}