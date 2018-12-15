# 百度



## 推送网址
>http://push.baidu.com/doc/android/api

## 【SDK版本更新】Android SDK 6.1.1
    时间：2018年01月29日
    Change Log：
    1. 增强安全校验，进一步提升安全性；
    2. 优化统计机制，降低性能消耗；
    3. 精简部分逻辑，优化PushService启动；
    4. 修复已知bug，进一步提升稳定性
    
    升级提示：
    强烈建议开发者升级。
    
    升级指南：
    步骤一：版本升级
    
    使用4.4.0及以后版本的开发者请直接参照步骤二和步骤三；
    使用4.3.0及以前版本的开发者，请阅读SDK下载包docs目录下的《升级指南》。
    
    步骤二：更改回调Receiver和Manifest.xml
    
    5.8.0版本适配Android O。
        如果开发者想要自定义Android O系统上通知Channel，请参考用户手册和PushDemo示例说明。（可选）
    
    5.2.0版本的AndroidManifest.xml中PushInfoProvider新增权限级别，如下所示：
        <provider
          android:name="com.baidu.android.pushservice.PushInfoProvider"
          android:authorities=" YourPackageName.bdpush"
          android:writePermission="baidu.push.permission.WRITE_PUSHINFOPROVIDER. YourPackageName "
          android:protectionLevel = "signature"
          android:exported="true" />
    
    5.1.0版本新增了PushInfoProvider ContentProvider的声明,并增加了ContentProvider的写权限声明，示例如下：
    <!-- 适配Android N系统必需的ContentProvider写权限声明，写权限包含应用包名-->
        <uses-permission android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName" />
        <permission
            android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
            android:protectionLevel="normal">
        </permission>
    
    <!-- 适配Android N系统必需的ContentProvider声明，写权限包含应用包名-->
        <provider
        android:name="com.baidu.android.pushservice.PushInfoProvider"
        android:authorities="YourPackageName.bdpush"
        android:writePermission="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
        android:exported="true" />
    
    4.6.3版本去掉了PushKeepAlive Activity的声明，如果您声明了该Activity，请删除。
    4.5.1版本去掉了Frontia相关类，如果您初始化了FrontiaApplication，请删除。
    4.5.1版本的自定义Receiver继承父类修改为PushMessageReceiver。
    4.4.1版本的自定义Receiver中增加回调函数onNotificationArrived，声明如下：
        public void onNotificationArrived(Context context, String title, String description, String customContentString) { }
    
    4.4.0版本的AndroidManifest.xml中增加一个必选Service声明：
    <service android:name="com.baidu.android.pushservice.CommandService"
    android:exported="true" />
    
    步骤三：更新so库和jar包
    
    1、请将libs目录下armeabi目录下原有的libbdpush_V*_*.so，替换为最新的libbdpush_V2_9.so。
    注：如果你的工程中还使用了其他的.so文件，只需要复制云推送对应目录下的so文件。
    
    2、请将jar包替换为最新的pushservice-6.1.1.21.jar。


## 导入SDK 

    
## provider
    <!-- 适配Android N系统必需的ContentProvider声明，写权限包含应用包名-->
    <provider
        android:name="com.baidu.android.pushservice.PushInfoProvider"
        android:authorities="YourPackageName.bdpush"
        android:writePermission="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
        android:protectionLevel = "signature"
        android:exported="true" />
## 添加服务
    <!-- push service start -->
        <!-- 用于接收系统消息以保证PushService正常运行 -->
        <receiver android:name="com.baidu.android.pushservice.PushServiceReceiver"
            android:process=":bdservice_v1" >
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                <action android:name="com.baidu.android.pushservice.action.notification.SHOW" />
                <action android:name="com.baidu.android.pushservice.action.media.CLICK" />
                <!-- 以下四项为可选的action声明，可大大提高service存活率和消息到达速度 -->
                <action android:name="android.intent.action.MEDIA_MOUNTED" />
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
                <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED" />
            </intent-filter>
        </receiver>
        <!-- Push服务接收客户端发送的各种请求-->
        <receiver android:name="com.baidu.android.pushservice.RegistrationReceiver"
            android:process=":bdservice_v1" >
            <intent-filter>
                <action android:name="com.baidu.android.pushservice.action.METHOD" />
                <action android:name="com.baidu.android.pushservice.action.BIND_SYNC" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_REMOVED" />
                <data android:scheme="package" />
            </intent-filter>
        </receiver>
        <service android:name="com.baidu.android.pushservice.PushService" android:exported="true"
            android:process=":bdservice_v1" >
            <intent-filter >
                    <action android:name="com.baidu.android.pushservice.action.PUSH_SERVICE" />
            </intent-filter>
        </service>
        
        <!-- 4.4版本新增的CommandService声明，提升小米和魅族手机上的实际推送到达率 -->
        <service android:name="com.baidu.android.pushservice.CommandService"
            android:exported="true" />
        
## 权限
    <!-- Push service 运行需要的权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- 富媒体需要声明的权限 -->
    <uses-permission android:name="android.permission.ACCESS_DOWNLOAD_MANAGER"/>
    <uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
    <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
    
    <!-- 适配Android N系统必需的ContentProvider写权限声明，写权限包含应用包名-->
    <uses-permission android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName" />
    <permission
            android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
            android:protectionLevel="signature">
    </permission>
# init
    PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"api_key")
        
## 混淆
     -libraryjars libs/pushservice-VERSION.jar
     -dontwarn com.baidu.**
     -keep class com.baidu.**{*; }
## 说明：
    在清单文件里加上
    <!--百度推送-->
    <uses-permission android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.com.jkwy.nj.skq" />
    <permission
        android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.com.jkwy.nj.skq"
        android:protectionLevel="signature"></permission>
    <!--百度推送 end-->
    <!--百度推送-->
    <provider
        android:name="com.baidu.android.pushservice.PushInfoProvider"
        android:authorities="com.jkwy.nj.skq.bdpush"
        android:exported="true"
        android:protectionLevel="signature"
        android:writePermission="baidu.push.permission.WRITE_PUSHINFOPROVIDER.com.jkwy.nj.skq" />
    <!--百度推送 END-->
## 遇到的坑
    新安装的app，没有打开通知导致，不弹通知，其实是通知到的