from django.conf.urls import url
from logindemo import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[

    url(r'^index$',views.index),

    url(r'^login_check$',views.login_check),     #用户登录校验



    url(r'^testAjax$',views.testAjax),

    url(r'^ajax_handle$',views.ajax_handle),

    url(r'^login_ajax$',views.login_ajax),

    url(r'^login_ajax_check$',views.login_ajax_check),

    #注意位置
    url(r'^login$',views.login),  #跳转到登录页面


]