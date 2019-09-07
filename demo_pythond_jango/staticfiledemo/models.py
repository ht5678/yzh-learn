from django.db import models

# Create your models here.

class AreaInfo(models.Model):
    '''地址模型类'''
    #地区名称
    atitle = models.CharField(verbose_name='标题',max_length=20);
    #自关联属性
    aParent = models.ForeignKey('self',null=True,blank=True);

    #可以用于admin的列表展示
    def __str__(self):
        return self.atitle;


    def title(self):
        return self.atitle;


    def parent(self):
        if self.aParent is None:
            return ''
        return self.aParent.atitle


    #排序
    title.admin_order_field='atitle'

    title.short_description='地区名称'

    class Meta:
        db_table = 'booktest_areainfo';  #指定模型类对应的表名

