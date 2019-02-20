import socket;
import threading;



def recvMsg(udpSocket):
    """接收数据并显示"""
    #4.接收数据
    while True:
        recvData = udpSocket.recvfrom(1024);
        print(recvData);



def sendMsg(udpSocket , destIp , destPort):
    """发送数据"""
    #发送数据
    while True:
        sendData = input("请输入要发送的数据:");
        udpSocket.sendto(sendData.encode("utf-8") , (destIp , destPort));



def execute():
    """完成udp聊天的整体控制"""

    #1.创建套接字
    udpSocket = socket.socket(socket.AF_INET , socket.SOCK_DGRAM);

    #2.绑定本地信息
    udpSocket.bind(("" , 7890));

    #3.获取对方的ip
    destIp = input("请输入对方的ip:");
    destPort = int(input("请输入对方的端口:"));

    #创建2个线程 , 去执行相应的功能
    tRecv = threading.Thread(target=recvMsg , args=(udpSocket,));
    tSend = threading.Thread(target=sendMsg , args=(udpSocket , destIp , destPort));

    tRecv.start();
    tSend.start();



if __name__ == '__main__':
    """多线程实现udp收发 , 全双工模式"""
    execute();