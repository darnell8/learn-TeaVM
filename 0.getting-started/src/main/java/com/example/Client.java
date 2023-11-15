package com.example;

import org.teavm.jso.JSBody;
import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    public static void main(String[] args) {
        var document = HTMLDocument.current();
        var div = document.createElement("div");
        div.appendChild(document.createTextNode("TeaVM generated element"));
        document.getBody().appendChild(div);

        // 这是调用JavaScript方法发送的消息
        log("这是调用JavaScript方法发送的消息");

        String str = "Hello World!";
        System.out.println("str.substring(0, 5)= " + str.substring(0, 5));
        // 这是调用JavaScript方法回到Java
        log("left(str, 5)= " + left(str, 5));
    }

    @JSBody(params = {"message"}, script = "console.log(message)")
    public static native void log(String message);

    @JSBody(params = {"str", "count"}, script = ""
            + "return str.substring(0, count);")
    public static native String left(String str, int count);

//    @JSBody(params = {"str", "count"}, script = ""
//            + "return javaMethods.get('java.lang.String.substring(II)Ljava/lang/String;')"
//            + ".invoke(str, 0, count);")
//    public static native void substring(String str, int count);
//
//    @JSBody(params = {"str"}, script = ""
//            + "return javaMethods.get('java.lang.String.matches(Ljava/lang/String;)Z')"
//            + ".invoke(str);")
//    public static native void matches(String str);
}
