import socket;



def execute():
    """完成udp聊天的整体控制"""

    #1.创建套接字
    udpSocket = socket.socket(socket.AF_INET , socket.SOCK_STREAM);

    #2.绑定本地信息
    udpSocket.bind(("" , 7890));

    #3.获取对方的ip
    destIp = input("请输入对方的ip:");
    destPort = input("请输入对方的端口:");

    #4.接收数据
    while True:
        recvData = udpSocket.recvfrom(1024);
        print(recvData);

    #发送数据
    while True:
        sendData = input("请输入要发送的数据:");



if __name__ == '__main__':
    execute();