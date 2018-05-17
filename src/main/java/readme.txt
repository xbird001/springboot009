Springboot启动流程分析

	1、Springboot启动的两种方式：
		1.1、静态方法启动
				ConfigurableApplicationContext  app = SpringApplication.run(App.class, args);
		1.2、new一个SpringApplication的实例，然后再调用run方法
				1.2.1、SpringApplication app = new SpringApplication(App.class);//判断运行环境、读取配置文件  
				1.2.2、ConfigurableApplicationContext context = app.run(args);
		***在静态方法内部其实也是通过1.2的方式来启动的，在静态方法内部new SpringApplication(sources).run(args);这样来启动的，
		***所以下面分析Springboot的启动流程时就按照1.2的方式进行分析
		
	2、Springboot的启动流程
		2.1、在new SpringApplication(xx.class)方法内部调用初始化方法initialize；initialize方法内部调用过程如下：
			2.1.1、设置当前项目的运行环境，判断是否为web环境：
					根据Class.forName的方式来加载项目类路劲中是否有"javax.servlet.Servlet","org.springframework.web.context.ConfigurableWebApplicationContext"来判断是否为WEB环境；
			2.1.2、给SpringApplication的initializers属性赋值：
					在项目类路径下的META-INF/spring.factoriess搜索所有实现了ApplicationContextInitializer接口的子类，并赋值给SpringApplication的initializers属性；
			2.1.3、给SpringApplication的listeners属性赋值：
					在项目类路径下的META-INF/spring.factoriess搜索所有实现了ApplicationListener接口的子类，并赋值给SpringApplication的listeners属性；
			2.1.4、给SpringApplication的mainApplicationClass属性赋值：
					获取当前main方法所属的类的全类名
			***在2.1.2、2.1.3中还只是搜寻到实现了相应接口的子类，还并么有纳入到Spring的容器中进行管理，其实也很简单,此时Spring的xxxContext还未赋值
			***在2.1.2、2.1.3过程中有一个很重要的方法调用getSpringFactoriesInstances(Xxx.class)根据给定的Xxx.class类型的全类名作为Key值，在项目的类路径下的搜寻所有
			***META-INF/spring.factoriess的配置文件，从中找出给定Key值对应的Value，而这个给定的Value可能会包含多个接口实现类，用逗号隔开，找到好通过反射进行实例化，并
			***赋值给制定的SpringApplication属性
		2.2、调用SpringApplication实例对象的run方法，过程如下：