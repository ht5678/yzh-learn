import socket



def sendMsg(udpSocket):
    """发送消息"""
    #获取要发送的内容
    # 获得对方的 ip/port
    destIp = input("请输入对方的ip:");
    destPort = input("请输入对方的port:");
    # 从键盘获取数据
    sendData = input("请输入要发送的数据:");

    udpSocket.sendto(sendData.encode("utf-8"),(destIp,int(destPort)));


def recvMsg(udpSocket):
    """接收数据"""
    recvData = udpSocket.recvfrom(1024);
    recvMsg = recvData[0];  # 存储接收到的数据
    sendAddr = recvData[1];  # 存储发送方的地址信息

    print("%s:%s" % (str(sendAddr), recvMsg.decode("utf-8")))




def execute():
    # 创建一个udp套接字
    udpSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM);
    udpSocket.bind(("",7788));


    while True:
        # 发送
        sendMsg(udpSocket);

        #接收并显示
        recvMsg(udpSocket);

    # 关闭套接字
    udpSocket.close();


if __name__ == '__main__':
    execute();