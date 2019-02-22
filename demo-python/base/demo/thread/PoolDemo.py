#from multiprocessing import pool
import multiprocessing
import os,time,random


def worker(msg):
    tStart = time.time();
    print("%s开始执行 , 进程号为%d " % (msg , os.getpid()));

    #random.random()随机生成0-1之间的浮点数
    time.sleep(random.random()*2);
    tStop = time.time();

    print(msg , "执行完毕 , 耗时%0.2f" % (tStop-tStart));



if __name__ == '__main__':
    #定义一个进程池 , 最大进程数3
    po = multiprocessing.Pool(3);

    for i in range(0,10):
        #pool().apply_async(要调用的目标 , (传递给目标的参数元祖 , ))
        #每次循环将会用空闲出来的子进程去调用目标
        po.apply_async(worker , (i,));

    print("-----start-----");
    #关闭进程池 , 关闭后po不再接收新的进程
    po.close();

    #等待po中所有子进程执行完毕,必须放在close语句之后
    po.join();

    print("-----end-----");


