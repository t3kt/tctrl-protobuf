import google.protobuf.message as proto_msg

import tctrlgen.tctrl_schema_pb2 as pb
from tctrl.messages import ValueToWrapper, ValueToInt32Wrapper, WrapperToValue, WrapperToInt32Value


class _MessageMapping:
    _registered = {}

    @staticmethod
    def Register(mapping):
        _MessageMapping._registered[mapping.messagetype] = mapping

    @staticmethod
    def GetMapping(messagetype) -> '_MessageMapping':
        return _MessageMapping._registered[messagetype]

    def __init__(
            self, messagetype,
            fields=None,
            postprocmessage=None,
            postprocdict=None):
        self.messagetype = messagetype
        self.postprocmessage = postprocmessage
        self.postprocdict = postprocdict
        self.fields = []
        if fields:
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
            self.postprocmessage(message, obj=obj)
        return message

    def MessageToDict(self, message: proto_msg.Message, obj=None):
        if obj is None:
            obj = {}
        for field in self.fields:
            field.MessageToDict(message, obj)
        if self.postprocdict:
            self.postprocdict(obj, message=message)
        return obj

class _FieldMapping:
    def __init__(
            self,
            messagekey,
            jsonkey=None,
            defaultval=None,
            islist=False,
            ismessage=False,
            messagetype=None,
            parser=None,
            builder=None):
        self.messagekey = messagekey
        self.jsonkey = jsonkey or messagekey
        self.defaultval = defaultval
        self.islist = islist
        self.messagetype = messagetype
        self.ismessage = bool(ismessage or messagetype)
        self.parser = parser
        self.builder = builder
        if messagetype and not parser:
            def _messageParser(val, message=None):
                return _MessageMapping.GetMapping(messagetype).DictToMessage(val, message=message)
            self.parser = _messageParser
        if messagetype and not builder:
            def _messageBuilder(message, obj=None):
                return _MessageMapping.GetMapping(messagetype).MessageToDict(message, obj=obj)
            self.builder = _messageBuilder

    def DictToMessage(
            self,
            obj: dict,
            message: proto_msg.Message):
        val = obj.get(self.jsonkey, self.defaultval)
        if self.islist:
            message.ClearField(self.messagekey)
            if not val:
                return
            if self.parser:
                val = [self.parser(v) for v in val]
            getattr(message, self.messagekey).extend(val)
        elif self.ismessage:
            if val is None:
                message.ClearField(self.messagekey)
            else:
                submessage = getattr(message, self.messagekey)
                if self.parser:
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
        if self.jsonkey in obj:
            del obj[self.jsonkey]
        if self.islist:
            val = getattr(message, self.messagekey)
            if self.builder:
                val = [self.builder(v) for v in val]
            obj[self.jsonkey] = val
        elif self.ismessage:
            if message.HasField(self.messagekey):
                val = getattr(message, self.messagekey)
                if self.builder:
                    val = self.builder(val)
                    obj[self.jsonkey] = val
        else:
            val = getattr(message, self.messagekey)
            if val is not None and self.builder:
                val = self.builder(val)
            if val is not None:
                obj[self.jsonkey] = val
        return obj

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

def ParseDict(obj: dict, messagetype: type, message=None):
    return _MessageMapping.GetMapping(messagetype).DictToMessage(
        obj, message=message)

def ParseParamType(val: str):
    if not val:
        return pb.OTHER
    try:
        return pb.ParamType.Value(val.upper())
    except ValueError:
        return pb.OTHER

def _FixParamType(message: pb.ParamSpec, obj: dict, **kwargs):
    if message.type == pb.OTHER and not message.otherType:
        message.otherType = obj['type']

def ParseAppSpec(obj: dict, message=None):
    return ParseDict(obj, pb.AppSpec, message=message)

def MessageToDict(message: proto_msg.Message, obj: dict=None):
    mapping = _MessageMapping.GetMapping(type(message))
    return mapping.MessageToDict(message, obj=obj)

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
        'default': WrapperToValue(spec.defaultVal),
        'value': WrapperToValue(spec.value),
        'valueIndex': WrapperToInt32Value(spec.valueIndex),
        'minLimit': WrapperToValue(spec.minLimit),
        'maxLimit': WrapperToValue(spec.maxLimit),
        'minNorm': WrapperToValue(spec.minNorm),
        'maxNorm': WrapperToValue(spec.maxNorm),
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

_MessageMapping.Register(_MessageMapping(
    pb.ParamOption,
    fields=[
        'key',
        'label',
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.OptionList,
    fields=[
        'key',
        'label',
        _FieldMapping(
            'option',
            jsonkey='options',
            islist=True,
            messagetype=pb.ParamOption,
            builder=ParamOptionToObj),
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.ParamPartSpec,
    fields=[
        'key',
        'label',
        'path',
        _ValueFieldMapping('defaultVal', jsonkey='default'),
        _ValueFieldMapping('value'),
        _ValueFieldMapping('minLimit'),
        _ValueFieldMapping('maxLimit'),
        _ValueFieldMapping('minNorm'),
        _ValueFieldMapping('maxNorm'),
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.ParamSpec,
    fields=[
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
        _FieldMapping(
            'valueIndex',
            ismessage=True,
            parser=ValueToInt32Wrapper,
            builder=WrapperToInt32Value),
        _ValueFieldMapping('minLimit'),
        _ValueFieldMapping('maxLimit'),
        _ValueFieldMapping('minNorm'),
        _ValueFieldMapping('maxNorm'),
        _FieldMapping('option', jsonkey='options', islist=True, messagetype=pb.ParamOption, builder=ParamOptionToObj),
        _FieldMapping('optionListKey', jsonkey='optionList'),
        _FieldMapping('part', jsonkey='parts', islist=True, messagetype=pb.ParamPartSpec, builder=ParamPartSpecToObj),
    ],
    postprocmessage=_FixParamType,
))

_MessageMapping.Register(_MessageMapping(
    pb.ModuleTypeSpec,
    fields=[
        'key',
        'label',
        _FieldMapping('paramGroup', jsonkey='paramGroups', islist=True, messagetype=pb.GroupInfo, builder=GroupInfoToObj),
        _FieldMapping('param', jsonkey='params', islist=True, messagetype=pb.ParamSpec, builder=ParamPartSpecToObj),
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.ModuleSpec,
    fields=[
        'key',
        'path',
        'label',
        'moduleType',
        'group',
        _FieldMapping('tag', jsonkey='tags', islist=True),
        _FieldMapping('paramGroup', jsonkey='paramGroups', islist=True, messagetype=pb.GroupInfo, builder=GroupInfoToObj),
        _FieldMapping('param', jsonkey='params', islist=True, messagetype=pb.ParamSpec, builder=ParamPartSpecToObj),
        _FieldMapping('childGroup', jsonkey='childGroups', islist=True, messagetype=pb.GroupInfo, builder=GroupInfoToObj),
        _FieldMapping('childModule', jsonkey='children', islist=True, messagetype=pb.ModuleSpec, builder=ModuleSpecToObj),
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.ConnectionInfo,
    fields=[
        'key',
        'label',
        'type',
        'host',
        'port',
    ],
))

_MessageMapping.Register(_MessageMapping(
    pb.GroupInfo,
    fields=[
        'key',
        'label',
        _FieldMapping('tag', jsonkey='tags', islist=True),
    ],
))

def _AddMissingAppSpecPath(message: pb.AppSpec, **kwargs):
    if not message.path and message.key:
        message.path = '/' + message.key
    return message

_MessageMapping.Register(_MessageMapping(
    pb.AppSpec,
    fields=[
        'key',
        'label',
        'description',
        'path',
        _FieldMapping('optionList', jsonkey='optionLists', islist=True, messagetype=pb.OptionList, builder=OptionListToObj),
        _FieldMapping('moduleType', jsonkey='moduleTypes', islist=True, messagetype=pb.ModuleTypeSpec, builder=ModuleTypeSpecToObj),
        _FieldMapping('childGroup', jsonkey='childGroups', islist=True, messagetype=pb.GroupInfo, builder=GroupInfoToObj),
        _FieldMapping('childModule', jsonkey='children', islist=True, messagetype=pb.ModuleSpec, builder=ModuleSpecToObj),
        _FieldMapping('connection', jsonkey='connections', islist=True, messagetype=pb.ConnectionInfo, builder=ConnectionInfoToObj),
    ],
    postprocmessage=_AddMissingAppSpecPath,
))
