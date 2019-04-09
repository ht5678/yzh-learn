def setLevel(levelNum):
    def setFunc(func):
        def callFunc(*args , **kwargs):
            if levelNum ==1:
                print("-----权限级别10 , 验证------");
            elif levelNum==2:
                print("-----权限级别2 , 验证------");
            return func();
        return callFunc;
    return setFunc;



@setLevel(1)
def test1():
    print("------test1------");


@setLevel(2)
def test2():
    print("------test2------");
    return "ok";



test1();
test2();