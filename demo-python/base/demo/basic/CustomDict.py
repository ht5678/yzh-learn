class Foo:
    def __getitem__(self, item):
        print("__getitem__" , item);

    def __setitem__(self, key, value):
        print("__setitem__" , key , value);

    def __delitem__(self, key):
        print("__delitem__" , key);


obj = Foo();

#自动触发执行 __getitem__
result = obj['k1'];

#自动触发执行 __setitem__
obj['k2'] = "laowang";

#自动触发执行 __delitem__
del obj['k1'];