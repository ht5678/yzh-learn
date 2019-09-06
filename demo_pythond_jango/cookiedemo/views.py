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


#默认保存在  django_session  表中
def setSession(request):
    '''设置session'''
    request.session['username']='smart';
    request.session['age']=18;
    return HttpResponse('设置session');


def getSession(request):
    '''获取session'''
    username = request.session['username'];

    #request.session.get('key',默认值)
    #清除所有session ,在存储中删除值部分
    #request.session.clear();
    #清除所有session数据, 在存储中删除session的整条数据
    #request.session.flush();
    #删除session中指定的键值,在存储中只删除某个键对应的值
    #del request.session['键']
    #设置会话超时时间,如果没有指定过期时间则两个星期后过期
    #request.session.set_expiry(value)
    #如果value是整数,会话的session_id  cookie将在value秒没有活动后过期
    #如果value是0,那么会话的session_id cookie会在用户的浏览器关闭时期
    #如果value为none , 那么会话的session_id  cookie会在两周之后过期

    age = request.session['age'];
    return HttpResponse(username+":"+str(age));