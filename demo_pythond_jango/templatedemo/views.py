from django.shortcuts import render
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
