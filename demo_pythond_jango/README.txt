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



---------------------------------------------------------------------------------------------------------------


模板:
模板文件的使用
1.创建模板文件夹 , templates
2.配置模板目录
demo_python_jango/settings.xml  文件中有配置项:

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [os.path.join(BASE_DIR,'templates')], #设置模板文件目录
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

3.使用模板文件
    a.加载模板文件
        去模板目录下获取html文件内容,得到一个模板对象
    b.定义模板上下文
        向模板传递数据
    c.模板渲染
        得到一个标准的html内容



---------------------------------------------------------------------------------------------------------------

django   ORM  切换不同数据库

settings.py



DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'NAME': 'mydatabase',
        'USER': 'mydatabaseuser',
        'PASSWORD': 'mypassword',
        'HOST': '127.0.0.1',
        'PORT': '5432',
    }
}



需要安装mysql模块 ; pip install pymysql

然后在demo_python_jango/__init__.py里导入模块

---------------------------------------------------------------------------------------------------------------

模型类关系:
1.一对多关系
    图书类 - 英雄类
    models.ForeignKey()定义在多的类中
2.多对多关系
    新闻类 - 新闻类型类     体育新闻,国际
    models.ManyToManyField()    定义在哪个类中都可以
3.一对一关系
    员工基本信息类 - 员工详细信息类 . 员工工号
    models.OneToOneField定义在哪个类中都可以