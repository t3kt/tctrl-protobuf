from cefpython3 import cefpython as cef
import platform
import sys
import base64
# import tctrlgui.resources as resources

homehtml = '''<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello TCTRL!</title>
</head>
<body>
<h1>Hello TCTRL!</h1>
</body>
</html>'''

class TctrlGuiApp:
    def __init__(self):
        self.browser = None

    def Run(self):
        check_versions()
        sys.excepthook = cef.ExceptHook  # To shutdown all CEF processes on error
        cef.Initialize()
        # homehtml = resources.Get('index.html')
        self.browser = cef.CreateBrowserSync(
            url=html_to_data_uri(homehtml),
            window_title="TCTRL CEF Gui")
        cef.MessageLoop()
        cef.Shutdown()

def main():
    app = TctrlGuiApp()
    app.Run()

def check_versions():
    print("[hello_world.py] CEF Python {ver}".format(ver=cef.__version__))
    print("[hello_world.py] Python {ver} {arch}".format(
        ver=platform.python_version(), arch=platform.architecture()[0]))
    assert cef.__version__ >= "55.3", "CEF Python v55.3+ required to run this"


def html_to_data_uri(html, js_callback=None):
    # This function is called in two ways:
    # 1. From Python: in this case value is returned
    # 2. From Javascript: in this case value cannot be returned because
    #    inter-process messaging is asynchronous, so must return value
    #    by calling js_callback.
    html = html.encode("utf-8", "replace")
    b64 = base64.b64encode(html).decode("utf-8", "replace")
    ret = "data:text/html;base64,{data}".format(data=b64)
    if js_callback:
        js_callback.Call(ret)
    else:
        return ret

if __name__ == '__main__':
    main()
