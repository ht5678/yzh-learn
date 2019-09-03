from django.conf.urls import url
from booktest import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[
    #通过url函数设置url路由配置项
    url(r'^index',views.index),  #建立/index和视图index之间的关系



]