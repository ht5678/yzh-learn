

def test2(a , b ,*args , **kwargs):
    print("-----------");
    print(a);
    print(b);
    print(args);
    print(kwargs);





def test1(a , b ,*args , **kwargs):
    print(a);
    print(b);
    print(args);
    print(kwargs);

    #这样穿参数有问题
    #test2(a, b, args, kwargs);

    #拆包方式传参数 , 正确的方式
    test2(a , b , *args , **kwargs);


test1(11,22,33,44,55,66,name="lisi" , age=19);