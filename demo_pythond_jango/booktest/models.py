from django.db import models

# Create your models here.

#图书类
class BookInfo(models.Model):
    '''图书模型类'''
    #CharField表示一个字符串,max_length表示字符串的最大长度
    btitle = models.CharField(max_length=20);
    #出版日期,DateField说明是一个日期类
    bpub_date=models.DateField();
