from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
#1.定义视图函数 , httprequest对象
#2.进行url配置,建立url地址和视图的对应关系
# 测试:  http://localhost:8000/index

def index(request):
    #进行处理,和M和T进行交互...
    return HttpResponse('ok');

