package io.github.simg2img.oplus;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class MainHook implements IXposedHookLoadPackage {

    private static final Map<String, String> PROPS = new HashMap<>();

    static {
        PROPS.put("ro.product.device", "OnePlus 15");
        PROPS.put("ro.product.system.brand", "OnePlus");
        PROPS.put("ro.product.system.manufacturer", "OnePlus");
        PROPS.put("ro.product.product.manufacturer", "OnePlus");
        PROPS.put("ro.system.manufacturer", "OnePlus");
        PROPS.put("ro.product.model", "OP611FL1");
        PROPS.put("ro.product.name", "OnePlus 15");
        PROPS.put("ro.product.product.device", "OnePlus 15");
        PROPS.put("ro.product.product.model", "OP611FL1");
        PROPS.put("ro.product.brand", "OnePlus");
        PROPS.put("ro.product.product.name", "OP611FL1");
        PROPS.put("ro.product.brand_for_attestation", "oneplus");
        PROPS.put("ro.product.device_for_attestation", "OP611FL1");
        PROPS.put("ro.product.manufacturer_for_attestation", "OnePlus");
        PROPS.put("ro.product.model_for_attestation", "OnePlus 15");
        PROPS.put("ro.product.name_for_attestation", "OP611FL1");
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        Class<?> sp = XposedHelpers.findClass("android.os.SystemProperties", lpparam.classLoader);

        XposedHelpers.findAndHookMethod(sp, "get", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                String key = (String) param.args[0];
                if (PROPS.containsKey(key)) {
                    param.setResult(PROPS.get(key));
                }
            }
        });

        XposedHelpers.findAndHookMethod(sp, "get", String.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                String key = (String) param.args[0];
                if (PROPS.containsKey(key)) {
                    param.setResult(PROPS.get(key));
                }
            }
        });

        XposedHelpers.findAndHookMethod(sp, "getBoolean", String.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                String key = (String) param.args[0];
                if (PROPS.containsKey(key)) {
                    param.setResult(Boolean.parseBoolean(PROPS.get(key)));
                }
            }
        });

        XposedHelpers.findAndHookMethod(sp, "getInt", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                String key = (String) param.args[0];
                if (PROPS.containsKey(key)) {
                    try {
                        param.setResult(Integer.parseInt(PROPS.get(key)));
                    } catch (NumberFormatException ignored) {}
                }
            }
        });

        XposedHelpers.findAndHookMethod(sp, "getLong", String.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                String key = (String) param.args[0];
                if (PROPS.containsKey(key)) {
                    try {
                        param.setResult(Long.parseLong(PROPS.get(key)));
                    } catch (NumberFormatException ignored) {}
                }
            }
        });

        XposedHelpers.setStaticObjectField(Build.class, "DEVICE", "OnePlus 15");
        XposedHelpers.setStaticObjectField(Build.class, "MODEL", "OP611FL1");
        XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", "OnePlus");
        XposedHelpers.setStaticObjectField(Build.class, "BRAND", "OnePlus");
        XposedHelpers.setStaticObjectField(Build.class, "PRODUCT", "OP611FL1");
    }
}
