这个是基于python虚拟环境django==1.8.2的练习


后台管理(admin.py):
1.本地化
    语言和时区的本地化
    修改settings.xml文件

2.创建管理员
    命令:python manage.py createsuperuser
    然后可以启动server访问后台管理页面: http://127.0.0.1:8000/admin


3.注册模型类
    在应用下的admin.py中注册模型类
    告诉django框架根据注册的模型类来生成对应表管理页面
    b = BookInfo()
    str(b)  __str__


4.自定义管理页面
    自定义模型管理类.  模型管理类就是告诉django在生成的管理页面上显示哪些内容




---------------------------------------------------------------------------------------------------------------

视图:

urls.py --> booktest/urls.py --> booktest/views.py

urlpatterns的列表中,每一个配置项都调用url函数

    url函数有两个参数,第一个参数是正则表达式,第二个是对应的处理动作.

    配置url的时候,有两种语法格式:
    1.url(正则表达式,视图函数名)
    2.url(正则表达式,include(应用中的urls文件))

工作中在配置url的时候,首先要在项目中的urls.py文件中添加配置项,并不写具体的url和视图函数之间的对应关系.
而是包含具体应用的urls.py文件,在应用的urls.py文件中写url和视图函数的对应关系.



