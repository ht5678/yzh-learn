
import  socket;


tcpServerTcp = socket.socket(socket.AF_INET , socket.SOCK_STREAM);
tcpServerTcp.bind(("",7890));
tcpServerTcp.listen(128);
tcpServerTcp.setblocking(False);    #将套接字设置为非堵塞的方式


clientSocketList = list();

while True:
    try:
        newSocket , newAddr = tcpServerTcp.accept();
    except Exception as ret:
        pass;
        #print("没有新的客户端到来");
    else:
        print("只要没有产生异常 , 那么也就意味着来了一个新的客户端");
        newSocket.setblocking(False);   #设置套接字为非堵塞的方式
        clientSocketList.append(newSocket);

    for clientSocket in clientSocketList :
        try:
            recvData = clientSocket.recv(1024);
        except Exception as ret:
            pass;
            #print("这个客户端没有发送过来数据");
        else:
            if recvData:
                #对方发送过来数据
                print("客户端发送过来了数据");
            else:
                #对方调用了close导致了recv数据返回
                clientSocketList.remove(clientSocket);
                clientSocket.close();
                print("客户端已经关闭");