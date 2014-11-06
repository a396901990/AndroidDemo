package dean.demo;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

    ImageView pic;

    int position;

    int picIndex = 0;

    Actor actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set Explode enter transition animation for current activity
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        getWindow().setExitTransition(null);

        setContentView(R.layout.detail_layout);

        position = getIntent().getIntExtra("pos", 0);
        actor = MyActivity.actors.get(position);
        pic = (ImageView) findViewById(R.id.detail_pic);

        TextView name = (TextView) findViewById(R.id.detail_name);
        TextView works = (TextView) findViewById(R.id.detail_works);
        TextView role = (TextView) findViewById(R.id.detail_role);
        ImageButton btn = (ImageButton) findViewById(R.id.detail_btn);

        // set detail info
        pic.setTransitionName(position + "pic");
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this)));
        name.setText("姓名：" + actor.name);
        works.setText("代表作：" + actor.works);
        role.setText("饰演：" + actor.role);
        // set action bar title
        getActionBar().setTitle(MyActivity.actors.get(position).name);

        // floating action button
        btn.setImageDrawable(getDrawable(android.R.drawable.ic_menu_gallery));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set first animation
                Animator animator = createAnimation(pic, true);
                animator.start();
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        picIndex++;
                        if (actor.getPics() != null) {
                            if (picIndex >= actor.getPics().length) {
                                picIndex = 0;
                            }
                            // set second animation
                            doSecondAnim();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }

    /**
     * exec second animation for pic view
     */
    private void doSecondAnim() {
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this, actor.getPics()[picIndex])));
        Animator animator = createAnimation(pic, false);
        animator.start();
    }

    /**
     * create CircularReveal animation with first and second sequence
     */
    public Animator createAnimation(View v, Boolean isFirst) {

        Animator animator;

        if (isFirst) {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth() / 2,
                    v.getHeight() / 2,
                    v.getWidth(),
                    0);
        } else {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth() / 2,
                    v.getHeight() / 2,
                    0,
                    v.getWidth());
        }

        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        return animator;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this, actor.picName)));
        finishAfterTransition();
    }

}

