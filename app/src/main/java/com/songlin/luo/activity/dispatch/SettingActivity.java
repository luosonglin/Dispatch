package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.songlin.luo.ui.material.ripple.MaterialRippleLayout;

import java.io.File;

/**
 * Created by luosonglin on 15/6/5.
 */
public class SettingActivity extends FragmentActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        headImage = (ImageView) findViewById(R.id.avatar_default);
        // xml initialization
        //findViewById(R.id.upload_head).setOnClickListener(this);
        findViewById(R.id.upload_head).setOnLongClickListener(this);
        findViewById(R.id.upload_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("更换头像")
                        .setIcon(R.drawable.context_head)
                        .setMessage("确定拍照更换头像吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                choseHeadImageFromCameraCapture();
                            }
                        })
                        .show();
            }
        });

        // static initialization
        View view = findViewById(R.id.test);
        MaterialRippleLayout.on(view)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

        view.setOnLongClickListener(this);
        view.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.accout_name);
        textView.setText("罗 X X");

        findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(change);

            }
        });

        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clean = new Intent(SettingActivity.this, CleanActivity.class);
                startActivity(clean);

            }
        });
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(feedback);
            }
        });
        findViewById(R.id.call_wrap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("联系作者")
                        .setIcon(R.drawable.context_call)
                        .setMessage("确定拨打作者电话吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                try {
                                    String phonenumber = "18817802295";
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    System.out.println(e.getStackTrace());
                                }
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(about);
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(this, "Short click", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "当前版本已是最新版本...   \n" +
                "升级版仍在开发中，请耐心等待...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.upload_head) {
            Toast.makeText(this, "Long click not consumed", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Long click and consumed", Toast.LENGTH_SHORT).show();
        return true;
    }

    //Error:Execution failed for task ':app:compileDebugJava'.
    //    > Compilation failed; see the compiler error output for details.

/*Error:Execution failed for task ':app:dexDebug'.
> com.android.ide.common.internal.LoggedErrorException: Failed to run command:
        /Users/luosonglin/Downloads/android-sdk-macosx/build-tools/22.0.1/dx --dex --no-optimize --output /Users/luosonglin/AndroidStudioProjects/Dispatch/app/build/intermediates/dex/debug --input-list=/Users/luosonglin/AndroidStudioProjects/Dispatch/app/build/intermediates/tmp/dex/debug/inputList.txt
        Error Code:
        2
        Output:
        UNEXPECTED TOP-LEVEL EXCEPTION:
        com.android.dex.DexException: Multiple dex files define Landroid/support/v7/app/ActionBar$LayoutParams;
        at com.android.dx.merge.DexMerger.readSortableTypes(DexMerger.java:596)
        at com.android.dx.merge.DexMerger.getSortedTypes(DexMerger.java:554)
        at com.android.dx.merge.DexMerger.mergeClassDefs(DexMerger.java:535)
        at com.android.dx.merge.DexMerger.mergeDexes(DexMerger.java:171)
        at com.android.dx.merge.DexMerger.merge(DexMerger.java:189)
        at com.android.dx.command.dexer.Main.mergeLibraryDexBuffers(Main.java:454)
        at com.android.dx.command.dexer.Main.runMonoDex(Main.java:303)
        at com.android.dx.command.dexer.Main.run(Main.java:246)
        at com.android.dx.command.dexer.Main.main(Main.java:215)
        at com.android.dx.command.Main.main(Main.java:106)*/

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageView headImage = null;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_head);
        headImage = (ImageView) findViewById(R.id.imageView);

        Button buttonLocal = (Button) findViewById(R.id.buttonLocal);
        buttonLocal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });

        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
            }
        });

    }*/

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image*//*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        /*if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }*/

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
}