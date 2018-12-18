import 'package:bd_push/bd_push_plugin.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(new BdPushDemo());
}

class BdPushDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Flutter baidu push Demo',
      theme: new ThemeData(
        primarySwatch: Colors.cyan,
      ),
      home: new _EntryHome(),
    );
  }
}

class _EntryHome extends StatefulWidget {
  _EntryHome({Key key}) : super(key: key);

  @override
  State<_EntryHome> createState() => new _EntryHomeState();
}

class _EntryHomeState extends State<_EntryHome> {
  String _data = "====";


  void _onBind(String appId, String userId, String channelId, String requestId){
    _data+='\n _onBind----------\n appId=${appId}\n userId=${userId}\n channelId=${channelId}\n requestId=${requestId}';
    setState(() {
      _data = _data;
    });
  }
  void _onUnbind(String requestId){
    _data+='\n _onUnbind----------\n requestId=${requestId}';
    setState(() {
      _data = _data;
    });
  }
  void _onMessage(String message, String customContentString){
    _data+='\n _onMessage----------\n message=${message}\n customContentString=${customContentString}';
    setState(() {
      _data = _data;
    });
  }
  void _onNotificationArrived(String title, String description,String customContentString){
    _data+='\n _onNotificationArrived----------\n title=${title}\n description=${description}\n customContentString=${customContentString}';
    setState(() {
      _data = _data;
    });
  }
  void _onNotificationClicked(String title, String description,String customContentString){
    _data+='\n _onNotificationClicked----------\n title=${title}\n description=${description}\n customContentString=${customContentString}';
    setState(() {
      _data = _data;
    });
  }

  @override
  void initState() {
    print("initState");
    BdPushPlugin(
        onBind:_onBind,
        onUnbind:_onUnbind,
        onMessage:_onMessage,
        onNotificationArrived:_onNotificationArrived,
        onNotificationClicked:_onNotificationClicked
    );
    super.initState();
  }
  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    print("didChangeDependencies");
  }

  @override
  Widget build(BuildContext context) {
    print("build");
    return Scaffold(
      appBar: AppBar(
        title: const Text("bdPush demo"),
      ),
      body: Text(_data,textDirection:TextDirection.ltr),
    );
  }
  @override
  void didUpdateWidget(_EntryHome oldWidget) {
    super.didUpdateWidget(oldWidget);
    print("didUpdateWidget");
  }


}
