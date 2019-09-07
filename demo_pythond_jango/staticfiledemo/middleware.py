from django.http import HttpResponse


class BlockedIPSMiddleware(object):
    '''中间件类'''
    EXCLUDE_IPS = ['127.0.0.2']

    def process_view(self,request,view_func,*view_args,**view_kwargs):
        '''视图调用之前会调用'''
        user_ip=request.META['REMOTE_ADDR'];
        if user_ip in BlockedIPSMiddleware.EXCLUDE_IPS:
            return HttpResponse('<h1>FORBIDDEN</h1>');