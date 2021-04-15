package com.example.quipuxprueba.Utils;

import com.example.quipuxprueba.BuildConfig;

enum CostumerTypeEnum {
    appQuipux, appQuipuxPrueba;
}

public class CostumerUtils {
    public static final CostumerTypeEnum costumerTypeEnum = CostumerTypeEnum.valueOf(BuildConfig.FLAVOR);


    public static String getKeyOneSingle(){
        switch (costumerTypeEnum){
            case appQuipux:
                return "b319f828-3756-4727-8014-91512a5e07ff";
            case appQuipuxPrueba:
                return "5b88071a-1802-4a46-842b-9401be5416c2";
            default:
                return "";
        }
    }
}
