

from distutils. core import setup

setup(name=" yuezh2 message",#包名
    version="1.0",#版本
    description=" yuezh2's发送和接收消息模块",#描信息
    long_description="完整的发送和接收消息模块",#完整描述信息
    author=" yuezh2",#作者
    author_email=" yuezh2,com",#作者邮箱
    urL="ww.yuezh2.com",#主页
    py_modules=["hm_messages.hm_receive_message","hm_messages.hm_send_message"])