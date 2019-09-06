from django.shortcuts import render
from django.template import loader,RequestContext
from django.http import HttpResponse

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
