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
}