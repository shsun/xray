## iBas4J��Ŀ���

- iBase4J��Java���Եķֲ�ʽϵͳ�ܹ��� ʹ��Spring���Ͽ�Դ��ܡ�
- ʹ��Maven����Ŀ����ģ�黯���������Ŀ���׿����ԡ���չ�ԡ�
- ϵͳ��������ģ�飺����ģ�顢ϵͳ����ģ�顢Webչʾģ�顣
- ����ģ�飺��������(AOP�����桢���ࡢ���ȵȵ�)���������á������ࡣ
- ϵͳ����ģ�飺�����û�����Ȩ�޹��������ֵ䡢ϵͳ��������ȵȡ�
- ÿ��ģ�鶼�Ƕ�����ϵͳ���������޵���չģ�飬ģ��֮��ʹ��Dubbo��MQ����ͨ�š�
- ÿ��ģ������ϵͳ����ע�ᵽͬһ��Zookeeper��Ⱥ����ע�����ģ�ʵ�ּ�Ⱥ����

## ��Ҫ����
 1. ���ݿ⣺Druid���ݿ����ӳأ�������ݿ�������ܣ�ͳ��SQL��ִ�����ܡ� ���ݿ�������ܣ����ܷ�ʽ��鿴PropertiesUtil��decryptProperties����������Ҫ���ܵ�key��
 2. �־ò㣺mybatis�־û���ʹ��MyBatis-Plus�Ż�������sql��������aop�л����ݿ�ʵ�ֶ�д���롣Transtractionע������
 3. MVC�� ����spring mvcע��,Rest���Controller��Exceptionͳһ����
 4. ���ȣ�Spring+quartz, ���Բ�ѯ���޸����ڡ���ͣ��ɾ��������������ִ�У���ѯִ�м�¼�ȡ�
 5. ����session�Ĺ��ʻ���ʾ��Ϣ��ְ����ģʽ�ı�������������,Shiro��¼��URLȨ�޹����Ự����ǿ�ƽ����Ự��
 6. �����Session��ע��redis�������ݣ�Spring-session��redisʵ�ֲַ�ʽsessionͬ������������Ự����ʧ��
 7. ��ϵͳ������Dubbo,ActiveMQ��ϵͳ������ftp/sftp/fastdafs�����ļ���������������ʹ�ļ�������롣
 8. ǰ��˷��룺û��Ȩ�޵��ļ�ֻ��nginx�����ɡ�
 9. ��־��log4j2��ӡ��־��ҵ����־�͵�����־�ֿ���ӡ��ͬʱ����ʱ����ļ���С�ָ���־�ļ���
 10. QQ��΢�š�����΢����������¼��
 11. �����ࣺexcel���뵼��������תƴ�������֤������֤������ת��д����ң�FTP/SFTP/fastDFS�ϴ����أ������ʼ���redis���棬���ܵȵȡ�

## ����ѡ��
    �� ���Ŀ�ܣ�Spring Framework 4.3.0 + Dubbo 2.5.3
    �� ��ȫ��ܣ�Apache Shiro 1.2
    �� ������ȣ�Spring + Quartz
    �� �־ò��ܣ�MyBatis 3.4 + MyBatis-Plus 2.0
    �� ���ݿ����ӳأ�Alibaba Druid 1.0
    �� �����ܣ�Redis
    �� �Ự����Spring-Session 1.3.0
    �� ��־����SLF4J��Log4j2
    �� ǰ�˿�ܣ�Angular JS + Bootstrap + Jquery

## ����˵��
    * ��Ŀ����activemq��Redis��ZooKeeper����
    * ����SYS-Service���clean package -P build tomcat7:run-war-only -f pom-sys-service-server.xml
    * ����Web���clean package -P build tomcat7:run-war-only -f pom-sys-web-server.xml
    * ʹ��nginx����UI���޸��������UIĿ¼������nginx��
    
## ��Ȩ����
iBase4Jʹ�� [Apache License 2.0][] Э��.

## ����
�����ȿ�һ�²��ǰ�İ汾����ַ��[iBase4J-old][].

## ����QQȺ[538240548](http://shang.qq.com/wpa/qunwpa?idkey=b0fb32618d54e6a7f3cb718cd469b2952c8a968b1ef6f17fd68c83338ae4bce3) [498085331](http://shang.qq.com/wpa/qunwpa?idkey=0a7344955bb9b9f6e366d34be42c02709c933f308ab435b1a52ac17d40efdff5)
�����������⣬������Ŀ�ĵ���һ�������������񹤾ߡ�

![QQȺ](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/1464169485871.png "QQȺһ")
![QQȺ](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/1482378175294.png "QQȺ��")

## UIЧ��ͼ

![��¼](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/login.png "��¼")
![��ҳ](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/index.png "��ҳ")
![�ӿ�](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/swagger.png "�ӿ�")

## License
iBase4J is released under version 2.0 of the [Apache License][].

![����](http://git.oschina.net/iBase4J/iBase4J/raw/master/img/contribute.png "����")

[Apache License 2.0]: http://www.apache.org/licenses/LICENSE-2.0
[iBase4J-old]: http://git.oschina.net/iBase4J/iBase4J/tree/NoSplit
[Apache License]: http://www.apache.org/licenses/LICENSE-2.0s