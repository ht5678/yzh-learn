from django.shortcuts import render,redirect
from django.template import loader,RequestContext
from django.http import HttpResponse
from django.conf import settings

# Create your views here.

def static_test(request):
    '''跳转'''
    print(settings.STATICFILES_FINDERS)
    #加载静态文件的目录 , 和顺序
    #('django.contrib.staticfiles.finders.FileSystemFinder', 'django.contrib.staticfiles.finders.AppDirectoriesFinder')

    return render(request,'staticfiledemo/static_test.html')