import socket;



def execute():
    #1.创建套接字socket
    tcpServerSocket = socket.socket(socket.AF_INET , socket.SOCK_STREAM);

    #2.绑定本地信息bind
    tcpServerSocket.bind(("",7890));

    #3.让默认的套接字由主动变为被动
    tcpServerSocket.listen(128);

    while True:

        #4.等待客户端的连接 accept
        newClientSocket , clientAddr = tcpServerSocket.accept();

        while True:

            #5.接收客户端发来的请求     recv()和recv_from()方法的不同在于是否有客户端数据
            recvData = newClientSocket.recv(1024);
            print("客户端发过来的请求是%s "% recvData.decode("utf-8"));


            #如果recv堵塞 , 那么有两种方法:
            #1.客户端发送过来数据
            #2.客户端调用close导致 , 这里recv解堵塞
            if recvData:
                #回送一部分数据给客户端
                newClientSocket.send("hahaha".encode("utf-8"));
            else:
                break;

        #关闭套接字
        newClientSocket.close();
    tcpServerSocket.close();



if __name__ == '__main__':
    execute();