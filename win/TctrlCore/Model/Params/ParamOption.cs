namespace Tctrl.Core.Model.Params {
    public sealed class ParamOption {
        public string Key { get; }
        public string Label { get; }
        public int Index { get; }

        internal ParamOption(string key, string label, int index) {
            Key = key;
            Label = label;
            Index = index;
        }
    }
}