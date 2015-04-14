package me.vable.android.helloworld.activities

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.OnClick
import me.vable.android.helloworld.Fragment_step1
import me.vable.android.helloworld.Fragment_webImg
import me.vable.android.helloworld.R
import me.vable.android.helloworld.utils.EnvUtil

//import groovy.transform.CompileStatic

/**
 * @CompileStatic to improve performance of Groovy
 */
//@CompileStatic
class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This must be called for injection of views and callbacks to take place
        SwissKnife.inject(this);
        // This must be called for saved state restoring  恢复之前的数据状态（例如有啥玩意儿勾选了)
        SwissKnife.restoreState(this, savedInstanceState);

        if(application.isFirstRun()){
            application.show("first time run, great!")
        }


        Log.i("MainActivity","Currently, the server host is:"+EnvUtil.getHost())

        //加进去缺省的  first step Fragment
        if(savedInstanceState == null) {    //避免重复创建fragment
            addStep1Fragment()   //先加第一个fragment
        }

        /*
            //注释掉的是没用swissknife的传统写法
                def btn = findViewById(R.id.weatherBtn)
                btn.onClickListener = {
                    application.weatherData = "haha"
                    getFuckWeatherData()
                }
        */
    }
    def addStep1Fragment(){
        //Log.i("MainActivity addFragement step1","-----------------start running....")

        Fragment_step1 frag = new Fragment_step1()
        getFragmentManager().beginTransaction().add(R.id.demoContainer,frag,"step1").commit()     //不考虑back操作的写法
    }

    /*
    def getFuckWeatherData() {
        def intent = new Intent(this,SyncResult)

        startActivity(intent);
    }
    */

    /**
     * to load a realtime weather data and display in a textview in SyncResult.groovy
     * -------weatherData is a test of shareing variable in Application, meaningless
     */
    @OnClick(R.id.weatherBtn)
    def onBtnClicked(){          //any function name works
        application.weatherData = "Touch me,   I will show you some weather"
        def intent = new Intent(this,SyncResult)
        startActivity(intent)
    }

    /**
     * load a web image to the NetworkImageView
     */
    @OnClick(R.id.imgBtn)
    def toLoadFragment(){
        //先判断一下，万一fragment被back操作退出stack了，就加一个
        if(getFragmentManager().findFragmentByTag("testImage")==null){
            addWebImgFragment()
        }
    }
    /**
     * imageLoader test--- 动态添加一个建好的image fragment
     */
    def addWebImgFragment(){
        //Log.i("MainActivity addFragement","-----------------start running....")

        Fragment_webImg frag = new Fragment_webImg()

        //getFragmentManager().beginTransaction().add(R.id.jingContainer,frag,"testImage").commit()     //不考虑back操作的写法
        FragmentTransaction ft = getFragmentManager().beginTransaction()
        ft.add(R.id.jingContainer, frag, "testImageFrag")
        ft.addToBackStack(null)     //支持 （手机）back 按钮回退
        ft.commit()

    }

}
