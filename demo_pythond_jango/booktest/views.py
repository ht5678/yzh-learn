from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader,RequestContext
from django.shortcuts import render
from booktest.models import BookInfo


def myRender(request,templatePath , contextDict={}):
    '''使用模板文件'''
    #使用模板文件
    #1.加载模板文件 , 模板对象
    temp = loader.get_template(templatePath)
    #2.定义模板上下文,给模板文件传递数据
    context = RequestContext(request,{});
    #3.模板渲染:产生标准的html内容
    resHtml = temp.render(context);
    #4.返回给浏览器
    return HttpResponse(resHtml);





# Create your views here.
#1.定义视图函数 , httprequest对象
#2.进行url配置,建立url地址和视图的对应关系
# 测试:  http://localhost:8000/index

def index(request):
    #进行处理,和M和T进行交互...
    #1
    #return HttpResponse('ok');
    #2
    #return myRender(request,'booktest/index.html');
    #3
    return render(request,'booktest/index.html',
                  {'content':'hello world' , 'list':list(range(1,9))});




def showBooks(request):
    '''显示图书信息'''
    #通过model查找图书表中的数据
    books = BookInfo.objects.all();
    #使用模板
    return render(request,'booktest/showBooks.html',{'books':books});



def detail(request,bid):
    '''查询图书关联英雄信息'''
    #根据bid查询图书信息
    book = BookInfo.objects.get(id=bid);
    #查询和book关联的英雄信息
    heros = book.heroinfo_set.all();
    return render(request,'booktest/detail.html',{'book':book,'heros':heros});
