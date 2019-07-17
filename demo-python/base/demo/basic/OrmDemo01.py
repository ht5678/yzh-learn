class ModelMetaclass(type):
    def __new__(cls, name , bases , attrs):
        mappings = dict();
        #判断是否需要保存
        for k,v in attrs.items():
            #判断是否是指定的StringField或者IntegerField的实例对象
            if isinstance(v , tuple):
                #print("Found mappings :%s => %s" % (k,v));
                mappings[k] = v;

        #删除这些已经在字典中存储的属性
        for k in mappings.keys():
            attrs.pop(k);

        #将之前的uid/name/email/password以及对应的对象引用,类名字
        attrs['__mappings__'] = mappings;   #保存属性和列的对应关系
        attrs['__table__'] = name;          #假设表名和类名一致
        return type.__new__(cls , name ,bases , attrs);




class User(metaclass=ModelMetaclass):
    uid = ('uid','int unsigned');
    name = ('username' , 'varchar(30)');
    email = ('email' , 'varchar(30)');
    password = ('password' , 'varchar(30)');

    #当指定元类以后,以上的类属性将不在类中,而是在__mappings__属性指定的字典中存储
    #以上User类中有
    #__mappings__ = {
    #       'uid':  ('uid', 'int unsigned'),
    #       'name': ('username', 'varchar(30)'),
    #       'email':('email','varchar(30)'),
    #       'password':('password','varchar(30)')
    # };
    #__table__ = 'User';

    def __init__(self , **kwargs):
        for name, value in kwargs.items():
            setattr(self , name , value);



    def save(self):
        fields = [];
        args = [];
        for k, v in self.__mappings__.items():
            fields.append(v[0]);
            args.append(getattr(self , k , None));

        #sql = "insert into %s (%s) values (%s)" % (self.__table__ ,','.join(fields) , ','.join([str(i) for i in args]));

        args_temp = list();
        for temp in args:
            #判断是否是数字类型
            if isinstance(temp , int):
                args_temp.append(str(temp));
            elif isinstance(temp , str):
                args_temp.append("""'%s'""" % temp);
        sql = "insert into %s (%s) values (%s)" % (self.__table__ ,','.join(fields) , ','.join([str(i) for i in args_temp]));

        print(sql);


u = User(uid=12345,name='Michael',email='test@163.com',password='mypasword');
u.save();
