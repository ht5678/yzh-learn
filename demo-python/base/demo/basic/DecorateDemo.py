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





print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

######################07-装饰器-7-多个装饰器对同一个函数装饰#########################

def addQx(func):
    print("开始进行装饰权限1的功能");
    def callFunc(*args,**kwargs):
        print("这是权限验证1");
        return func(*args, **kwargs);
    return callFunc;


def addXx(func):
    print("开始进行装饰xxx的功能");
    def callFunc(*args , **kwargs):
        print("这是xxx的功能");
        return func(*args , **kwargs);
    return callFunc;


@addQx      #相当于test1 = addQx(test1);
@addXx      #等价于test1 = addXx(test1);
def test22():
    print("-----test22-----");


test22();