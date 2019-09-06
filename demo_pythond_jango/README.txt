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


关联查询:
1.查询id为1的图书关联的英雄的信息
    b = BookInfo.objects.get(id=1);
    b.heroinfo_set.all()

通过模型类查询:
    HeroInfo.objects.filter(hbook_id=1);

2.查询id为1的英雄所属图书信息
    hero = HeroInfo.objects.get(id=1);
    hero.hbook

通过模型类查询
    BookInfo.objects.filter(heroinfo__id=1);



通过模型类实现关联查询:
1.查询图书信息,要求图书关联的英雄描述包含'八'
BookInfo.objects.filter(heroinfo__hcomment__contains='八');
2.


---------------------------------------------------------------------------------------------------------------


管理器:
BookInfo.objects.all() -> objects是一个什么东西呢?
objects是django帮我自动生成的管理器对象,通过这个管理器可以实现对数据的查询.

objects是models.Manager类的一个对象.自定义管理器之后Django不再帮我们生成默认的objects管理器

1.自定义一个管理器类,这个类继承models.Manager类
2.再在具体的模型类中定义一个自定义管理器类的对象.

class BookInfo(models.Model):
    ##自定义一个Manger类对象 . 自定义以后 , 再使用 BookInfo.objects 就会报错 . 就要用BookInfo.book.all()来查
    book = models.Manager();


自定义管理器类的引用场景:
1.改变查询的结果集
    比如调用BookInfo.books.all()返回的是没有删除的图书的数据
2.添加额外的方法
    管理器类中定义一个方法帮我们操作模型类对应的数据表
    使用self.model()就可以创建一个跟自定义管理器对应的模型类对象



---------------------------------------------------------------------------------------------------------------

元选项:
django默认生成的表名:
    应用名小写_模型类名小写
元选项:
    需要在模型类中定义一个元类Meta,在里面定义一个类属性 db_table  就可以指定表名


---------------------------------------------------------------------------------------------------------------

错误视图:
网站开发完成需要关闭调试模式,在settings.py文件中:
DEBUG=False
ALLOWED_HOST=['*']

---------------------------------------------------------------------------------------------------------------

捕获url参数:
1.位置参数 ,
位置参数,参数名可以随意指定
url(r'^book/(\d+)',views.detail),

2.关键字参数,在位置的基础上给正则表达式组命名
?P(组名)
关键字参数,视图中参数名必须和正则表达式组名一致
url(r'^book/(?P<num>\d+)',views.detail),

---------------------------------------------------------------------------------------------------------------

表单post提交 , 如果出现403 , 要在settings.py文件中注释csrf

ajax实现登录刷新页面.
需要引入js ,css文件 , 在demo_python_jango的settings.py文件中,最后一行.


#设置静态文件的保存目录
STATICFILES_DIRS=[os.path.join(BASE_DIR,'static')]


---------------------------------------------------------------------------------------------------------------


cookie和session的应用场景:
cookie : 记住用户名,安全性不高.
session: 涉及到安全性较高的数据,银行卡账户,密码


---------------------------------------------------------------------------------------------------------------

模板文件的加载顺序:
1.先去配置的模板目录下找模板文件
2.去settings.py的INSTALLED_APPS下面的每个应用去找模板文件,前提是应用中必须有templates文件夹.



模板变量的解析顺序:
例如 :  {{book.btitle}}
1.首先把book当成一个字典,把btitle当成键名,进行取值book['btitle']
2.把book当成一个对象,把btitle当成属性,进行取值book.btitle
3.把book当成一个对象,把btitle当成对象的方法,进行取值book.btitle

例如:  {{book.0}}
1.首先把book当成一个字典,把0当成键名,进行取值book['0']
2.把book当成一个列表,把0当成下标,进行取值book[0]

如果解析失败,产生内容时用空字符串填充模板变量.




模板标签:
{% 代码段 %}
for循环:
{% for x in 列表 %}
#列表不空时执行
{% empty %}
#列表为空时执行
{% endfor %}

可以通过{{forloop.counter}}得到for循环到了第几次
{% if 条件 %}
{% elif 条件 %}
{% else 条件 %}
{% endif 条件 %}

关系比较运算符:  >  <  >=  <=  ==  !=

注意:运算比较操作时,比较操作符两边必须要有空格
逻辑运算: not and or








模板过滤器:
过滤器用于对模板变量进行操作.
date:改变日期的显示格式
length:求长度,字符串.列表,元祖,字典长度
default:设置模板变量的默认值

格式:模板变量|过滤器:参数

自定义过滤器:

参考资料:官方文档API


