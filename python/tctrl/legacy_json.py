import tctrlgen.tctrl_schema_pb2 as pb
import google.protobuf.struct_pb2 as struct_pb
import google.protobuf.wrappers_pb2 as wrap_pb
import google.protobuf.text_format as text_format

class LegacyJsonParser:

    def parseAppSpec(self, obj):
        raise NotImplemented()

    @staticmethod
    def parseParamOption(obj):
        result = pb.ParamOption()
        result.key = obj['key']
        result.label = obj.get('label')
        return result

    def parseOptionList(self, obj):
        result = pb.OptionList()
        result.key = obj['key']
        result.label = obj.get('label')
        optobjs = obj.get('options')
        if optobjs:
            opts = [self.parseParamOption(o) for o in optobjs]
            result.options.extend(opts)
        return result

    @staticmethod
    def parseParamPartSpec(obj):
        result = pb.ParamPartSpec()
        result.key = obj['key']
        result.label = obj.get('label')
        # TODO
        return result

# def _getValueObj(obj, key):
#     val = obj.get(key)
#     if val is None:
#         return None
#     if isinstance(val, float):
#         pass

# def _getObjects(obj, key, parser):
#     objs = obj.get(key)
#     if not objs:
#         return None


# def _setOptionalProtoField(result, key, val):
#     if val is None:
#         result.ClearField(key)
#     else:
#         setattr(result, key, val)

class LegacyJsonBuilder:
    @staticmethod
    def paramOptionToObj(spec: pb.ParamOption):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
        })

    def optionListToObj(self, spec: pb.OptionList):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'options': [self.paramOptionToObj(o) for o in spec.option],
        })

    def paramPartSpecToObj(self, spec: pb.ParamPartSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'default': self.convertValue(spec.defaultVal),
            'value': self.convertValue(spec.value),
            'minLimit': self.convertValue(spec.minLimit),
            'maxLimit': self.convertValue(spec.maxLimit),
            'minNorm': self.convertValue(spec.minNorm),
            'maxNorm': self.convertValue(spec.maxNorm),
        })

    def paramToObj(self, spec: pb.ParamSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'style': spec.style,
            'group': spec.group,
            'tags': spec.tag[:] if spec.tag else None,
            'help': spec.help,
            'offHelp': spec.offHelp,
            'buttonText': spec.buttonText,
            'buttonOffText': spec.buttonOffText,
            'type': _TypeToString(spec.type),
            'otherType': spec.otherType,
            'default': self.convertValue(spec.defaultVal),
            'value': self.convertValue(spec.value),
            'valueIndex': self.convertInt32Value(spec.valueIndex),
            'minLimit': self.convertValue(spec.minLimit),
            'maxLimit': self.convertValue(spec.maxLimit),
            'minNorm': self.convertValue(spec.minNorm),
            'maxNorm': self.convertValue(spec.maxNorm),
            'options': [self.paramOptionToObj(o) for o in spec.option] if spec.option else None,
            'optionsList': spec.optionListKey,
            'parts': [self.paramPartSpecToObj(o) for o in spec.part] if spec.part else None,
        })

    def moduleTypeSpecToObj(self, spec: pb.ModuleTypeSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'paramGroups': [self.groupInfoToObj(o) for o in spec.paramGroup] if spec.paramGroup else None,
            'params': [self.paramToObj(o) for o in spec.param] if spec.param else None,
        })

    def moduleSpecToObj(self, spec: pb.ModuleSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'moduleType': spec.moduleType,
            'group': spec.group,
            'tags': spec.tag[:] if spec.tag else None,
            'paramGroups': [self.groupInfoToObj(o) for o in spec.paramGroup] if spec.paramGroup else None,
            'params': [self.paramToObj(o) for o in spec.param] if spec.param else None,
            'childGroups': [self.groupInfoToObj(o) for o in spec.childGroup] if spec.childGroup else None,
            'children': [self.moduleSpecToObj(o) for o in spec.childModule] if spec.childModule else None,
        })

    def connectionInfoToObj(self, spec: pb.ConnectionInfo):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'type': spec.type,
            'host': spec.host,
            'port': spec.port,
        })

    def groupInfoToObj(self, spec: pb.GroupInfo):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'tags': spec.tag[:] if spec.tag else None,
        })

    def appSpecToObj(self, spec: pb.AppSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'tags': spec.tag[:] if spec.tag else None,
            'description': spec.description,
            'moduleTypes': [self.moduleTypeSpecToObj(o) for o in spec.moduleType] if spec.moduleType else None,
            'optionLists': [self.optionListToObj(o) for o in spec.optionList] if spec.optionList else None,
            'childGroups': [self.groupInfoToObj(o) for o in spec.childGroup] if spec.childGroup else None,
            'children': [self.moduleSpecToObj(o) for o in spec.childModule] if spec.childModule else None,
            'connections': [self.connectionInfoToObj(o) for o in spec.connection] if spec.connection else None,
        })

    @staticmethod
    def convertValue(value: struct_pb.Value):
        if value is None or value.WhichOneof('kind') is None:
            return None
        if value.HasField('null_value'):
            return None
        if value.HasField('bool_value'):
            return value.bool_value
        if value.HasField('number_value'):
            return value.number_value
        if value.HasField('string_value'):
            return value.string_value
        raise Exception('Unsupported value type: ' + text_format.MessageToString(value))

    @staticmethod
    def convertInt32Value(value: wrap_pb.Int32Value):
        if value is None:
            return None
        return value.value

def _TypeToString(paramtype: pb.ParamType):
    if paramtype is None:
        return 'other'
    return pb.ParamType.Name(paramtype).lower()

def _CleanDict(d):
    if not d:
        return None
    for k in list(d.keys()):
        if d[k] is None or d[k] == '' or (isinstance(d[k], list) and len(d[k]) == 0):
            del d[k]
    return d

def _MergeDicts(*parts):
    if parts is None:
        return {}
    d = {}
    for part in parts:
        if part:
            d.update(part)
    return d
