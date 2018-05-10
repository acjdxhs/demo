[TOC]
# Vue + SpringBoot + MyBatis  
  
## 一、 Vue
### Vue 安装
1.	获得cnpm，目的是利用淘宝的镜像，而不是使用国外的资源，这样下载依赖包的时候会快很多
        > npm install -g cnpm –registry=https://registry.npm.taobao.org
2.	全局安装 vue-cli （vue-cli 是一个官方发布 vue.js 项目脚手架，使用 vue-cli 可以快速创建 vue 项目）
        > cnpm install --global vue-cli
3.	创建项目   
        > 先创建文件夹命名为 demo，之后进入文件夹输入   
          vue init webpack frontend
    
        注：  
        ① webpack 是模板名称，可以到 vue.js 的 GitHub 上查看更多的模板https://github.com/vuejs-templates  
        ② 在模板安装的交互式界面中，可以先不选择安装vue-router， 后面手动安装来更好的学习。
            
4.	进入Vue项目，安装依赖
        > cd frontend  
          cnpm install
5.	启动项目
        > npm run dev
6.	项目完成后，进行打包 (打包完成后，会生成 dist 文件夹, 项目上线时，只需要将 dist 文件夹放到服务器就行了)
        > npm run build
### Vue文件说明
1.  bulid：配置了webpack的基本配置、开发环境配置、生产环境配置等
2.  config：包含项目基本配置文件，其中index.js 设定了端口和其它常用的值
3.  node_modules：包含了下载的依赖模块，在 cnpm install 后产生
4.  src：放置组件和入口文件
    * assets：包含图片的资源文件
    * App.vue：根组件
    * main.js：是入口文件，主要作用是初始化Vue实例，并使用我们需要的插件
6.  package.json：包含依赖设置
### 在Intellij 中配置Vue 支持
1.  打开File->Settings->Plugins窗口,在查询框输入vue，点击安装。
2.  点击重启IntellIJ IDEA
3.  配置支持.vue后缀文件。打开File->Settings->Editor->File Types窗口，在右侧面板选中HTML，在下方Registered Patterns面板增加 *.vue
4.  配置支持 ECMAScript。打开File->Settings->Languag & Framworks->JavaScript，在右侧面板JavaScript language version中选择ECMAScript 6   
### 使用element-ui
element-ui是Vue的一个组件库，其中丰富的组件可以让Vue的开发更简便和高效
1.  安装 element-ui 模块
    >cnpm install element-ui -S  
    
    其中 -S 的作用是将 element-ui 记录在package.json文件中，等同于--save
2.  Vue 使用element-ui  
    在 main.js 文件中加入
    >import ElementUI from 'element-ui' //引入组件库  
     import 'element-ui/lib/theme-chalk/index.css';  // 引入样式  
     Vue.use(ElementUI)
3.  使用  
    组件使用可查阅http://element.eleme.io/#/zh-CN/component/layout
### 使用 vue-router
1.  vue-router 安装
    > cnpm install vue-router -S
2.  在 frontend/src 下新建文件 router.js，其中包含路由配置
    <pre><code>
    import Vue from 'vue'
    import VueRouter from 'vue-router'  // 引入 vue-router
    import HelloWorld from './components/HelloWorld'
    
    Vue.use(VueRouter)  // Vue 使用 vue-router
    
    export default new VueRouter ({
      routes: [     
        {
          path: '/',         // 当路径匹配到"/"时，就会渲染 HelloWorld 组件，那在哪里渲染呢？是在下面介绍的 router-view 标签中                  
          name: 'HelloWorld',
          component: HelloWorld
        }
      ]
    })   
    </code></pre>
    
    路由中有三个基本的概念 route, routes, router
    * route，它是一条路由，由这个英文单词也可以看出来，它是单数， Home按钮  => home内容， 这是一条route,  about按钮 => about 内容， 这是另一条路由。      
    * routes 是一组路由，把上面的每一条路由组合起来，形成一个数组。\[{home 按钮 =>home内容 }， { about按钮 => about 内容}] 
    * router 是一个机制，相当于一个管理者，它来管理路由。因为routes 只是定义了一组路由，它放在哪里是静止的，当真正来了请求，怎么办？ 就是当用户点击home 按钮的时候，怎么办？这时router 就起作用了，它到routes 中去查找，去找到对应的 home 内容，所以页面中就显示了 home 内容。
3.  因为main.js是文件入口，所以要使用上面的router.js中的配置，需要在 main.js 文件中加入
    > import router from './router' //新增的   
      new Vue({  
        el: '#app',  
        router,  //新增的  
        components: { App },  
        template: '<App/>'  
      })             
4.  在 App.vue 中将
    > <HelloWorld\>\</HelloWorld>  

    替换为
    > <router-view\>\</router-view>
    
    这就是在步骤 2 中提到的<router-view>标签 
5.  更多可以参考 https://www.cnblogs.com/SamWeb/p/6610733.html
          

## 二、 SpringBoot
1.  在 src/main/java/com.example.demo目录下创建以下四个包
    > controller  
      service  
      mapper    相当于 DAO 层，之后 MyBatis 中会讲到  
      model  
2.  包中的代码见仓库，在此仅讲述当请求到来后的信息流
    *   当请求 http://localhost:8080/login?username=admin&password=123456到来
    *   LoginController 根据 RequestMapping 规则匹配到 login 就会调用 Login 方法，
        并将请求参数转化为 User 对象
    *   Login 再调用 Service 层的 verify 方法
    *   verify 使用 mapper 包中 UserMapper 的方法
    *   而 UserMapper 接口又与 resources/mapper下的 UserMapper.xml 联系起来（具体怎么联系见 MyBatis一节）
    *   mapper.xml 中封装了访问数据库的语句
3.  从代码中可以看到频繁出现注解，我将一些常用注解整理在“注解.docx”文件中，不过待进一步补充
4.  MySQL  
    MySQL安装在此不做介绍。安装好MySQL后，登入数据库，建立数据库和表  
    <pre><code>    
    create database demo;     
    use demo;  
    create table user( 
        username varchar(20) primary key, 
        password varchar(20)
    );     
    </code></pre>  
5.  连接 MySQL配置
    在 src/main/resources/application.properties 文件中加入
    <pre><code> 
    # mysql配置
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo      #demo是数据库名
    spring.datasource.username=root                             #root是数据库登陆账号
    spring.datasource.password=password                         #password是数据库密码
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver  
    </code></pre>
    这样配置之后，就能够访问到数据库数据了
## 三、 MyBatis
通过上面的连接数据库配置，确实可以连接到数据库，不过使用
常规的JAVA方式访问数据库时，会产生大量的JDBC代码，而使用MyBatis可以大大简化它。  
MyBatis 的概念、配置和语法可以参考 https://www.w3cschool.cn/mybatis/
这里将讲述如何将 com.example.demo/mapper 中的 UserMapper 和 resources/mapper/UserMapper.xml联系起来
1.  在 UserMapper.xml 文件中，包含如下代码
    
    >\<mapper namespace="com.example.demo.mapper.UserMapper">  
        ... 省略   
     \<mapper>
    
    其中，namespace指定了 UserMapper.java 的位置，因此可以和其联系。
    但另一个问题在于框架如何找到 UserMapper.xml 文件
2.  在 resources/application.properties 中，有如下代码
    >\# mybatis配置  
     mybatis.type-aliases-package=com.example.demo.model  
     mybatis.mapper-locations=classpath:mapper/*Mapper.xml       
    
    其中，mybatis.mapper-locations定义了框架扫描 Mapper.xml 的位置，
    因此解决了步骤 1 中的问题。  
    注意到，上面还有 mybatis.type-aliases-package 配置，这个配置定义了
    别名，即可以直接用 User 代表其全限定名 com.example.demo.model.User
    这在 Mapper.xml 中会用到。
3.  在 UserMapper.java 中的方法对应着 UserMapper.xml 中的 id。因此
    调用 UserMapper.java 中给的方法时会执行 UserMapper.xml 中对应 id 的
    SQL 语句。
     
## 四、前后端连接

