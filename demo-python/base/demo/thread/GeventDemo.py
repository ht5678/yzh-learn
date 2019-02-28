import gevent
import time
from gevent import monkey;

#有耗时操作时需要
#将程序中用到的耗时操作的代码,换为gevent中自己实现的模块
monkey.patch_all();


def f1(n):
    for i in range(n):
        print(gevent.getcurrent(),i);
        gevent.sleep(0.5);


def f2(n):
    for i in range(n):
        print(gevent.getcurrent(),i);
        gevent.sleep(0.5);



def f3(n):
    for i in range(n):
        print(gevent.getcurrent(),i);
        gevent.sleep(0.5);



g1 = gevent.spawn(f1,5);
g2 = gevent.spawn(f1,5);
g3 = gevent.spawn(f1,5);

g1.join();
g2.join();
g3.join();
