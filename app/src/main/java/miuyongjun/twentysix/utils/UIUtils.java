package miuyongjun.twentysix.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import miuyongjun.twentysix.common.Constant;

/**
 * Created by miaoyongjun on 16/5/2.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class UIUtils {
    public static void intentWithTransition(Activity context, Intent intent, View view) {
        intent.putExtra(
                Constant.EXTRA_TRANSITION, Constant.TRANSITION_EXPLODE);
        Pair participants = new Pair<>(view, ViewCompat.getTransitionName(view));
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context, participants);
        ActivityCompat.startActivity(
                context, intent, transitionActivityOptions.toBundle());
    }

    public static void startActivityWithOptions(Activity context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivity = null;
            transitionActivity = ActivityOptions.makeSceneTransitionAnimation(context);
            context.startActivity(intent, transitionActivity.toBundle());
        }
    }
}
