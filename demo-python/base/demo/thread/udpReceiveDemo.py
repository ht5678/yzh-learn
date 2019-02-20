import socket


def execute():
    #1.创建套接字
    udpSocket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM);
    #2.绑定一个本地信息
    localaddr = ("127.0.0.1" , 8080);
    udpSocket.bind(localaddr);
    #3.接收数据
    recvData = udpSocket.recvfrom(1024); #1024表示单次接收的最多字节数

    #4.打印接收到的数据
    #print(recvData);
    #recvData这个变量中存储的是一个元组,  (接收到的数据,(发送方的ip , port))
    recvMsg = recvData[0];      #存储接收到的数据
    sendAddr = recvData[1];     #存储发送方的地址信息

    print("%s:%s" % (str(sendAddr) , recvMsg.decode("utf-8")))


    #5.关闭套接字
    udpSocket.close();


if __name__ == '__main__':
    execute();