[TOC]
Vue + SpringBoot + MyBatis  
=========
# 后端
1.  在 IDEA 中创建项目
    ![](https://github.com/acjdxhs/demo/raw/master/img_folder/step1.png)
    ![](https://github.com/acjdxhs/demo/raw/master/img_folder/step2.png)
    ![](https://github.com/acjdxhs/demo/raw/master/img_folder/step3.png)
    ![](https://github.com/acjdxhs/demo/raw/master/img_folder/step4.png)
    ![](https://github.com/acjdxhs/demo/raw/master/img_folder/step5.png)
2.  创建文件并输入代码，见仓库。
3.  配置连接 MySQL 数据库
    *   首先需要安装 MySQL 数据库，这里不做展开。
    *   安装好MySQL后，登入数据库，建立数据库和表，并插入一个数据  
        <pre><code>    
        create database demo;     
        use demo;  
        create table user( 
            username varchar(20) primary key, 
            password varchar(20)
        );
        insert into user(username, password) values("admin","123456");    
        </code></pre>  
    *   在 src/main/resources/application.properties 文件中加入
        <pre><code> 
        # mysql配置
        spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo      #demo是数据库名
        spring.datasource.username=root                             #root是数据库登陆账号
        spring.datasource.password=password                         #password是数据库密码
        spring.datasource.driver-class-name=com.mysql.jdbc.Driver  
        </code></pre>
4.  运行 DemoApplication.java
5.  在浏览器中输入 http://localhost:8080/login/?username=admin&password=123456，会看到登陆成功
6.  当上面这个请求到来时，会发生什么？
    *   首先，框架扫描控制器，发现在 LoginController 中的 Login 方法能够匹配到 url 中的 "/login" （这在 RequestMapping 中定义），因此 
        Login 方法会被调用。
    *   接着请求 ? 后面的参数会转化为 User 对象，作为 Login 方法的参数
    *   Login 方法会调用 LoginService 的 verify 函数
    *   verify 函数调用 UserMapper 中 getByName 方法
    *   注意到 UserMapper 是一个接口，其 getByName 方法实际上是 resources/mapper/UserMapper.xml 
        中 id 为 getByName 的 \<select> 代码块，其中包含访问数据库的 SQL语句 
7.  在上面的描述中，仍然没有说明 UserMapper.java 是如何与 resources/mapper/UserMapper.xml 联系起来
    *   在 UserMapper.xml 文件中，包含如下代码            
        >\<mapper namespace="com.example.demo.mapper.UserMapper">  
            ... 省略   
         \<mapper>
    
        其中，namespace指定了 UserMapper.java 的位置，因此可以和其联系。
        但另一个问题在于框架如何找到 UserMapper.xml 文件
    *   在 resources/application.properties 中，有如下代码
        >\# mybatis配置  
         mybatis.type-aliases-package=com.example.demo.model  
         mybatis.mapper-locations=classpath:mapper/*Mapper.xml       
        
        其中，mybatis.mapper-locations定义了框架扫描 Mapper.xml 的位置，
        因此解决了步骤 1 中的问题。  
        注意到，上面还有 mybatis.type-aliases-package 配置，这个配置定义了
        别名，即可以直接用 User 代表其全限定名 com.example.demo.model.User
        这在 Mapper.xml 中会用到。
    *    在 UserMapper.java 中的方法对应着 UserMapper.xml 中的 id。因此
            调用 UserMapper.java 中给的方法时会执行 UserMapper.xml 中对应 id 的
            SQL 语句。  
    
#前端
### Vue 安装
1.	获得cnpm，目的是利用淘宝的镜像，而不是使用国外的资源，这样下载依赖包的时候会快很多
        > npm install -g cnpm –registry=https://registry.npm.taobao.org
2.	全局安装 vue-cli （vue-cli 是一个官方发布 vue.js 项目脚手架，使用 vue-cli 可以快速创建 vue 项目）
        > cnpm install --global vue-cli
3.	创建项目   
        > 在命令行模式进入 demo 文件夹（为后端文件所在的目录）下，输入   
          vue init webpack frontend
    
        注：  
        ① webpack 是模板名称，可以到 vue.js 的 GitHub 上查看更多的模板https://github.com/vuejs-templates  
        ② 在模板安装的交互式界面中，可以先不选择安装vue-router， 后面手动安装来更好的学习。  
        ![](https://github.com/acjdxhs/demo/raw/master/img_folder/frontend.png)
        ③ 此时文件目录结构为 
        ![](https://github.com/acjdxhs/demo/raw/master/img_folder/directory.png)
            
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
    组件使用可查阅http://element.eleme.io/#/zh-CN/component/layout，在这里用其实现一个简单界面，
    代码见 frontend/src/view/Login.vue
    
### 使用 vue-router
1.  vue-router 安装
    > cnpm install vue-router -S
2.  在 frontend/src 下新建文件 router.js，其中包含路由配置
    <pre><code>
    import Vue from 'vue'
    import VueRouter from 'vue-router'  // 引入 vue-router
    import Login from './view/Login'
    
    Vue.use(VueRouter)      //Vue 使用 vue-router
    
    export default new VueRouter ({
      routes: [
        {
          path: '/',        // 当路径匹配到"/"时，就会渲染 Login 组件，那在哪里渲染呢？是在下面介绍的 router-view 标签中
          name: 'Login',
          component: Login
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
5.  现在输入 <code> npm run dev </code> 即可访问前端，在浏览器输入 <code>localhost:8080/Login</code>，
    注意此时不要打开后端，因为这与后端都跑在 8080 端口，会冲突，后面会更改。
5.  更多可以参考 https://www.cnblogs.com/SamWeb/p/6610733.html            
# 三、前后端连接
1.  现在我们有一个后端，从 LoginController 中可以看出它给出的接口有两个
    *   localhost:8080/login    其参数包含 username, password
    *   localhost:8080/register 其参数包含 username, password
2.  前端使用 axios 访问后端
    *   引入axios
        1.  安装axios，工作目录为 ../frontend
            > cnpm install axios -S 
        2.  在main.js中引入
            > import Axios from ‘axios’  
              Vue.prototype.$http=Axios            
    *   因为前后端服务都跑在 8080 端口，因此将前端服务的端口修改为 8181
        >   在 frontend/config/index.js 中将 port 值改为 8081
    *   因为前后端是跑在两个服务上，因此前端要访问后端需要跨域  
        1.  配置代理转发表，在config/index.js文件内，找到dev下的proxyTable，添加如下内容
            <pre><code>
            proxyTable: {
                  '/api': {
                    target: 'http://localhost:8080',
                    changeOrigin: true,
                    pathRewrite: {"^/api" : ""}
                  }
            },
            </code></pre>   
            这样，以/api开头的请求就会被代理，如/api/login就会成为localhost:8080/login（注意，这里/api不见了是因为如上代码中将路径重写了）  
        2.  注意到通过上面的配置，当需要访问后端localhost:8080/login时，需要在前端使用this.$http.get('/api/get')，这个/api显得有点多余，
            可以在main.js中添加一句 Axios.defaults.baseURL=’/api’，这样直接this.$http.get(/get)就好了
