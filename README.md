# MyNews
###MVP+Retrofit2.0+RxJava 实现的仿网易新闻

####该app采用了MVP模式开发，由于是第一次使用这个模式开发，所以可能还存在设计不好的地方。<br>
####  使用了retrofit2.0框架作为网络请求，结合RxJava和RxAndroid使用。
####  其中使用了RxJava的concat和first操作符来解决数据缓存。timer操作符实现启动界面等。<br>

####  个人觉得retrofit和RxJava 真的是很不错的两个框架。特别是RxJava强大的操作符可以帮助我们实现以前很复杂的东西。解决了回调嵌套的麻烦。让复杂事情的逻辑变动更加清楚。<br>

###用到的相关技术<br>
* 使用到了SQLite 数据库，由于本地数据库比较简单，使用就没有采用数据库框架。
* 后台数据来源是百度开放api  
* 还用到了SharedPreferences 文件操作，标识数据是否已经存在
* 网络层用Retrofit来换取数据。获取数据以后用RxJava对其进行相关操作，过滤，筛选。
* 主界面用到了android5.0以上的最新控件，Toolbar。
* 主界面时采用ViewPager+Fragment来显示各个新闻频道的数据
###个人感觉采用MVP模式开发app在某些方面的确比传统的MVC开发模式要好一些。MVP模式把数据、界面、逻辑（model+view+presenter）这三个模块进行了分离。presenter成为了连接model和view的中间件。分别定义model、view、presenter的接口。然后实现这些接口。在实现了presenten中引用实现了model、view的接口。在presenter中处理好了业务逻辑以后调用model的接口改变数据实体。同时调用view的接口刷新UI给用户反馈结果。这就是MVP模式的大楷流程。MVP的好处就是当view层改的时候我们不需要修改过多的代码就可以实现view的改变并且在Activity和Fragemnt中不在会有处理逻辑的相关代码，这样的话代码逻辑就更清楚。所以这一点是我觉得很好的地方。

####app启动界面<br>
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/login.png" width=400 height=710/><br>
####相关内容界面
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/1.png" width=400 height=710/> 
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/2.png" width=400 height=710/><br>
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/3.png" width=400 height=710/>
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/5.png" width=400 height=710/>
<img src="https://github.com/jiangML/MyNews/blob/master/raw/master/6.png" width=400 height=710/><br>
