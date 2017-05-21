import google.protobuf.message as proto_msg

import tctrlgen.tctrl_schema_pb2 as pb
from tctrl.messages import ValueToWrapper, ValueToInt32Wrapper, WrapperToValue, WrapperToInt32Value


class _MessageMapping:
    def __init__(
            self, messagetype, *fields,
            postprocmessage=None,
            postprocdict=None):
        self.messagetype = messagetype
        self.postprocmessage = postprocmessage
        self.postprocdict = postprocdict
        self.fields = []
        for field in fields:
            if isinstance(field, str):
                self.fields.append(_FieldMapping(field))
            else:
                self.fields.append(field)

    def DictToMessage(self, obj: dict, message=None):
        message = message or self.messagetype()
        for field in self.fields:
            field.DictToMessage(obj, message)
        if self.postprocmessage:
            self.postprocmessage(message)
        return message

    def MessageToDict(self, message: proto_msg.Message, obj=None):
        if obj is None:
            obj = {}
        for field in self.fields:
            field.MessageToDict(message, obj)
        if self.postprocdict:
            self.postprocdict(obj)
        return obj

class _FieldMapping:
    def __init__(
            self,
            messagekey,
            jsonkey=None,
            defaultval=None,
            islist=False,
            ismessage=False,
            parser=None,
            builder=None):
        self.messagekey = messagekey
        self.jsonkey = jsonkey or messagekey
        self.defaultval = defaultval
        self.islist = islist
        self.ismessage = ismessage
        self.parser = parser
        self.builder = builder
        if ismessage and not parser:
            raise Exception('Sub-message fields must have a parser')

    def DictToMessage(
            self,
            obj: dict,
            message: proto_msg.Message):
        val = obj.get(self.jsonkey, self.defaultval)
        if self.islist:
            message.ClearField(self.messagekey)
            if val is not None and self.parser:
                val = [self.parser(v) for v in val]
            if val:
                getattr(message, self.messagekey).extend(val)
        elif self.ismessage:
            if val is None:
                message.ClearField(self.messagekey)
            else:
                submessage = getattr(message, self.messagekey)
                self.parser(val, message=submessage)
        else:
            if val is not None and self.parser:
                val = self.parser(val)
            if val is None:
                message.ClearField(self.messagekey)
            else:
                setattr(message, self.messagekey, val)

    def MessageToDict(
            self,
            message: proto_msg.Message,
            obj: dict):
        if not message.HasField(self.messagekey):
            if self.jsonkey in obj:
                del obj[self.jsonkey]
        elif self.islist:
            raise NotImplemented()
        else:
            val = getattr(message, self.messagekey)
            if val is not None and self.builder:
                val = self.builder(val)
            if val is not None:
                obj[self.jsonkey] = val
            elif self.jsonkey in obj:
                del obj[self.jsonkey]

def _ValueFieldMapping(
        messagekey,
        jsonkey=None,
        defaultval=None):
    return _FieldMapping(
        messagekey,
        jsonkey=jsonkey,
        defaultval=defaultval,
        ismessage=True,
        parser=ValueToWrapper,
        builder=WrapperToValue,
    )

_messageMappings = {}

def ParseParamOption(obj: dict, message=None):
    return _messageMappings[pb.ParamOption].DictToMessage(obj, message=message)

def ParseOptionList(obj: dict, message=None):
    return _messageMappings[pb.OptionList].DictToMessage(obj, message=message)

def ParseParamPartSpec(obj: dict, message=None):
    return _messageMappings[pb.ParamPartSpec].DictToMessage(obj, message=message)

def ParseParamType(val: str):
    if not val:
        return pb.OTHER
    try:
        return pb.ParamType.Value(val.upper())
    except ValueError:
        return pb.OTHER

def ParseParamSpec(obj: dict, message=None):
    result = _messageMappings[pb.ParamSpec].DictToMessage(obj, message=message)
    if result.type == pb.OTHER and not result.otherType:
        result.otherType = obj['type']
    return result

def ParseModuleTypeSpec(obj: dict, message=None):
    return _messageMappings[pb.ModuleTypeSpec].DictToMessage(obj, message=message)

def ParseModuleSpec(obj: dict, message=None):
    return _messageMappings[pb.ModuleSpec].DictToMessage(obj, message=message)

def ParseConnectionInfo(obj: dict, message=None):
    return _messageMappings[pb.ConnectionInfo].DictToMessage(obj, message=message)

def ParseGroupInfo(obj: dict, message=None):
    return _messageMappings[pb.GroupInfo].DictToMessage(obj, message=message)

def ParseAppSpec(obj: dict, message=None):
    result = _messageMappings[pb.AppSpec].DictToMessage(obj, message=message)
    if not result.path and result.key:
        result.path = '/' + result.key
    return result


def _ParseList(obj, key, parser):
    return [parser(o) for o in (obj.get(key) or [])]

def ParamOptionToObj(spec: pb.ParamOption):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
    })

def OptionListToObj(spec: pb.OptionList):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'options': [ParamOptionToObj(o) for o in spec.option],
    })

def ParamPartSpecToObj(spec: pb.ParamPartSpec):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'path': spec.path,
        'default': WrapperToValue(spec.defaultVal),
        'value': WrapperToValue(spec.value),
        'minLimit': WrapperToValue(spec.minLimit),
        'maxLimit': WrapperToValue(spec.maxLimit),
        'minNorm': WrapperToValue(spec.minNorm),
        'maxNorm': WrapperToValue(spec.maxNorm),
    })

def ParamToObj(spec: pb.ParamSpec):
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
        'default': ValueToWrapper(spec.defaultVal),
        'value': ValueToWrapper(spec.value),
        'valueIndex': WrapperToInt32Value(spec.valueIndex),
        'minLimit': ValueToWrapper(spec.minLimit),
        'maxLimit': ValueToWrapper(spec.maxLimit),
        'minNorm': ValueToWrapper(spec.minNorm),
        'maxNorm': ValueToWrapper(spec.maxNorm),
        'options': [ParamOptionToObj(o) for o in spec.option] if spec.option else None,
        'optionsList': spec.optionListKey,
        'parts': [ParamPartSpecToObj(o) for o in spec.part] if spec.part else None,
    })

def ModuleTypeSpecToObj(spec: pb.ModuleTypeSpec):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'paramGroups': [GroupInfoToObj(o) for o in spec.paramGroup] if spec.paramGroup else None,
        'params': [ParamToObj(o) for o in spec.param] if spec.param else None,
    })

def ModuleSpecToObj(spec: pb.ModuleSpec):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'path': spec.path,
        'moduleType': spec.moduleType,
        'group': spec.group,
        'tags': spec.tag[:] if spec.tag else None,
        'paramGroups': [GroupInfoToObj(o) for o in spec.paramGroup] if spec.paramGroup else None,
        'params': [ParamToObj(o) for o in spec.param] if spec.param else None,
        'childGroups': [GroupInfoToObj(o) for o in spec.childGroup] if spec.childGroup else None,
        'children': [ModuleSpecToObj(o) for o in spec.childModule] if spec.childModule else None,
    })

def ConnectionInfoToObj(spec: pb.ConnectionInfo):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'type': spec.type,
        'host': spec.host,
        'port': spec.port,
    })

def GroupInfoToObj(spec: pb.GroupInfo):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'tags': spec.tag[:] if spec.tag else None,
    })

def AppSpecToObj(spec: pb.AppSpec):
    return _CleanDict({
        'key': spec.key,
        'label': spec.label,
        'path': spec.path,
        'tags': spec.tag[:] if spec.tag else None,
        'description': spec.description,
        'moduleTypes': [ModuleTypeSpecToObj(o) for o in spec.moduleType] if spec.moduleType else None,
        'optionLists': [OptionListToObj(o) for o in spec.optionList] if spec.optionList else None,
        'childGroups': [GroupInfoToObj(o) for o in spec.childGroup] if spec.childGroup else None,
        'children': [ModuleSpecToObj(o) for o in spec.childModule] if spec.childModule else None,
        'connections': [ConnectionInfoToObj(o) for o in spec.connection] if spec.connection else None,
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

_messageMappings[pb.ParamOption] = _MessageMapping(
    pb.ParamOption,
    'key',
    'label',
)

_messageMappings[pb.OptionList] = _MessageMapping(
    pb.OptionList,
    'key',
    'label',
    _FieldMapping(
        'option',
        jsonkey='options',
        islist=True,
        ismessage=True,
        parser=ParseParamOption,
        builder=ParamOptionToObj)
)

_messageMappings[pb.ParamPartSpec] = _MessageMapping(
    pb.ParamPartSpec,
    'key',
    'label',
    'path',
    _ValueFieldMapping('defaultVal', jsonkey='default'),
    _ValueFieldMapping('value'),
    _ValueFieldMapping('minLimit'),
    _ValueFieldMapping('maxLimit'),
    _ValueFieldMapping('minNorm'),
    _ValueFieldMapping('maxNorm'),
)

_messageMappings[pb.ParamSpec] = _MessageMapping(
    pb.ParamSpec,
    'key',
    'label',
    'style',
    'group',
    _FieldMapping('tag', jsonkey='tags', islist=True),
    'help',
    'offHelp',
    'buttonText',
    'buttonOffText',
    _FieldMapping('type', parser=ParseParamType, builder=_TypeToString),
    'otherType',
    _ValueFieldMapping('defaultVal', jsonkey='default'),
    _ValueFieldMapping('value'),
    _FieldMapping('valueIndex', ismessage=True, parser=ValueToInt32Wrapper, builder=WrapperToInt32Value),
    _ValueFieldMapping('minLimit'),
    _ValueFieldMapping('maxLimit'),
    _ValueFieldMapping('minNorm'),
    _ValueFieldMapping('maxNorm'),
    _FieldMapping('option', jsonkey='options', ismessage=True, islist=True, parser=ParseParamOption, builder=ParamOptionToObj),
    _FieldMapping('optionListKey', jsonkey='optionList'),
    _FieldMapping('part', jsonkey='parts', ismessage=True, islist=True, parser=ParseParamPartSpec, builder=ParamPartSpecToObj),
)

_messageMappings[pb.ModuleTypeSpec] = _MessageMapping(
    pb.ModuleTypeSpec,
    'key',
    'label',
    _FieldMapping('paramGroup', jsonkey='paramGroups', ismessage=True, islist=True, parser=ParseGroupInfo, builder=GroupInfoToObj),
    _FieldMapping('param', jsonkey='params', ismessage=True, islist=True, parser=ParseParamOption, builder=ParamOptionToObj),
)

_messageMappings[pb.ModuleSpec] = _MessageMapping(
    pb.ModuleSpec,
    'key',
    'path',
    'label',
    'moduleType',
    'group',
    _FieldMapping('tag', jsonkey='tags', islist=True),
    _FieldMapping('paramGroup', jsonkey='paramGroups', ismessage=True, islist=True, parser=ParseGroupInfo, builder=GroupInfoToObj),
    _FieldMapping('param', jsonkey='params', ismessage=True, islist=True, parser=ParseParamSpec, builder=ParamPartSpecToObj),
    _FieldMapping('childGroup', jsonkey='childGroups', ismessage=True, islist=True, parser=ParseGroupInfo, builder=GroupInfoToObj),
    _FieldMapping('childModule', jsonkey='children', ismessage=True, islist=True, parser=ParseModuleSpec, builder=ModuleSpecToObj),
)

_messageMappings[pb.ConnectionInfo] = _MessageMapping(
    pb.ConnectionInfo,
    'key',
    'label',
    'type',
    'host',
    'port',
)

_messageMappings[pb.GroupInfo] = _MessageMapping(
    pb.GroupInfo,
    'key',
    'label',
    _FieldMapping('tag', jsonkey='tags', islist=True),
)

_messageMappings[pb.AppSpec] = _MessageMapping(
    pb.AppSpec,
    'key',
    'label',
    'description',
    'path',
    _FieldMapping('optionList', jsonkey='optionLists', ismessage=True, islist=True, parser=ParseOptionList, builder=OptionListToObj),
    _FieldMapping('moduleType', jsonkey='moduleTypes', ismessage=True, islist=True, parser=ParseModuleTypeSpec, builder=ModuleTypeSpecToObj),
    _FieldMapping('childGroup', jsonkey='childGroups', ismessage=True, islist=True, parser=ParseGroupInfo, builder=GroupInfoToObj),
    _FieldMapping('childModule', jsonkey='children', ismessage=True, islist=True, parser=ParseModuleSpec, builder=ModuleSpecToObj),
    _FieldMapping('connection', jsonkey='connections', ismessage=True, islist=True, parser=ParseConnectionInfo, builder=ConnectionInfoToObj),
)
