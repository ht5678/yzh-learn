from django.contrib import admin
from staticfiledemo.models import AreaInfo

# Register your models here.

#自定义模型管理类
class AreaInfoAdmin(admin.ModelAdmin):
    '''地区模型管理类'''
    list_per_page = 3  #指定每页显示10条数据
    list_display = ['id' , 'atitle' , 'title' , 'parent'];

    actions_on_bottom = True;
    actions_on_top = False;

    list_filter = ['atitle']    #列表页右侧g过滤栏
    search_fields = ['atitle']  #列表页上方的搜索框

admin.site.register(AreaInfo , AreaInfoAdmin);