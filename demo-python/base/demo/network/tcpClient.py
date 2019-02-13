import socket


def execute():
    #1.创建tcp的套接字
    tcpSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM);
    #2.连接服务器
    serverIp = input("请输入要连接的服务器ip:");
    serverPort = int(input("请输入要连接的服务器port:"));
    serverAddr = (serverIp , serverPort);
    tcpSocket.connect(serverAddr);
    #3.发送数据/接受数据
    sendData = input("请输入要发送的数据:");
    tcpSocket.send(sendData.encode("utf-8"));
    #4.关闭套接字
    tcpSocket.close();


if __name__ == '__main__':
    execute();