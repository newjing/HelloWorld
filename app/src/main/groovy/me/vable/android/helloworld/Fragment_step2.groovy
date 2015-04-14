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

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        Log.i("MainActivity--step2--onCreateView:","onCreateView")
        view = inflater.inflate(R.layout.fragment_step2, container, false);
        return view;
    }

    @Override
    void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState)
        SwissKnife.inject(this, view);
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
            //super.onBackPressed();  //看父类有没有定义什么返回响应
        }

    }

}