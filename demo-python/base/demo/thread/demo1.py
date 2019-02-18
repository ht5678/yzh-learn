import time;
import threading;

def sing():
    """唱歌 , 5s钟"""
    for i in range(5):
        print("---正在唱 : 菊花茶---");
        time.sleep(1);


def dance():
    """跳舞 : 5s钟"""
    for i in range(5):
        print("---正在跳舞---");
        time.sleep(1);



def execute():

    t1 = threading.Thread(target=sing);
    t2 = threading.Thread(target=dance);

    t1.start();
    t2.start();

    #sing();
    #dance();


if __name__ == '__main__':
    execute();

    while True:
        length = len(threading.enumerate());
        print("当前运行的线程数为:%s" % length);
        if length<=1:
            break;

        time.sleep(0.5);