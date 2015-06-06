package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.songlin.luo.ui.material.ripple.MaterialRippleLayout;

/**
 * Created by luosonglin on 15/6/5.
 */
public class SettingActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        // xml initialization
        findViewById(R.id.upload_head).setOnClickListener(this);
        findViewById(R.id.upload_head).setOnLongClickListener(this);

        // static initialization
        View view = findViewById(R.id.test);
        MaterialRippleLayout.on(view)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

        view.setOnLongClickListener(this);
        view.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        Toast.makeText(this, "Short click", Toast.LENGTH_SHORT).show();
    }

    @Override public boolean onLongClick(View v) {
        if (v.getId() == R.id.upload_head) {
            Toast.makeText(this, "Long click not consumed", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Long click and consumed", Toast.LENGTH_SHORT).show();
        return true;
    }
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