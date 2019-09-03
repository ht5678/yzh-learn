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