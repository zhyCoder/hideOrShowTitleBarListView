package cn.dream.study;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import cn.dream.study.adapter.DataAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private int mTouchSlop;
    private float mDownY;
    private int mSlideDirection = 0;
    private boolean mShow = true;
    private ObjectAnimator mTitleAnimator, mListViewAnimator;
    private View mTitleBar;
    private DataAdapter mDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_view);
        mTitleBar = findViewById(R.id.title_bar);

        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        mListView.setTranslationY(getResources().getDimension(R.dimen.title_bar));


        String[] mData = new String[100];
        for (int i = 0; i < mData.length; i++) {
            mData[i] = String.valueOf(i + 1);
        }
        mDataAdapter = new DataAdapter(this, mData);
        mListView.setAdapter(mDataAdapter);

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() - mDownY > mTouchSlop) {
                            // 手指向下滑动
                            mSlideDirection = 0;
                        } else if (mDownY - event.getY() > mTouchSlop) {
                            // 手指向上滑动
                            mSlideDirection = 1;
                        }
                        if (mSlideDirection == 1) {
                            if (mShow) {
                                toolBarAnim(1);
                                mShow = !mShow;
                            }
                        } else if (mSlideDirection == 0) {
                            if (!mShow) {
                                toolBarAnim(0);
                                mShow = !mShow;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    private void toolBarAnim(final int flag) {
        if (mTitleAnimator != null && mTitleAnimator.isRunning()) {
            mTitleAnimator.cancel();
        }
        if (mListViewAnimator != null && mListViewAnimator.isRunning()) {
            mListViewAnimator.cancel();
        }
        if (flag == 0) {
            mTitleAnimator = ObjectAnimator.ofFloat(mTitleBar, "translationY", mTitleBar.getTranslationY(), 0);
            mListViewAnimator = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(), mTitleBar.getHeight());
        } else {
            mTitleAnimator = ObjectAnimator.ofFloat(mTitleBar, "translationY", mTitleBar.getTranslationY(), -mTitleBar.getHeight());
            mListViewAnimator = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(), 0);
        }
        mTitleAnimator.start();
        mListViewAnimator.start();
    }
}
