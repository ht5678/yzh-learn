#自定义过滤器
#过滤器其实就是python函数
from django.template import Library


#创建一个Library类的对象
register = Library();



#自定义的过滤器函数,至少有一个参数,最多有两个,没有其他情况
@register.filter
def mod(num):
    '''判断num是否为偶数'''
    return num%2 == 0;