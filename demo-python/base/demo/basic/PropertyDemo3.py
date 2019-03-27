class Foo(object):
    def getBar(self):
        print("get bar");
        return "laowang";


    def setBar(self , value):
        #必须两个参数
        print("setter");
        return "set value"+value;

    def delBar(self):
        print("deleter");
        return "laowang";

    BAR = property(getBar , setBar , delBar , "description ... ");



obj =  Foo();

#自动调用第一个参数中定义的方法,getBar
obj.BAR;

#自动调用第二个参数中定义的方法,setBar方法,并将"alex"当做参数传入
obj.BAR = "alex";
#自动获取第四个参数中设置的值 , description ...
desc = Foo.BAR.__doc__;

print(desc);

#自动调用三个参数中定义的方法,delBar方法
del obj.BAR;

