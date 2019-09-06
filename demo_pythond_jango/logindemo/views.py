from django.shortcuts import render,redirect
from django.http import JsonResponse
import logging
# Create your views here.


#http://localhost:8000/login/index
def index(request):
    return render(request, 'logindemo/index.html')


#http://localhost:8000/login/login
def login(request):
    '''显示登录页面'''
    print(request.path);
    #获取cookies , username
    if 'username' in request.COOKIES:
        #获取记住的用户名
        username = request.COOKIES['username'];
    else:
        username='';
    return render(request,'logindemo/login.html' , {'username':username})


def login_check(request):
    '''登录校验视图'''
    #request.POST保存的是post方式提交的参数    QueryDict
    #request.GET保存的是get方式提交的参数
    #1.获取提交的用户名和密码

    username = request.POST.get('username');
    password = request.POST.get('password');
    remember = request.POST.get('remember');

    #2.进行登录的校验
    #实际开发,根据用户名和密码查找数据库
    #模拟:
    if username=='smart' and password=='123':
        #用户名密码正确 , 跳转到首页
        response = redirect('/login/index');
        #判断是否需要记住用户名
        if remember == 'on':
            #设置cookie , username,过期时间为一周
            response.set_cookie('username',username ,max_age=7*24*3600);
        return response;
    else:
        #用户名密码错误 , 跳转到登录页面
        return redirect('/login/login');
    #3.返回应答


#http://localhost:8000/login/testAjax
def testAjax(request):
    return render(request,'logindemo/test_ajax.html');


def ajax_handle(request):
    return JsonResponse({'res':1});


#http://localhost:8000/login/login_ajax
def login_ajax(request):
    '''显示ajax登录页面'''
    return render(request,'logindemo/login_ajax.html');




def login_ajax_check(request):
    '''ajax登录校验'''
    #获取用户名密码
    username = request.POST.get('username');
    password = request.POST.get('password');


    #进行校验,返回json数据
    if username == 'smart' and password=='123':
        #校验成功
        return JsonResponse({'res':1});
    else:
        # 校验失败
        return JsonResponse({'res': 0});
