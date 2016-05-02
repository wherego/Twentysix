package miuyongjun.twentysix.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.ui.base.ToolbarActivity;
import miuyongjun.twentysix.utils.ImageUtils;
import miuyongjun.twentysix.utils.ShareUtils;
import miuyongjun.twentysix.utils.ToastUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class PictureActivity extends ToolbarActivity {
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_NAME = "image_name";
    @Bind(R.id.iv_shared_transition)
    ImageView iv_shared_transition;
    PhotoViewAttacher mPhotoViewAttacher;
    String mImageUrl, mImageTitle;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }


    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        parseIntent();
        setTitle(mImageTitle);
        Picasso.with(this).load(mImageUrl).into(iv_shared_transition);
        setAppBarAlpha(0.4f);
        setupPhotoAttacher();

    }


    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(iv_shared_transition);
        mPhotoViewAttacher.setOnViewTapListener((view, x, y) -> onBackPressed());
    }


    private void saveImageToGallery() {
        Subscription s = ImageUtils.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "TwentySix");
                    String msg = String.format(getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    ToastUtils.showShort(msg);
                }, throwable -> ToastUtils.showLong(throwable.getMessage() + "\n再试试..."));
        addSubscription(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                ImageUtils.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uri -> {
                            ShareUtils.shareImage(PictureActivity.this, uri, "分享图片到...");
                        }, throwable -> {
                            ToastUtils.showLong(throwable.getMessage());
                        });
                return true;
            case R.id.action_save:
                saveImageToGallery();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoViewAttacher.cleanup();
        ButterKnife.unbind(this);
    }
}
