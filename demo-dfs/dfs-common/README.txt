cd demo-dfs/demo-dfs-common
mvn protobuf:compile && mvn install



•方式一：在demo-dfs-common的pom.xml 中添加如下代码
<properties>
  <os.detected.classifier>osx-x86_64</os.detected.classifier>
</properties>
•方式二：全局配置Maven，不用修改代码，在你的Maven的settings.xml(通常在~/.m2/settings.xml)文件下添加如下代码
<profile>
  <id>apple-silicon</id>
  <properties>
    <os.detected.classifier>osx-x86_64</os.detected.classifier>
  </properties>
</profile>

 </activeProfiles>

启动NameNode

打开配置NameNode的配置文件，在项目根目录下conf目录存在一个namenode.properties文件，打开此文件，修改以下内容：
base.dir=/srv/demo-dfs/namenode  # 修改为你本机的一个路径
