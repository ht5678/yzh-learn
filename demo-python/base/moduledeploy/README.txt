这个包下的文件用于制作模块压缩包 :

#setup.py 文件不能再pycharm中执行, 只能在终端命令行中执行




步骤:
1.创建setup.py
2.构建模块          python setup.py build        , 会在目录下生成一个build文件夹
3.生成发布压缩包    python setup.py sdist        , 对模块进行压缩 , 生成dist目录,生成xxx.tar.gz




安装模块:
tar -zvxf xxx.tar.gz
sudo python setup.py install


删除模块:
import hm_messages

#打印出来模块所在的完整路径
print(hm_messages.__file__);



直接删除这2个:
目录:hm_messages
文件:hm_messages-1.0.egg-info




pip安装第三方模块:
例如:pygame是一个非常成熟的游戏开发模块

#pip是一个现代的,通用的python包管理工具:
#提供了对python包的查找,下载,安装,卸载等功能


安装和卸载的命令如下:
pip install pygame
pip uninstall pygame



