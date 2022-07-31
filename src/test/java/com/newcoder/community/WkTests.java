package com.newcoder.community;

import java.io.IOException;

/**
 * @Description:
 * @ClassName: WkTests
 * @author: jinhua
 */
public class WkTests {
    public static void main(String[] args) {
        String cmd = "D:/Program Files/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com d:/work/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
