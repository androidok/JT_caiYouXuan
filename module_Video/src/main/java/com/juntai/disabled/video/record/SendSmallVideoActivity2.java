package com.juntai.disabled.video.record;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.video.R;
import com.juntai.disabled.video.player.DisplayVideoActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;


/**
 * Created by jian on 2016/7/21 15:52
 * mabeijianxi@gmail.com
 */
public class SendSmallVideoActivity2 extends AppCompatActivity implements View.OnClickListener {
    private String videoUri;
    private TextView tv_send;
    private TextView tv_cancel;
    private String videoScreenshot;
    private ImageView iv_video_screenshot;
    private EditText et_send_content;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tv_cancel.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        et_send_content.setOnClickListener(this);
        iv_video_screenshot.setOnClickListener(this);
    }


    private void initData() {
        Intent intent = getIntent();
        videoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
        Bitmap bitmap = BitmapFactory.decodeFile( videoScreenshot);
        iv_video_screenshot.setImageBitmap(bitmap);
        et_send_content.setHint("您视频地址为:"+videoUri);
    }

    private void initView() {
        setContentView(R.layout.smallvideo_text_edit_activity);

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_send = (TextView) findViewById(R.id.tv_send);
        et_send_content = (EditText) findViewById(R.id.et_send_content);
        iv_video_screenshot = (ImageView) findViewById(R.id.iv_video_screenshot);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel) {
            hesitate();
        } else if (i == R.id.tv_send) {//确认
            Intent intent = new Intent();
            intent.setAction(ActionConfig.BROAD_VIDEO);
            intent.putExtra("videoUri",videoUri);
            intent.putExtra("videoScreenshotUri",videoScreenshot);
            sendBroadcast(intent);
            finish();
        } else if (i == R.id.iv_video_screenshot) {//播放
            startActivity(new Intent(this, DisplayVideoActivity.class).putExtra(
                    "playPath", videoUri));
        }
    }

    @Override
    public void onBackPressed() {
        hesitate();
    }

    private void hesitate() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setMessage(R.string.record_camera_exit_dialog_message)
                    .setNegativeButton(
                            R.string.record_camera_cancel_dialog_yes,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
//                                    FileUtils.deleteDir(getIntent().getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY));

                                }

                            })
                    .setPositiveButton(R.string.record_camera_cancel_dialog_no,
                            null).setCancelable(false).show();
        } else {
            dialog.show();
        }
    }

}
