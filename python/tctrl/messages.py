from google.protobuf import struct_pb2 as struct_pb, wrappers_pb2 as wrap_pb, text_format as text_format

from tctrlgen import tctrl_schema_pb2 as pb


def CreateParamOption(
        key,
        label=None):
    result = pb.ParamOption()
    result.key = key
    if label:
        result.label = label
    return result


def ValueToWrapper(rawval, message=None):
    result = message or struct_pb.Value()
    if rawval is None:
        result.null_value = struct_pb.NULL_VALUE
    elif isinstance(rawval, bool):
        result.bool_value = rawval
    elif isinstance(rawval, (int, float)):
        result.number_value = rawval
    elif isinstance(rawval, str):
        result.string_value = rawval
    else:
        raise Exception('Unsupported value type: %r' % rawval)
    return result


def ValueToInt32Wrapper(rawval, message=None):
    result = message or wrap_pb.Int32Value()
    result.value = rawval
    return result


def WrapperToValue(value: struct_pb.Value):
    if value is None or value.WhichOneof('kind') is None or value.HasField('null_value'):
        return None
    if value.HasField('bool_value'):
        return value.bool_value
    if value.HasField('number_value'):
        return value.number_value
    if value.HasField('string_value'):
        return value.string_value
    raise Exception('Unsupported value type: ' + text_format.MessageToString(value))


def WrapperToInt32Value(value: wrap_pb.Int32Value):
    if value is None:
        return None
    return value.value
