from django.conf.urls import url
from staticfiledemo import views


#在应用的urls文件中进行url配置的时间
#1.严格匹配开头和结尾
urlpatterns=[

    url(r'^static_test$',views.static_test),

    url(r'^index$', views.index),

    url(r'^show_upload', views.show_upload),

    url(r'^upload_handle', views.upload_handle),

    #url(r'^media/(?P<path>.*)$', serve, {"document_root": MEDIA_ROOT}),

]