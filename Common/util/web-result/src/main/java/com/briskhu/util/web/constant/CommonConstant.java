package com.briskhu.util.web.constant;

/**
 * 常用常数<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-23
 **/
public interface CommonConstant {

    /**
     * 字符串类型常量
     */
    interface Str{
        String A = "A", B = "B", C = "C", D = "D", E = "E", F = "F", O = "O",
                a = "a", b = "b", c = "c", d = "d", e = "e", f = "f";
        String EMPTY = "";
        String ENTER = "\n", TAB = "\t", WIN_ENTER = "\r\n";
    }
    
    interface Int{
        int NUM0 = 0, NUM1 = 1, NUM2 = 2, NUM3 = 3, NUM4 = 4, NUM5 = 5, NUM6 = 6, NUM7 = 7, NUM8 = 8, NUM9 = 9,
            NUM10 = 10, NUM11 = 11, NUM12 = 12, NUM13 = 13, NUM14 = 14, NUM15 = 15, NUM16 = 16,
            NUM24 = 24, NUM32 = 32, NUM64 = 64, NUM127 = 127, NUM128 = 128, NUM255 = 255, NUM256 = 256;
    }

}
