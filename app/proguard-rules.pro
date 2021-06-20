
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizations !code/allocation/variable
-dontoptimize

##---------------Begin: proguard configuration for Gson  ----------
-keepattributes Signature
-keepattributes *Annotation*
##---------------End: proguard configuration for Gson  ----------

# Waring 무시
-dontwarn com.google.common.**
-dontwarn android.support.v4.**

## InnerClass 난독화 하지 않기
-keepattributes InnerClasses

# Generic으로 선언된 부가 있는 경우 해당 옵션 사용
-keepattributes Signature

# 에러시 Exception이난 위치가 Unknown Source로 나올경우 파일명과 라인수를 표시하는 옵션
-keepattributes SourceFile,LineNumberTable

## Preserve all public classes, and their public and protected fields and methods.
-keep public class * {
    public protected  *;
}
#
# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}