
class Fibonacci(object):
    def __init__(self , all_num):
        self.allNum = all_num;
        self.currentNum = 0;
        self.a=0;
        self.b=1;



    def __iter__(self):
        return self;


    def __next__(self):
        if self.currentNum<self.allNum:
            ret = self.a;

            self.a , self.b = self.b , self.a + self.b;
            self.currentNum+=1;

            return ret;
        else:
            raise StopIteration;



fibo = Fibonacci(10);

for num in fibo:
    print(num);

