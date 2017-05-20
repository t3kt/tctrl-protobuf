import tctrlgen.tctrl_schema_pb2 as pb

def EmbedModuleTypes(
        appspec: pb.AppSpec,
        removetypespecs=False):
    moduletypesbykey = {t.key: t for t in appspec.moduleType}
    if removetypespecs:
        appspec.moduleType.clear()

    def _moduleAction(modulespec: pb.ModuleSpec, **kwargs):
        if not modulespec.moduleType or modulespec.moduleType not in moduletypesbykey:
            return
        modtype = moduletypesbykey[modulespec.moduleType]
        values = GetModuleParamValues(modulespec)
        modulespec.paramGroup.clear()
        modulespec.paramGroup.extend(modtype.paramGroup)
        modulespec.param.clear()
        modulespec.param.extend(modtype.param)
        for param in modulespec.param:
            paramvalue = values.get(param.key)
            if not paramvalue:
                continue
            if isinstance(paramvalue, dict):
                param.value.CopyFrom(paramvalue['value'])
                if 'index' in paramvalue:
                    param.valueIndex.CopyFrom(paramvalue['index'])
            else:
                for i, part in enumerate(param.part):
                    part.value.CopyFrom(paramvalue[i])

    WalkChildModules(appspec, _moduleAction)

def EmbedOptionLists(
        appspec: pb.AppSpec,
        removeoptionlistdefs=False):
    optionlistsbykey = {l.key for l in appspec.optionList}
    if removeoptionlistdefs:
        appspec.optionList.clear()

    def _moduleAction(modulespec: pb.ModuleSpec, **kwargs):
        for param in modulespec.param:
            pass
        pass
    pass

def GetModuleParamValues(modulespec):
    values = {}
    for param in modulespec.param:
        if param.part:
            values[param.key] = [part.value for part in param.part]
        elif param.HasField('valueIndex'):
            values[param.key] = {'index': param.valueIndex, 'value': param.value}
        else:
            values[param.key] = {'value': param.value}
    return values

def WalkChildModules(node, action):
    for child in node.childModules:
        action(child, parent=node)
        WalkChildModules(child, action)
