import threading;
import time;


g_num=0;

#创建一个互斥锁 , 默认是没有上锁的
mutex = threading.Lock();



def test1(num):
    global  g_num;

    #上锁 , 如果之前没有被上锁 , 那么此时 , 上锁成功
    #如果上锁之前已经被上锁了 , 那么此时会被堵塞在这里 , 直到这个锁被解开
    mutex.acquire();
    for i in range(num):
        g_num+=1;
    #解锁
    mutex.release();
    print("---in test1 g_num=%d---" % g_num)



def test2(num):
    global g_num;
    mutex.acquire();
    for i in range(num):
        g_num+=1;
    mutex.release();
    print("---- in test2 g_num = %d ---" % num);



def execute():
    t1 = threading.Thread(target=test1 , args=(1000000 , ));
    t2 = threading.Thread(target=test2 , args=(2000000 , ));

    t1.start();
    t2.start();



if __name__ == '__main__':
    execute();

    time.sleep(2);
