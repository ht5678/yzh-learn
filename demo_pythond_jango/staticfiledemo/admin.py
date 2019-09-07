from django.contrib import admin
from staticfiledemo.models import AreaInfo,PicTest


# Register your models here.

#关联对象, , 一对多的关系
class AreaStackedInline(admin.StackedInline):
    #写多类的名字
    model = AreaInfo


#表格的嵌入方式,展示方式
class AreaTabularInline(admin.TabularInline):
    model = AreaInfo
    extra=2

#自定义模型管理类
class AreaInfoAdmin(admin.ModelAdmin):
    '''地区模型管理类'''
    list_per_page = 3  #指定每页显示10条数据
    list_display = ['id' , 'atitle' , 'title' , 'parent'];

    actions_on_bottom = True;
    actions_on_top = False;

    list_filter = ['atitle']    #列表页右侧g过滤栏
    search_fields = ['atitle']  #列表页上方的搜索框


    #编辑页字段显示顺序
    #fields = ['aParent','atitle']
    #分组显示 , fieldsets和fields只能用一个
    fieldsets = (
        ('基本',{'fields':['atitle']}),
        ('高级', {'fields': ['aParent']})
    )

    #inlines = [AreaStackedInline]
    inlines = [AreaTabularInline]




admin.site.register(AreaInfo , AreaInfoAdmin);
admin.site.register(PicTest);