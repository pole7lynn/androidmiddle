#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

jstring JNICALL Java_com_pole6lynn_test_JniTest_get(JNIEnv *env, jobject thiz) {
	printf("invoke get in c++\n");
	return env->NewStringUTF("Hello from JNI !");
}

void JNICALL Java_com_pole6lynn_test_JniTest_set(JNIEnv *env, jobject thiz, jstring string) {
	printf("invoke set in c++\n");
	char* str = env->GetStringUTFChars(string,NULL);
	printf("%s\n", str);
	env->ReleaseStringUTFChars(string, str);
}

#ifdef __cplusplus
}
#endif
