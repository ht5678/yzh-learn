from django.shortcuts import render,redirect
import logging
# Create your views here.



def index(request):
    return render(request, 'logindemo/index.html')


#http://localhost:8000/login/login
def login(request):
    '''显示登录页面'''
    return render(request,'logindemo/login.html')


def login_check(request):
    '''登录校验视图'''
    #request.POST保存的是post方式提交的参数    QueryDict
    #request.GET保存的是get方式提交的参数
    #1.获取提交的用户名和密码
    username = request.POST.get('username');
    password = request.POST.get('password');

    logging.info(username)
    print('username:'+username)
    print('password:'+password)

    #2.进行登录的校验
    #实际开发,根据用户名和密码查找数据库
    #模拟:
    if username=='smart' and password=='123':
        #用户名密码正确 , 跳转到首页
        #return redirect('/login/index');
        print('username:' + username)
        print('password:' + password)
    else:
        #用户名密码错误 , 跳转到登录页面
        #return redirect('/login/login');
        print('usernam1e:' + username)
        print('password1:' + password)
    #3.返回应答
