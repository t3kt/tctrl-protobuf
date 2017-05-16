import tctrlgen.tctrl_schema_pb2 as pb

class LegacyJsonParser:

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
            'default': spec.defaultVal,
            'value': spec.value,
            'minLimit': spec.minLimit,
            'maxLimit': spec.maxLimit,
            'minNorm': spec.minNorm,
            'maxNorm': spec.maxNorm,
        })

    def paramToObj(self, spec: pb.ParamSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'style': spec.style,
            'group': spec.group,
            'tags': spec.tag,
            'help': spec.help,
            'offHelp': spec.offHelp,
            'buttonText': spec.buttonText,
            'buttonOffText': spec.buttonOffText,
            'type': _TypeToString(spec.type),
            'otherType': spec.otherType,
            'default': spec.defaultVal,
            'value': spec.value,
            'valueIndex': spec.valueIndex,
            'minLimit': spec.minLimit,
            'maxLimit': spec.maxLimit,
            'minNorm': spec.minNorm,
            'maxNorm': spec.maxNorm,
            'options': [self.paramOptionToObj(o) for o in spec.options] if spec.options else None,
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
            'tags': spec.tag,
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
            'tags': spec.tag,
        })

    def appSpecToObj(self, spec: pb.AppSpec):
        return _CleanDict({
            'key': spec.key,
            'label': spec.label,
            'path': spec.path,
            'tags': spec.tag,
            'description': spec.description,
            'moduleTypes': [self.moduleTypeSpecToObj(o) for o in spec.moduleType] if spec.moduleType else None,
            'optionLists': [self.optionListToObj(o) for o in spec.optionList] if spec.optionList else None,
            'childGroups': [self.groupInfoToObj(o) for o in spec.childGroup] if spec.childGroup else None,
            'children': [self.moduleSpecToObj(o) for o in spec.childModule] if spec.childModule else None,
            'connections': [self.connectionInfoToObj(o) for o in spec.connection] if spec.connection else None,
        })

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
