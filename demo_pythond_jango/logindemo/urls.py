from django.conf.urls import url
from logindemo import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[
    url(r'^login',views.login),  #跳转到登录页面



]