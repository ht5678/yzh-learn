
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


def index():
    return "这是主页";

def login():
    return "这是登录页面";


#wsgi协议支持
def application(environ , startResponse):
    startResponse('200 OK' , [('Content-Type' , 'text/html;charset=utf-8'),('server','mini-frameV1.0')]);
    fileName = environ['PATH_INFO'];

    if fileName=='/index.py':
        return index();
    elif fileName=='/login.py':
        return login();
    else:
        return "Hello World!";
