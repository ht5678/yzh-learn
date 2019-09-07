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

    #获取用户输入的验证码
    vcode1 = request.POST.get('vcode');
    #获取session中保存的验证码
    vcode2 = request.session.get('verifycode');

    print(vcode1)
    print(vcode2)

    #进行验证码校验
    if vcode1 != vcode2:
        #验证码错误
        return redirect('/template/login');

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






from PIL import Image, ImageDraw, ImageFont
from django.utils.six import BytesIO

def verify_code(request):
    #引入随机函数模块
    import random
    #定义变量，用于画面的背景色、宽、高
    bgcolor = (random.randrange(20, 100), random.randrange(
        20, 100), 255)
    width = 100
    height = 25
    #创建画面对象
    im = Image.new('RGB', (width, height), bgcolor)
    #创建画笔对象
    draw = ImageDraw.Draw(im)
    #调用画笔的point()函数绘制噪点
    for i in range(0, 100):
        xy = (random.randrange(0, width), random.randrange(0, height))
        fill = (random.randrange(0, 255), 255, random.randrange(0, 255))
        draw.point(xy, fill=fill)
    #定义验证码的备选值
    str1 = 'ABCD123EFGHIJK456LMNOPQRS789TUVWXYZ0'
    #随机选取4个值作为验证码
    rand_str = ''
    for i in range(0, 4):
        rand_str += str1[random.randrange(0, len(str1))]
    #构造字体对象，ubuntu的字体路径为“/usr/share/fonts/truetype/freefont”
    font = ImageFont.truetype('C://Windows//Fonts//arial.ttf', 23)
    #构造字体颜色
    fontcolor = (255, random.randrange(0, 255), random.randrange(0, 255))
    #绘制4个字
    draw.text((5, 2), rand_str[0], font=font, fill=fontcolor)
    draw.text((25, 2), rand_str[1], font=font, fill=fontcolor)
    draw.text((50, 2), rand_str[2], font=font, fill=fontcolor)
    draw.text((75, 2), rand_str[3], font=font, fill=fontcolor)
    #释放画笔
    del draw
    #存入session，用于做进一步验证
    request.session['verifycode'] = rand_str
    #内存文件操作
    buf = BytesIO()
    #将图片保存在内存中，文件类型为png
    im.save(buf, 'png')
    #将内存中的图片数据返回给客户端，MIME类型为图片png
    return HttpResponse(buf.getvalue(), 'image/png')




def url_reverse(request):
    '''url反向解析'''
    return render(request,'templatedemo/url_reverse.html');


def show_args(request , a , b):
    ''''''
    return HttpResponse(a+":"+b);



def show_kwargs(request , c , d):
    ''''''
    return HttpResponse(c+":"+d);


#报错但是可以用
from django.core.urlresolvers import reverse

def test_redirect(request):
    #重定向到/index
    #url = reverse('template:show_args',args=(1,2))
    #url = reverse('template:index')
    url = reverse('template:show_kwargs', kwargs={'c':3 ,'d':4})

    return redirect(url)
