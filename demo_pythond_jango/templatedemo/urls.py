from django.conf.urls import url
from templatedemo import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[

    url(r'^index$',views.index,name='index'),

    url(r'^temp_var$',views.temp_var),

    url(r'^temp_tags$',views.temp_vars),

    url(r'^temp_filter$',views.temp_filter),

    url(r'^temp_inherit$',views.temp_inherit),

    url(r'^html_escape$',views.html_escape),

    url(r'^change_pwd$',views.change_pwd),

    url(r'^change_pwd_action$',views.change_pwd_action),

    url(r'^login$', views.login),

    url(r'^login_check$', views.login_check),

    url(r'^verify_code$', views.verify_code),   #产生验证码图片

    url(r'^url_reverse$', views.url_reverse),   #url反向解析

    url(r'^show_args/(\d+)/(\d+)$', views.show_args , name='show_args'),

    url(r'^show_kwargs/(?P<c>\d+)/(?P<d>\d+)$', views.show_kwargs, name='show_kwargs'),

    url(r'^test_redirect$', views.test_redirect),

]
