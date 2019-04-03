import time;


def setFunc(func):
    def callFunc(*args , **kwargs):
        startTime = time.time();
        return func(*args , **kwargs);
        stopTime= time.time();
        print("all times %f" % (stopTime-startTime));
    return callFunc;


@setFunc    #等价于test1=setFunc(test1);  ,手写实现
def test(a , *args , **kwargs):
    print("------test1------ %d" % a);
    print("------test1------ " , args);
    print("------test1------ " , kwargs);
    # for i in range(1000):
    #     pass;
    return "ok";


test(100);

test(100,200);

ret  = test(100,200,300 , mm =100);
print(ret);