# My_Demo_App

Background New Service

Step 1. Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:


```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
Step 2. Add the dependency
  
```
dependencies {
	        implementation 'com.github.FZNsID:My_Demo_App:1.0.0'
	}
  ```
  
Step 3. Now Create Two Java Files For Receiver and Service

MyNewService.java
  
```
import com.background_service_library.BGService;

public final class MyNewService extends BGService {

// TODO Something with your code

}
  ```
  
BootReceiver.java
  
```
import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import com.background_service_library.OnBootReceiver;

public final class BootReceiver extends OnBootReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Starts service on boot for receiving notification updates
        ContextCompat.startForegroundService(context, new Intent(context, MyNewService.class));
    }
}
  ```
 
Step 4. Please Declare this line into your first Activity in your Project

 ```
 ContextCompat.startForegroundService(MainActivity.this, new Intent(MainActivity.this, Myservice.class));
  ```
  
 
Step 5. Now Add Permissions and Declare Receiver and Service in Manifest FIle.

Permissons
  
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
  ```
  
Declare Receiver
  
```
<receiver android:name=".BootReceiver"
android:exported="true">
<intent-filter>
<action android:name="android.intent.action.BOOT_COMPLETED" />
<action android:name="android.intent.action.QUICKBOOT_POWERON" />
</intent-filter>
</receiver>
  ```

  
Declare Service
  
```
<service
android:name=".MyNewService"
android:enabled="true"
android:exported="false" />
  ```
