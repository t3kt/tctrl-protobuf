# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: tctrl-state.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import struct_pb2 as google_dot_protobuf_dot_struct__pb2
from google.protobuf import wrappers_pb2 as google_dot_protobuf_dot_wrappers__pb2


DESCRIPTOR = _descriptor.FileDescriptor(
  name='tctrl-state.proto',
  package='tctrl.state',
  syntax='proto3',
  serialized_pb=_b('\n\x11tctrl-state.proto\x12\x0btctrl.state\x1a\x1cgoogle/protobuf/struct.proto\x1a\x1egoogle/protobuf/wrappers.proto\"q\n\nParamState\x12\x0b\n\x03key\x18\x01 \x01(\t\x12%\n\x05value\x18\x02 \x01(\x0b\x32\x16.google.protobuf.Value\x12/\n\nvalueIndex\x18\x03 \x01(\x0b\x32\x1b.google.protobuf.Int32Value\"\x7f\n\x0bModuleState\x12\x0b\n\x03key\x18\x01 \x01(\t\x12\x0c\n\x04path\x18\x02 \x01(\t\x12&\n\x05param\x18\x03 \x03(\x0b\x32\x17.tctrl.state.ParamState\x12-\n\x0b\x63hildModule\x18\x04 \x03(\x0b\x32\x18.tctrl.state.ModuleState\"P\n\x12ModuleStatePackage\x12\x10\n\x08\x62\x61sePath\x18\x01 \x01(\t\x12(\n\x06module\x18\x02 \x03(\x0b\x32\x18.tctrl.state.ModuleStateB\'\n\x14net.t3kt.tctrl.stateB\x0fTctrlStateProtob\x06proto3')
  ,
  dependencies=[google_dot_protobuf_dot_struct__pb2.DESCRIPTOR,google_dot_protobuf_dot_wrappers__pb2.DESCRIPTOR,])
_sym_db.RegisterFileDescriptor(DESCRIPTOR)




_PARAMSTATE = _descriptor.Descriptor(
  name='ParamState',
  full_name='tctrl.state.ParamState',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='key', full_name='tctrl.state.ParamState.key', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='value', full_name='tctrl.state.ParamState.value', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='valueIndex', full_name='tctrl.state.ParamState.valueIndex', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=96,
  serialized_end=209,
)


_MODULESTATE = _descriptor.Descriptor(
  name='ModuleState',
  full_name='tctrl.state.ModuleState',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='key', full_name='tctrl.state.ModuleState.key', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='path', full_name='tctrl.state.ModuleState.path', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='param', full_name='tctrl.state.ModuleState.param', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='childModule', full_name='tctrl.state.ModuleState.childModule', index=3,
      number=4, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=211,
  serialized_end=338,
)


_MODULESTATEPACKAGE = _descriptor.Descriptor(
  name='ModuleStatePackage',
  full_name='tctrl.state.ModuleStatePackage',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='basePath', full_name='tctrl.state.ModuleStatePackage.basePath', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='module', full_name='tctrl.state.ModuleStatePackage.module', index=1,
      number=2, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=340,
  serialized_end=420,
)

_PARAMSTATE.fields_by_name['value'].message_type = google_dot_protobuf_dot_struct__pb2._VALUE
_PARAMSTATE.fields_by_name['valueIndex'].message_type = google_dot_protobuf_dot_wrappers__pb2._INT32VALUE
_MODULESTATE.fields_by_name['param'].message_type = _PARAMSTATE
_MODULESTATE.fields_by_name['childModule'].message_type = _MODULESTATE
_MODULESTATEPACKAGE.fields_by_name['module'].message_type = _MODULESTATE
DESCRIPTOR.message_types_by_name['ParamState'] = _PARAMSTATE
DESCRIPTOR.message_types_by_name['ModuleState'] = _MODULESTATE
DESCRIPTOR.message_types_by_name['ModuleStatePackage'] = _MODULESTATEPACKAGE

ParamState = _reflection.GeneratedProtocolMessageType('ParamState', (_message.Message,), dict(
  DESCRIPTOR = _PARAMSTATE,
  __module__ = 'tctrl_state_pb2'
  # @@protoc_insertion_point(class_scope:tctrl.state.ParamState)
  ))
_sym_db.RegisterMessage(ParamState)

ModuleState = _reflection.GeneratedProtocolMessageType('ModuleState', (_message.Message,), dict(
  DESCRIPTOR = _MODULESTATE,
  __module__ = 'tctrl_state_pb2'
  # @@protoc_insertion_point(class_scope:tctrl.state.ModuleState)
  ))
_sym_db.RegisterMessage(ModuleState)

ModuleStatePackage = _reflection.GeneratedProtocolMessageType('ModuleStatePackage', (_message.Message,), dict(
  DESCRIPTOR = _MODULESTATEPACKAGE,
  __module__ = 'tctrl_state_pb2'
  # @@protoc_insertion_point(class_scope:tctrl.state.ModuleStatePackage)
  ))
_sym_db.RegisterMessage(ModuleStatePackage)


DESCRIPTOR.has_options = True
DESCRIPTOR._options = _descriptor._ParseOptions(descriptor_pb2.FileOptions(), _b('\n\024net.t3kt.tctrl.stateB\017TctrlStateProto'))
# @@protoc_insertion_point(module_scope)
