import socket;
import re;
import multiprocessing;
import time;
import sys;
import dynamic.miniFrame


class WSGIServer:
    """
        测试url:
            http://localhost:7890
            http://localhost:7890/login.py
            #伪静态
            http://localhost:7890/login.html

        启动:
            python WSGIServer.py 7890 miniFrame:application
    """

    def __init__(self , port , app ,staticPath):
        """用来完成整体的控制"""
        #1.创建套接字
        self.tcpServerSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM);
        #设置当服务器先close , 即服务器4次挥手之后资源立即释放,这样就保证了,下次运行程序,可以立即接收请求
        self.tcpServerSocket.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1);
        #2.绑定
        self.tcpServerSocket.bind(("",port));
        #3.变为监听套接字
        self.tcpServerSocket.listen(128);
        #app函数
        self.application= app;
        #资源文件路径
        self.staticPath = staticPath;


    def serviceClient(self,newSocket):
        """为这个客户端返回数据"""
        #1.接收浏览器发送过来的请求,即http请求
        #print(newSocket.recv(1024).decode("utf-8"));
        request = newSocket.recv(1024).decode("utf-8");
        #print(request);

        requestLines = request.splitlines();
        #print(requestLines);
        #GET /index.html HTTP/1.1
        #get post put del
        fileName = "";
        print("==========================");
        print(request);
        print(len(requestLines));
        print("==========================");

        if len(requestLines) ==0 :
            newSocket.close();
            return;

        ret = re.match("[^/]+(/[^ ]*)" , requestLines[0]);
        if ret:
            fileName = ret.group(1);
            if fileName=="/":
                fileName="/index.html";


        #返回http格式的数据,给浏览器
        #2.1 准备发送给浏览器的数据 --- header 如果请求的资源不是py结尾的,那么久默认为静态资源(html/css/js/png , jpg等)
        print("html/static"+fileName)
        if not fileName.endswith(".html"):
            try:
                f = open(self.staticPath+fileName , "rb");
            except:
                response = "HTTP/1.1 404 NOT FOUND\r\n";
                response +="\r\n";
                response += "------file not found------";
                newSocket.send(response.encode("utf-8"));
            else:
                htmlContent = f.read();
                f.close();
                #2.1准备发送给浏览器的数据 --- header
                response = "HTTP/1.1 200 OK\r\n";
                response +="\r\n";
                #2.2准备发送给浏览器的数据 =--- body

                #将response header发送给浏览器
                newSocket.send(response.encode("utf-8"));
                #将response body发送给浏览器
                newSocket.send(htmlContent);
        else:
            #2.2如果是以py结尾的,认为是动态资源的请求


            #body = "hahaha %s" % time.ctime();
            #body = miniFrame.login();
            #body = miniFrame.application(fileName);

            env = dict();
            env['PATH_INFO'] = fileName;
            body = self.application(env,self.setResponseHeader);

            header = "HTTP/1.1 %s\r\n" % self.status;

            for temp in self.headers:
                header += "%s:%s\r\n" % (temp[0]  , temp[1]);

            header += "\r\n";


            response = header+body;
            #发送response给浏览器
            newSocket.send(response.encode("utf-8"));

        #关闭套接字
        newSocket.close();

        # response = "HTTP/1.1 200 OK\r\n";
        # response += "\r\n";
        # #2.2准备发送给浏览器的数据 --- body
        # response += "hahaha";
        # newSocket.send(response.encode("utf-8"));


    def setResponseHeader(self , status , headers):
        self.status = status;
        self.headers = headers;




    def execute(self):


        while True:
            #4.等待客户端的连接
            newSocket , clientAddr = self.tcpServerSocket.accept();

            #5.为这个客户端服务
            p = multiprocessing.Process(target=self.serviceClient , args=(newSocket,));
            p.start();

            #为什么要关闭 , 因为子进程会拷贝newSocket的指向 , 这样主进程和子进程就有两个引用 ,
            #如果不把主进程的newSocket关闭 , 只关闭子进程的newSocket的话 , 就会导致浏览器一直转圈
            #所以newSocket要关闭两次
            newSocket.close();


            # serviceClient(newSocket);
        self.tcpServerSocket.close();



def main():
    """控制整体,创建一个web服务器对象,然后调用这个对象的run_forever方法运行"""
    if len(sys.argv) == 3:
        try:
            port = int(sys.argv[1]);    #7890
            frameAppName = sys.argv[2]; #miniFrame:application
        except Exception as ret:
            print("端口输入错误......");
            return;
    else:
        print("请按照以下方法运行:");
        print("python xxxx.py 7890 miniFame:application");
        return;


    #miniFrame:application
    ret = re.match(r"([^:]+):(.*)" , frameAppName);
    if ret:
        frameName = ret.group(1);   #miniFrame
        appName =  ret.group(2);    #application
    else:
        print("请按照以下方式运行:");
        print("python3 xxx.py 7890 miniFame:application");
        return;

    #sys.path.append("./dynamic");

    with open("./conf/server.conf") as f:
        confInfo = eval(f.read());

    sys.path.append(confInfo['dynamicPath']);

    #imort frameName   ---> 找frame.py
    frame = __import__(frameName);  #返回值标记导入的模板
    app = getattr(frame,appName);   #此时app就指向了dynamic/miniFrame中的这个函数

    #print(app)


    server = WSGIServer(port  , app , confInfo['staticPath']);
    server.execute();



if __name__ == '__main__':
    main();