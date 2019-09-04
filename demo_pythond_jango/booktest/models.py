from django.db import models

# Create your models here.

#图书类
class BookInfo(models.Model):
    '''图书模型类'''
    #CharField表示一个字符串,max_length表示字符串的最大长度 , 带索引 , blank是用于后台管理添加修改时候的
    btitle = models.CharField(max_length=20,unique=True,db_index=True,db_column='btitle',null=False,blank=False);
    #价格
    bprice = models.DecimalField(max_digits=10,decimal_places=2);
    #出版日期,DateField说明是一个日期类
    bpub_date=models.DateField();
    #阅读量
    bread = models.IntegerField(default=0);
    #评论量
    bcomment = models.IntegerField(default=0);
    #删除标记
    isDelete = models.BooleanField(default=False);


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