import socket;



def serviceClient(newSocket):
    """为这个客户端返回数据"""
    #1.接收浏览器发送过来的请求,即http请求
    request = newSocket.recv(1024).decode("utf-8");
    print(request);

    #返回http格式的数据,给浏览器
    #2.1 准备发送给浏览器的数据 --- header
    response = "HTTP/1.1 200 OK\r\n";
    response += "\r\n";
    #2.2准备发送给浏览器的数据 --- body
    response += "hahaha";
    newSocket.send(response.encode("utf-8"));



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

    while True:
        #4.等待客户端的连接
        newSocket , clientAddr = tcpServerSocket.accept();

        #5.为这个客户端服务
        serviceClient(newSocket);
    tcpServerSocket.close();


if __name__ == '__main__':
    execute();