from django.shortcuts import render,redirect
from django.template import loader,RequestContext
from django.http import HttpResponse
from templatedemo.models import  BookInfo

# Create your views here.

def my_render(request,template_path,context={}):
    #1.加载模板文件,获取一个模板对象
    temp = loader.get_template(template_path)
    #2.定义模板上下文,给模板文件传数据
    context = RequestContext(request,{});
    #3.模板渲染,产生一个替换后的html内容
    res_html = temp.render(context);
    #4.返回应答
    return HttpResponse(res_html);


#http://localhost:8000/template/index
def index(request):
    return my_render(request,'templatedemo/index.html');



def temp_var(request):
    '''模板变量'''
    my_dict = {'title':'字典键值'};
    my_list=[1,2,3];
    book = BookInfo.objects.get(id=1);

    #定义模板上下文
    context = {'my_dict':my_dict,'my_list':my_list,'book':book};
    return render(request,'templatedemo/temp_var.html',context);



def temp_vars(request):
    '''模板标签'''
    #查找所有图书信息
    books = BookInfo.objects.all();
    return render(request,'templatedemo/temp_tags.html',{'books':books});




def temp_filter(request):
    '''模板标签'''
    #查找所有图书信息
    books = BookInfo.objects.all();
    return render(request,'templatedemo/temp_filter.html',{'books':books});



def temp_inherit(request):
    '''模板继承'''
    return render(request,'templatedemo/child.html')



def html_escape(request):
    '''html转义'''
    return render(request,'templatedemo/html_scape.html',{'content':'<h1>hello</h1>'})



def login(request):
    '''跳转到登录页'''
    return render(request,'templatedemo/login.html')


def login_check(request):
    '''登录逻辑处理'''
    username = request.POST.get('username');
    password = request.POST.get('password');
    remember = request.POST.get('remember');

    #2.进行登录的校验
    #实际开发,根据用户名和密码查找数据库
    #模拟:
    if username=='smart' and password=='123':
        #用户名密码正确 , 跳转到首页
        response = redirect('/template/change_pwd');
        #判断是否需要记住用户名
        if remember == 'on':
            #设置cookie , username,过期时间为一周
            response.session['username']=username;

        #记住用户登录状态
        #只有session中有isLogin,就认为用户已登录
        request.session['isLogin'] = True;

        return response;
    else:
        #用户名密码错误 , 跳转到登录页面
        return redirect('/template/login');




#登录状态判断的装饰器
def login_required(view_func):
    '''登录判断装饰器'''
    def wrapper(request,*view_args,**view_kwargs):
        #判断用户是否登录
        if request.session.has_key('isLogin'):
            #用户已登录,调用对应的视图
            return view_func(request,*view_args,**view_kwargs);
        else:
            #用户未登录,转到登录页
            return redirect('/template/login');
    return wrapper;


@login_required
def change_pwd(request):
    '''显示修改密码页面'''
    #进行用户是否登录的限制
    #if not request.session.has_key('isLogin'):
    #   return redirect('/template/login');

    return render(request,'templatedemo/change_pwd.html')

@login_required
def change_pwd_action(request):
    '''模拟修改密码处理'''

    # 进行用户是否登录的限制
    #if not request.session.has_key('isLogin'):
    #    return redirect('/template/login');

    #1.获取新密码
    pwd = request.POST.get('pwd');
    #2.实际开发的时候,修改对应数据库的内容
    #3.返回一个应答
    return HttpResponse('修改密码为:%s' % pwd);
