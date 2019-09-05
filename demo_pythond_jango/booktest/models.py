from django.db import models

# Create your models here.


class BookInfoManager(models.Manager):
    '''图书类型管理器类'''
    #1.改变查询的结果集
    def all(self):
        #1.调用父类的all,获取所有数据
        books = super().all();  #QuerySet
        #2.对数据进行过滤
        books = books.filter(isDelete=False);
        #3.返回books
        return books;

    #2.封装函数:操作模型类对应的数据表(增删改查)
    #BookInfo.objects.createBook(xx,xx,xx)
    def createBook(self , btitle, bpub_date):
        #1.创建一个图书对象
        #每次修改BookInfo , 比如改成了BookInfo1,就会要改
        #book = BookInfo();
        #获取self所在的模型类
        book = self.model();
        book.btitle = btitle;
        book.bpub_date=bpub_date;
        #2.保存进数据库
        book.save();
        #3.返回book
        return book;




#图书类
class BookInfo(models.Model):
    '''图书模型类'''
    #CharField表示一个字符串,max_length表示字符串的最大长度 , 带索引 , blank是用于后台管理添加修改时候的
    btitle = models.CharField(max_length=20,unique=True,db_index=True,db_column='btitle',null=False,blank=False);
    #价格
    bprice = models.DecimalField(max_digits=10,decimal_places=2,default=0);
    #出版日期,DateField说明是一个日期类
    bpub_date=models.DateField();
    #阅读量
    bread = models.IntegerField(default=0);
    #评论量
    bcomment = models.IntegerField(default=0);
    #删除标记
    isDelete = models.BooleanField(default=False);

    ##
    ##自定义一个Manger类对象 . 自定义以后 , 再使用 BookInfo.objects 就会报错 . 就要用BookInfo.book.all()来查
    #book = models.Manager();

    #自定义一个BookInfoManager类的对象 , type(BookInfo.objects) 就改变了
    objects = BookInfoManager()


    @classmethod
    def createBook(cls ,btitle ,bpub_date):
        #1.创建一个图书对象
        obj = cls();
        obj.btitle = btitle;
        obj.bpub_date = bpub_date;
        #2.保存进数据库
        obj.save();
        #返回对象
        return obj;



    def __str__(self):
        return self.btitle


#多类
#英雄人物类
#英雄名 hname
#性别 hgender
#年龄 hage
#备注 hcomment
#关系属性 hbook , 建立图书类和英雄人物类之间的一对多关系
class HeroInfo(models.Model):
    '''英雄人物模型类'''
    #英雄名称
    hname = models.CharField(max_length=20);
    #性别,BooleanField说明是bool类型,default指定默认值,False代表男
    hgender = models.BooleanField(default=False);
    #备注
    hcomment = models.CharField(max_length=128);
    #关系属性 hbook , 建立图书类和英雄人物类之间的一对多关系
    #在django2.0后，定义外键和一对一关系的时候需要加on_delete选项，此参数为了避免两个表里的数据不一致问题
    #on_delete有CASCADE、PROTECT、SET_NULL、SET_DEFAULT、SET()五个可选择的值
    #CASCADE：此值设置，是级联删除。
    #PROTECT：此值设置，是会报完整性错误。
    #SET_NULL：此值设置，会把外键设置为null，前提是允许为null。
    #SET_DEFAULT：此值设置，会把设置为外键的默认值。
    #SET()：此值设置，会调用外面的值，可以是一个函数。

    #关系属性对应的表的字段名格式:  关系属性_id
    hbook = models.ForeignKey('BookInfo');

    #删除标记
    isDelete = models.BooleanField(default=False);



#多对多
#新闻类型类
class NewsType(models.Model):
    #类型名
    typeName = models.CharField(max_length=20);
    #关系属性,代表类型下面的信息
    #typeNews = models.ManyToManyField('NewsInfo');


#新闻类
class NewsInfo(models.Model):
    #新闻标题
    title = models.CharField(max_length=128);
    #发布时间
    pubDate = models.DateTimeField(auto_now_add=True);
    #信息内容
    content = models.TextField();

    #关系属性,代表信息所属的类型
    news_type = models.ManyToManyField('NewsType');



#一对一
#员工基本信息类
class EmployeeBasicInfo(models.Model):
    #姓名
    name = models.CharField(max_length=20);
    #性别
    gender = models.BooleanField(default=False);
    #年龄
    age = models.IntegerField();



#员工详细信息表
class EmployeeDetailInfo(models.Model):
    #联系地址
    addr = models.CharField(max_length=256);
    #教育经历
    #关系属性,代表员工基本信息
    employee_basic = models.OneToOneField("EmployeeBasicInfo");




#自关联 , 比如  地区  省市
class AreaInfo(models.Model):
    '''地区模型类'''
    #地区名称
    atitle = models.CharField(max_length=20);
    #关系属性,代表当前地区的父及地区
    aParent = models.ForeignKey('self',null=True,blank=True);





#shell命令测试
# >>> from booktest.models import BookInfo,HeroInfo
# >>> b = BookInfo()
# >>> b.btitle = '天龙八部'
# >>> from datetime import date
# >>> b.bpub_date=date(1990,1,1);
# >>> b.save()
# >>>
# >>> h = HeroInfo()
# >>> h.hname='duanyu'
# >>> h.hgender=False
# >>> h.hcomment='六脉神剑'
# >>> h.hbook = b
# >>> h.save()

#查询
#b = BookInfo.objects.get(id=2)
#b = BookInfo.objects.get(btitle='天龙八部')
#修改
#b.bpub_date=date(1989,10,21);
#b.save();#才会更新表格中的数据

#删除
#b.delete();


#查询图书表里面的所有内容
#BookInfo.objects.all();
#HeroInfo.objects.all();

#如果不存在,会IndexError异常
#books[0]
#如果不存在,会DoesNotExist异常
#books3[0:1].get()

#exists:判断一个查询集中是否有数据 , True , False