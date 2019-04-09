
"""

import time;


def login():
    return "------login------welcome to our website ...... time:%s" % time.ctime();

def register():
    return "------register------welcome to our website ...... time:%s" % time.ctime();


def profile():
    return "------profile------welcome to our website ...... time:%s" % time.ctime();


def application(fileName):
    if fileName == "/login.py":
        return login();
    elif fileName == "/register.py":
        return register();
    else:
        return "no found your page";

"""


URL_FUNC_DICT={

};


def route(url):
    def setFunc(func):
        #URL_FUNC_DICT['/index.py']=index
        URL_FUNC_DICT[url] = func;
        def callFunc(*args , **kwargs):
            return func(*args,**kwargs);
        return callFunc;
    return setFunc;


@route("/index.py")
def index():
    with open("html/index.html") as f:
        content = f.read();
    return content;


@route("/login.py")
def login():
    return "这是登录页面";





#wsgi协议支持
def application(environ , startResponse):
    startResponse('200 OK' , [('Content-Type' , 'text/html;charset=utf-8'),('server','mini-frameV1.0')]);
    fileName = environ['PATH_INFO'];

    """
        if fileName=='/index.py':
            return index();
        elif fileName=='/login.py':
            return login();
        else:
            return "Hello World!";
    """
    try:
        func = URL_FUNC_DICT[fileName];
        return func();
    except Exception as ret:
        return "产生异常:%s" % str(ret);
