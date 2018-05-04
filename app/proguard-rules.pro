# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Glide ProGuard 配置
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# EventBus ProGuard 配置
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }


 -keep class com.blankj.utilcode.** { *; }
 -keepclassmembers class com.blankj.utilcode.** { *; }
 -dontwarn com.blankj.utilcode.**



 -dontwarn okhttp3.**
 -dontwarn okio.**
 -dontwarn javax.annotation.**
 -dontwarn org.conscrypt.**
 # A resource is loaded with a relative path so the package of this class must be preserved.
 -keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# growingio
 -dontwarn okio.**
 -dontwarn javax.annotation.**
 -keepclasseswithmembers class * {
     @com.squareup.moshi.* <methods>;
 }
 -keep @com.squareup.moshi.JsonQualifier interface *


 -keep class com.growingio.android.sdk.** {
     *;
 }
 -dontwarn com.growingio.android.sdk.**
 -keepnames class * extends android.view.View
 -keep class * extends android.app.Fragment {
     public void setUserVisibleHint(boolean);
     public void onHiddenChanged(boolean);
     public void onResume();
     public void onPause();
 }
 -keep class android.support.v4.app.Fragment {
     public void setUserVisibleHint(boolean);
     public void onHiddenChanged(boolean);
     public void onResume();
     public void onPause();
 }
 -keep class * extends android.support.v4.app.Fragment {
     public void setUserVisibleHint(boolean);
     public void onHiddenChanged(boolean);
     public void onResume();
     public void onPause();
 }


 #微信混淆
 -keep class com.tencent.mm.opensdk.** {
 *;
 }

 -keep class com.tencent.wxop.** {
 *;
 }

 -keep class com.tencent.mm.sdk.** {
 *;
 }


 #激光推送
 -dontoptimize
 -dontpreverify

 -dontwarn cn.jpush.**
 -keep class cn.jpush.** { *; }
 -keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

 -dontwarn cn.jiguang.**
 -keep class cn.jiguang.** { *; }