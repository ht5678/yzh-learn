import socket

def execute():


    #创建一个udp套接字
    udpSocket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM);


    while True:
        # 从键盘获取数据
        sendData = input("请输入要发送的数据:");

        #如果输入的数据是exit , 那么久退出程序
        if sendData == "exit":
            break;

        #可以使用套接字收发数据
        #udpSocket.sendto(b"hahaha" , ("127.0.0.1",7788));
        udpSocket.sendto(sendData.encode("utf-8"), ("127.0.0.1",7788));



    #关闭套接字
    udpSocket.close();

if __name__ == '__main__':
    execute();