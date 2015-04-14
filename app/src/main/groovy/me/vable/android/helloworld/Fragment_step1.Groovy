package me.vable.android.helloworld
import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick

public class Fragment_step1 extends Fragment{
    @InjectView(R.id.nextBtn)
    Button nextBtn;
    @InjectView(R.id.step1Text)
    TextView wtfText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_step1, container, false);
    }

    @Override
    void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState)
        SwissKnife.inject(this, getView());
        SwissKnife.restoreState(this, savedInstanceState);

        wtfText.append("---我是SwissKnife动态加的, so what")
    }

    @OnClick(R.id.nextBtn)
    void letsFuckingChangeToNextStep(){

        Fragment_step2 frag = new Fragment_step2()

        FragmentTransaction ft = getFragmentManager().beginTransaction()

        //option1 这两句是为了支持    手机back返回按钮
        ft.add(R.id.demoContainer, frag, "loginFrags")
        ft.addToBackStack(null)     //加堆栈，而非替换，是支持 （手机）back 按钮回退

        //option2 如果不用支持back按钮
        //ft.replace(R.id.demoContainer, frag, "testImageFrag")

        ft.commit()

    }
}