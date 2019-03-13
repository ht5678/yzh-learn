import socket;
import re;
import multiprocessing;


def serviceClient(newSocket , request):
    """为这个客户端返回数据"""

    requestLines = request.splitlines();
    #GET /index.html HTTP/1.1
    #get post put del
    fileName = "";
    ret = re.match("[^/]+(/[^ ]*)" , requestLines[0]);
    if ret:
        fileName = ret.group(1);
        if fileName=="/":
            fileName="/index.html";


    #返回http格式的数据,给浏览器
    #2.1 准备发送给浏览器的数据 --- header
    print("./html"+fileName)
    try:
        f = open("./html"+fileName , "rb");
    except:
        response = "HTTP/1.1 404 NOT FOUND\r\n";
        response +="\r\n";
        response += "------file not found------";
        newSocket.send(response.encode("utf-8"));
    else:
        htmlContent = f.read();
        f.close();

        responseBody = htmlContent;


        #2.1准备发送给浏览器的数据 --- header
        responseHeader = "HTTP/1.1 200 OK\r\n";
        responseHeader +="Content-Length:%d\r\n" % len(responseBody);
        responseHeader +="\r\n";

        response = responseHeader.encode("utf-8")+responseBody;

        #将response header发送给浏览器
        newSocket.send(response);
        #将response body发送给浏览器
        newSocket.send(htmlContent);

    #关闭套接字 ----  修改成长连接版本
    #newSocket.close();



def execute():
    """用来完成整体的控制"""
    #1.创建套接字
    tcpServerSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM);
    #设置当服务器先close , 即服务器4次挥手之后资源立即释放,这样就保证了,下次运行程序,可以立即接收请求
    tcpServerSocket.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1);
    #2.绑定
    tcpServerSocket.bind(("",7890));
    #3.变为监听套接字
    tcpServerSocket.listen(128);
    tcpServerSocket.setblocking(False);


    clientSocketList = list();

    while True:
        #4.等待客户端的连接
        try:
            newSocket , clientAddr = tcpServerSocket.accept();
        except Exception as ret:
            pass;
        else:
            newSocket.setblocking(False);
            clientSocketList.append(newSocket);


        for clientSocket in clientSocketList:
            try:
                recvData = clientSocket.recv(1024).decode("utf-8");
            except Exception as ret:
                pass;
            else:
                if recvData:
                    serviceClient(clientSocket , recvData);
                else:
                    clientSocket.close();
                    clientSocketList.remove(clientSocket);

        #为什么要关闭 , 因为子进程会拷贝newSocket的指向 , 这样主进程和子进程就有两个引用 ,
        #如果不把主进程的newSocket关闭 , 只关闭子进程的newSocket的话 , 就会导致浏览器一直转圈
        #所以newSocket要关闭两次
        #newSocket.close();


        # serviceClient(newSocket);
    tcpServerSocket.close();


if __name__ == '__main__':
    execute();