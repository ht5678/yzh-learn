import socket;



def sendFile2Client(newClientSocket , clientAddr):
    #接收客户端发过来的要下载的文件名
    fileName = newClientSocket.recv(1024).decode("utf-8");
    print("客户端(%s)需要下载的文件是 : %s " % (str(clientAddr), fileName));

    #发送文件的数据给客户端
    newClientSocket.send(fileContent);



def execute():
    #1.创建套接字socket
    tcpServerSocket = socket.socket(socket.AF_INET , socket.SOCK_STREAM);

    #2.绑定本地信息bind
    tcpServerSocket.bind(("",7890));

    #3.让默认的套接字由主动变为被动
    tcpServerSocket.listen(128);

    #4.等待客户端的连接 accept
    newClientSocket , clientAddr = tcpServerSocket.accept();


    #发送文件的数据给客户端
    sendFile2Client(newClientSocket,clientAddr);

    #关闭套接字
    newClientSocket.close();
    tcpServerSocket.close();



if __name__ == '__main__':
    execute();