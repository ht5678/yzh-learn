from django.shortcuts import render,redirect
from django.template import loader,RequestContext
from django.http import HttpResponse
from django.conf import settings
from staticfiledemo.models import PicTest

# Create your views here.

def static_test(request):
    '''跳转'''
    print(settings.STATICFILES_FINDERS)
    #加载静态文件的目录 , 和顺序
    #('django.contrib.staticfiles.finders.FileSystemFinder', 'django.contrib.staticfiles.finders.AppDirectoriesFinder')

    return render(request,'staticfiledemo/static_test.html')


EXCLUDE_IPS=['127.0.0.2']
def blocked_ips(view_func):
    def wrapper(request,*view_args,**view_kwargs):
        # 获取浏览器的ip地址
        user_ip = request.META['REMOTE_ADDR'];
        print(user_ip)
        if user_ip in EXCLUDE_IPS:
            return HttpResponse('<h1>FORBIDDEN</h1>');
        else:
            return view_func(request,*view_args,**view_kwargs);
    return wrapper;



#@blocked_ips
def index(request):
    '''首页'''
    print('-----index-----')

    #用于测试middleware的exception
    #num = 'a'+1;

    return render(request,'staticfiledemo/index.html')



def show_upload(request):
    '''显示上传图片'''
    return render(request,'staticfiledemo/upload_pic.html');


def upload_handle(request):
    '''上传图片处理'''
    #1.获取上传文件的处理对象
    pic = request.FILES["pic"];
    print(pic)
    print(type(pic));

    #2.创建一个文件
    save_path = "%s/staticfiledemo/%s"%(settings.MEDIA_ROOT,pic.name)
    print(save_path);
    with open(save_path,'wb') as f:
        #3.获取上传文件的内容并写到创建的文件中
        for content in pic.chunks():
            f.write(content)

    #4.在数据库中保存上传记录
    PicTest.objects.create(goods_pic='staticfiledemo/%s'%pic.name);

    #5.返回
    return HttpResponse('ok');