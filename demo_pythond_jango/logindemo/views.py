from django.shortcuts import render

# Create your views here.


#http://localhost:8000/login/login
def login(request):
    '''显示登录页面'''
    return render(request,'logindemo/login.html')


def login_check(request):
    '''登录校验视图'''
    #1.获取提交的用户名和密码
    #2.进行登录的校验
    #3.返回应答
