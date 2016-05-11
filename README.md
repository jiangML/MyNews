# MyNews
MVP+Retrofit2.0+RxJava 实现的仿网易新闻

  该app采用了MVP模式开发，由于是第一次使用这个模式开发，所以可能还存在设计不好的地方。
  使用了retrofit2.0框架作为网络请求，结合RxJava和RxAndroid使用。
  其中使用了RxJava的concat和first操作符来解决数据缓存。timer操作符实现启动界面等。

  个人觉得retrofit和RxJava 真的是很不错的两个框架。特别是RxJava强大的操作符可以帮助我们实现以前很复杂的东西。解决了回调嵌套的麻烦。让复杂事情的逻辑变动更加清楚。
