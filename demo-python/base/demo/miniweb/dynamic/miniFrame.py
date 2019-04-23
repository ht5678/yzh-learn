
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

from pymysql import  *;
import re;


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

#伪静态
@route("/index.html")
def index():
    with open("html/index.html" , 'r', encoding='UTF-8') as f:
        content = f.read();

    #创建连接
    conn = connect(host="lenovodb",port=3306,user='myuser',password='mypassword',database='test',charset='utf8');
    #获得cursor对象
    cs = conn.cursor();
    cs.execute("select * from goods");
    stockInfos = cs.fetchall();

    cs.close();
    conn.close();



    trTemplate = """<tr>
        <td>%s</td>
        <td>%s</td>
        <td>%s</td>
        <td>%s</td>
        <td>%s</td>
        <td><button name="add" onclick='alert(hah)' type='button'>添加</button></td>
        </tr>
    """;

    html="";
    for lineInfo in stockInfos:
        html += trTemplate % (lineInfo[0],lineInfo[1],lineInfo[2],lineInfo[3],lineInfo[4]);


    content = re.sub(r"\{%content%\}" , html , content);

    return content;

#伪静态
@route("/login.html")
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
