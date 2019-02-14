import socket

def execute():
    #1.创建套接字
    tcpSocket = socket.socket(socket.AF_INET , socket.SOCK_STREAM);
    #2.获取服务器的ip, port
    destIp = input("请输入下载服务器的ip:");
    destPort = input("请输入下载服务器的port:");
    #3.连接服务器
    tcpSocket.connect((destIp,destPort));

    #4.获取下载的文件名字
    downloadFileName = input("请输入下载文件的名字:");

    #5.将文件名发送到服务器
    tcpSocket.send(downloadFileName.encode("utf-8"));

    #6.接收文件中的数据
    recvData = tcpSocket.recv(1024);

    #7.保存接收到的数据到服务器
    with open("[新]"+downloadFileName , "wb") as f:
        f.write(recvData);

    #8.关闭套接字
    tcpSocket.close();




if __name__ == '__main__':
    execute();