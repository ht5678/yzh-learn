from django.shortcuts import render
from django.http import HttpResponse,JsonResponse
from datetime import datetime,timedelta

# Create your views here.


#cookie的过期时间 , 默认是浏览器关闭就会删除cookie
def setCookie(request):
    '''设置cookie信息'''
    response = HttpResponse('设置cookie');
    #设置一个cookie
    response.set_cookie('num',1
                        #设置过期时间
                        #,expires=datetime.now()+timedelta(days=14)
                        #,max_age=14*24*3600
                        );
    #返回response
    return response;



def getCookie(request):
    '''获取cookie的信息'''
    #取出cookie  num的值
    num = request.COOKIES['num'];
    return HttpResponse(num);