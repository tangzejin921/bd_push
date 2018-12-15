package module;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tzj.bd.push.IPush;
import com.tzj.bd.push.OpenNotifiActivity;
import com.tzj.bd.push.PushReceiver;
import com.tzj.bd.push.demo.R;

import java.util.List;


public class ModuleActivity extends Activity implements View.OnClickListener{

    private TextView result;

    private IPush push = new IPush() {
        @Override
        public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
            StringBuffer sb = new StringBuffer("appid:").append(appid).append("\n")
                    .append("errCode:").append(errorCode).append("\n")
                    .append("userId:").append(userId).append("\n")
                    .append("channelId:").append(channelId).append("\n")
                    .append("requestId:").append(requestId).append("\n");
            Log.e("baidu_push",sb.toString());
            result.append(sb.toString());
        }
        @Override
        public void onUnbind(Context context, int errorCode, String requestId) {

        }
        @Override
        public void onSetTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {

        }
        @Override
        public void onDelTags(Context context, int errorCode, List<String> successTags, List<String> failTags, String requestId) {

        }
        @Override
        public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {

        }

        @Override
        public void onMessage(Context context, String message, String customContentString) {
            result.append("---------------------");
            StringBuffer sb = new StringBuffer("message:").append(message).append("\n")
                    .append("customContentString:").append(customContentString).append("\n");
            Log.e("baidu_push",sb.toString());
            result.append(sb.toString());
        }
        @Override
        public void onNotificationClicked(Context context, String title, String description, String customContentString) {

        }
        @Override
        public void onNotificationArrived(Context context, String title, String description, String customContentString) {
            result.append("---------------------");
            StringBuffer sb = new StringBuffer("title:").append(title).append("\n")
                    .append("description:").append(description).append("\n")
                    .append("customContentString:").append(customContentString).append("\n");
            Log.e("baidu_push",sb.toString());
            result.append(sb.toString());
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        result = (TextView) findViewById(R.id.result);
        PushReceiver.addPushListener(getClass().getSimpleName(),push);
        OpenNotifiActivity.start(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PushReceiver.removeListener(getClass().getSimpleName());
    }

}
