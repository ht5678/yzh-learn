

"""

https://docs.python.org/2/reference/datamodel.html#object.__getslice__




class MyClass:
    ...
    def __getitem__(self, index):
        ...
    def __setitem__(self, index, value):
        ...
    def __delitem__(self, index):
        ...

    if sys.version_info < (2, 0):
        # They won't be defined if version is at least 2.0 final

        def __getslice__(self, i, j):
            return self[max(0, i):max(0, j):]
        def __setslice__(self, i, j, seq):
            self[max(0, i):max(0, j):] = seq
        def __delslice__(self, i, j):
            del self[max(0, i):max(0, j):]
    ...

"""


class Foo(object):


    def __getitem__(self, item):
        print("__getslice__",item);


    def __setitem__(self, key, value):
        print("__setslice__" , key , value);


    def __delitem__(self, key):
        print("__delslice__" , key);


obj = Foo();

#自动触发执行__getslice__
obj[-1:1];

#自动触发执行__setslice__
obj[0:1]=[11,22,33,44];

#自动触发执行 __delslice__
del obj[0:2];
