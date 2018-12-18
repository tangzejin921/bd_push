import 'dart:async';

import 'package:flutter/services.dart';


class BdPushPlugin {
  static EventChannel _channel;
  static StreamSubscription _streamSubscription;
  static Map<int, BdPushPlugin> _map = Map();

  Function _onBind;
  Function _onUnbind;
  Function _onMessage;
  Function _onNotificationArrived;
  Function _onNotificationClicked;

  BdPushPlugin({
        ///onBind 并不是百度的 onBind 的回调
        void onBind(String appId, String userId, String channelId, String requestId),
        void onUnbind(String requestId),
        void onMessage(String message, String customContentString),
        void onNotificationArrived(String title, String description,String customContentString),
        void onNotificationClicked(String title, String description,String customContentString)
      }) {
    this._onBind = onBind;
    this._onUnbind = onUnbind;
    this._onMessage = onMessage;
    this._onNotificationArrived = onNotificationArrived;
    this._onNotificationClicked = _onNotificationClicked;
    _map.putIfAbsent(hashCode, () => this);
    if (_channel == null) {
      _channel = const EventChannel('BdPushPlugin');
      _streamSubscription = _channel.receiveBroadcastStream().listen((data) {
        switch(data['key'] as String){
          case "onBind":
            _map.forEach((_, listen) {
              listen?._onBind(data["appId"], data["userId"], data["channelId"],
                  data["requestId"]);
            });
            break;
          case "onUnbind":
            _map.forEach((_, listen) {
              listen?._onUnbind(data["requestId"]);
            });
            break;
          case "onMessage":
            _map.forEach((_, listen) {
              listen?._onMessage(data["message"], data["customContentString"]);
            });
            break;
          case "onNotificationArrived":
            _map.forEach((_, listen) {
              listen?._onNotificationArrived(
                  data["title"], data["description"],
                  data["customContentString"]);
            });
            break;
          case "onNotificationClicked":
            _map.forEach((_, listen) {
              listen?._onNotificationClicked(
                  data["title"], data["description"],
                  data["customContentString"]);
            });
            break;
        }
      });
    }
  }

  ///关闭 所有回调
  void close() {
    _streamSubscription.cancel();
    _map.clear();
    _streamSubscription = null;
    _channel = null;
  }

  ///去除当前的回调
  void remove() {
    _map.remove(hashCode);
  }

}