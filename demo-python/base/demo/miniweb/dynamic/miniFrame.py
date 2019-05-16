
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
@route(r"/index.html")
def index(ret):
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
def login(ret):
    return "这是登录页面";



#给路由器添加正则表达式的原因:在实际开发中,url往往会带很多参数,例如/add/000007.html中的000007就是参数
#如果没有正则的话,那么就需要编写n次@route来进行添加,url对应的函数到字典中,此时字典中的键值对有n个,浪费空间
#而采用了正则的话,那么只要编写一次@route就可以完成多个url例如/add/000007.html等对应一个函数,此时字典中的
#键值对会少很多
@route(r"/add/(\d+)\.html")
def addFocus(ret):
    stockCode = ret.group(1);
    return "add (%s) ok..." % stockCode;




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
        #func = URL_FUNC_DICT[fileName];
        #return func();

        for url,func in URL_FUNC_DICT.items():
            #{
            #   r"/index.html":index,
            #   r"/center.html":center,
            #   r"add/\d+\.html":addFocus
            #}
            ret = re.match(url,fileName);
            if ret:
                return func(ret);
            else:
                return "请求的url(%s)没有对应的函数......" % fileName;
    except Exception as ret:
        return "产生异常:%s" % str(ret);
