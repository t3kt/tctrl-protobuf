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
            val = list(getattr(message, self.messagekey))
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
        return _CleanDict(obj)

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

def ParseAppSpec(obj: dict, message=None):
    return ParseDict(obj, pb.AppSpec, message=message)

def MessageToDict(message: proto_msg.Message):
    mapping = _MessageMapping.GetMapping(type(message))
    obj = mapping.MessageToDict(message)
    return _CleanDict(obj)

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
            messagetype=pb.ParamOption),
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

# noinspection PyUnusedLocal
def _FixParamType(message: pb.ParamSpec, obj: dict, **kwargs):
    if message.type == pb.OTHER and not message.otherType:
        message.otherType = obj['type']

_MessageMapping.Register(_MessageMapping(
    pb.ParamSpec,
    fields=[
        'key',
        'path',
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
        _FieldMapping('option', jsonkey='options', islist=True, messagetype=pb.ParamOption),
        _FieldMapping('optionListKey', jsonkey='optionList'),
        _FieldMapping('part', jsonkey='parts', islist=True, messagetype=pb.ParamPartSpec),
    ],
    postprocmessage=_FixParamType,
))

_MessageMapping.Register(_MessageMapping(
    pb.ModuleTypeSpec,
    fields=[
        'key',
        'label',
        _FieldMapping('paramGroup', jsonkey='paramGroups', islist=True, messagetype=pb.GroupInfo),
        _FieldMapping('param', jsonkey='params', islist=True, messagetype=pb.ParamSpec),
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
        _FieldMapping('paramGroup', jsonkey='paramGroups', islist=True, messagetype=pb.GroupInfo),
        _FieldMapping('param', jsonkey='params', islist=True, messagetype=pb.ParamSpec),
        _FieldMapping('childGroup', jsonkey='childGroups', islist=True, messagetype=pb.GroupInfo),
        _FieldMapping('childModule', jsonkey='children', islist=True, messagetype=pb.ModuleSpec),
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

# noinspection PyUnusedLocal
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
        _FieldMapping('optionList', jsonkey='optionLists', islist=True, messagetype=pb.OptionList),
        _FieldMapping('moduleType', jsonkey='moduleTypes', islist=True, messagetype=pb.ModuleTypeSpec),
        _FieldMapping('childGroup', jsonkey='childGroups', islist=True, messagetype=pb.GroupInfo),
        _FieldMapping('childModule', jsonkey='children', islist=True, messagetype=pb.ModuleSpec),
        _FieldMapping('connection', jsonkey='connections', islist=True, messagetype=pb.ConnectionInfo),
    ],
    postprocmessage=_AddMissingAppSpecPath,
))
