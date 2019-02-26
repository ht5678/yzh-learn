
def createNum(allNum):
    a , b = 0,1;
    currentNum = 0;

    while currentNum<allNum:
        #如果一个函数中有yield语句,那么这个就不再是函数,而是一个生成器的模板
        yield  a;  #a表示要返回的变量
        a,b = b , a+b;
        currentNum +=1;
    return "ok......";


#如果在调用createNum的时候,发现这个函数中有yield那么此时,不是调用函数,而是创建一个生成器对象
obj = createNum(10);

ret = next(obj);
print(ret);

ret = next(obj);
print(ret);

for num in obj:
    print(num);


print("-------------------分割线-------------------");

obj2 = createNum(20);
while True:
    try:
        ret = next(obj2);
        print(ret);
    except Exception as ret:
        print(ret.value);
        break;
