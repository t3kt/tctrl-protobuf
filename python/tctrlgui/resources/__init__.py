
class _Loader:
    def __init__(self):
        self.files = {}
        self._load('index.html')

    def _load(self, *paths, **kwargs):
        import pkg_resources
        if 'mode' not in kwargs:
            kwargs['mode'] = 'r'
        for path in paths:
            filepath = pkg_resources.resource_filename(__name__, path)
            with open(filepath, **kwargs) as f:
                data = f.read()
            self.files[path] = data

_files = _Loader().files

def Get(path):
    return _files.get(path)
