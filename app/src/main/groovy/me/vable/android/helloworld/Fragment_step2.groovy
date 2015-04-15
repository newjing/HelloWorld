package me.vable.android.helloworld
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick

public class Fragment_step2 extends Fragment{
    @InjectView(R.id.backBtn)
    Button nextBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
         return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState)

        //看笔记，只有在这里才能保证view已经生成出来了，之前的N个步骤函数里view都是空，所以SwissKnife也绑定不了
        SwissKnife.inject(this, getView());
        SwissKnife.restoreState(this, savedInstanceState);
    }

    @OnClick(R.id.backBtn)
    void fuckback(){

        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();  //看父类or Activity什么的有没有定义什么返回响应需要调用
        }

    }

}