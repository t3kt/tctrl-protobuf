import google.protobuf.json_format as pbjson
import json
import tctrlgen.tctrl_schema_pb2 as pb
import sys
import tctrl.legacy_json

def main():
    import argparse

    parser = argparse.ArgumentParser()
    parser.add_argument('infile', metavar='F')
    parser.add_argument('-i', '--infmt', dest='infmt', default='json')
    parser.add_argument('-o', '--outfmt', dest='outfmt', default='json')

    args = parser.parse_args()

    with open(args.infile, 'r') as infile:
        rawinput = infile.read()
    infmt = args.infmt
    if infmt == 'pbjson':
        appspec = pbjson.Parse(rawinput, pb.AppSpec())
    elif infmt == 'json':
        inputdict = json.loads(rawinput)
        appspec = tctrl.legacy_json.ParseAppSpec(inputdict)
    else:
        parser.print_usage()
        raise Exception('Invalid input format: ' + infmt)

    outfmt = args.outfmt
    if outfmt == 'pbjson':
        outdict = pbjson.MessageToDict(appspec)
    elif outfmt == 'json':
        outdict = tctrl.legacy_json.AppSpecToObj(appspec)
    else:
        parser.print_usage()
        raise Exception('Invalid output format: ' + outfmt)
    json.dump(outdict, sys.stdout, sort_keys=True, indent='  ')

if __name__ == '__main__':
    main()
