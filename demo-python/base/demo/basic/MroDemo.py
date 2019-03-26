print("*************多继承使用super().__init__()发生的状态*************");


class Parent(object):

    def __init__(self , name , *args , **kargs):    #为避免多继承报错,使用不定长参数,接受参数
        print("parent的init开始被调用");
        self.name = name;
        print("parent的init结束被调用");



class Son1(Parent):
    def __init__(self , name , age , *args , **kargs):    #为避免多继承报错,使用不定长参数,接受参数
        print("Son1的init开始被调用");
        self.age = age;
        super().__init__(name , *args , **kargs);
        print("Son1的init结束被调用");


class Son2(Parent):
    def __init__(self, name , gender, *args , **kargs):    #为避免多继承报错,使用不定长参数,接受参数
        print("Son2的init开始被调用");
        self.gender = gender;
        super().__init__(name , *args , **kargs);
        print("Son2的init结束被调用");


class Grandson(Son1 , Son2):
    def __init__(self , name , age ,gender):
        print("Grandson的init开始被调用");
        #多继承时,相对于使用类名.__init__方法,要把每个父类全部写一遍
        #而super只是一句话,执行了全部父类的方法,这也是多继承需要全部传参的一个原因
        super().__init__(name , age , gender);
        print("Grandson的init结束被调用");


#这个顺序是有c3算法决定的
print(Grandson.__mro__);
