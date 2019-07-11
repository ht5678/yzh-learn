
def choose_class(name):
       if name == 'foo':
           class Foo(object):
               pass
           return Foo     # 返回的是类，不是类的实例
       else:
           class Bar(object):
               pass
           return Bar


MyClass = choose_class('foo');

# 函数返回的是类，不是类的实例
print(MyClass);

# 你可以通过这个类创建类实例，也就是对象
print(MyClass());


#######################################################################
#元类
#######################################################################

MyShinyClass = type('MyShinyClass', (), {});  # 返回一个类对象
print(MyShinyClass);





#######################################################################
#自定义元类
#######################################################################

# 元类会自动将你通常传给‘type’的参数作为自己的参数传入
def upper_attr(future_class_name, future_class_parents, future_class_attr):
  '''返回一个类对象，将属性都转为大写形式'''
  #  选择所有不以'__'开头的属性
  attrs = ((name, value) for name, value in future_class_attr.items() if not name.startswith('__'))
  # 将它们转为大写形式
  uppercase_attr = dict((name.upper(), value) for name, value in attrs)

  # 通过'type'来做类对象的创建
  return type(future_class_name, future_class_parents, uppercase_attr)



#已经弃用
########### 这会作用到这个模块中的所有类
#__metaclass__ = upper_attr;


class Foo(object , metaclass=upper_attr):
  # 已经弃用
  ########### 我们也可以只在这里定义__metaclass__，这样就只会作用于这个类中
  bar = 'bip'


print(hasattr(Foo, 'bar'));
# 输出: False
print(hasattr(Foo, 'BAR'));
# 输出:True

f = Foo();
print(f.BAR);
# 输出:'bip'





# 请记住，'type'实际上是一个类，就像'str'和'int'一样
# 所以，你可以从type继承
class UpperAttrMetaClass(type):
  # __new__ 是在__init__之前被调用的特殊方法
  # __new__是用来创建对象并返回之的方法
  # 而__init__只是用来将传入的参数初始化给对象
  # 你很少用到__new__，除非你希望能够控制对象的创建
  # 这里，创建的对象是类，我们希望能够自定义它，所以我们这里改写__new__
  # 如果你希望的话，你也可以在__init__中做些事情
  # 还有一些高级的用法会涉及到改写__call__特殊方法，但是我们这里不用
  def __new__(upperattr_metaclass, future_class_name, future_class_parents, future_class_attr):
    attrs = ((name, value) for name, value in future_class_attr.items() if not name.startswith('__'))
    uppercase_attr = dict((name.upper(), value) for name, value in attrs)
    return type(future_class_name, future_class_parents, uppercase_attr)





