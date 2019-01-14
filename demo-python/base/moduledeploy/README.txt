这个包下的文件用于制作模块压缩包 :

#setup.py 文件不能再pycharm中执行, 只能在终端命令行中执行




步骤:
1.创建setup.py
2.构建模块          python setup.py build        , 会在目录下生成一个build文件夹
3.生成发布压缩包    python setup.py sdist        , 对模块进行压缩 , 生成dist目录,生成xxx.tar.gz


