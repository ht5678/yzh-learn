from django.conf.urls import url
from cookiedemo import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[

    url(r'^setCookie$',views.setCookie),

    url(r'^getCookie$',views.getCookie),

    url(r'^setSession$', views.setSession),

    url(r'^getSession$', views.getSession),


]