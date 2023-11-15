package com.example;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.ajax.XMLHttpRequest;

import java.io.IOException;

public class Ajax {
    // 使用TeaVM的@Async注解标记异步方法
    @Async
    public static native String get(String url) throws IOException;

    // 私有方法，用于执行实际的GET请求
    private static void get(String url, AsyncCallback<String> callback) {
        // 创建XMLHttpRequest对象，用于发送HTTP请求
        var xhr = XMLHttpRequest.create();
        // 打开一个GET请求，指定请求的URL
        xhr.open("get", url);
        // 设置状态变化的监听器
        xhr.setOnReadyStateChange(() -> {
            // 当状态不是DONE时，直接返回
            if (xhr.getReadyState() != XMLHttpRequest.DONE) {
                return;
            }
            // 获取HTTP状态码的百位数，用于判断请求是否成功
            int statusGroup = xhr.getStatus() / 100;
            // 如果状态码不在2xx或3xx范围内，表示请求失败
            if (statusGroup != 2 && statusGroup != 3) {
                // 调用回调的error方法，传递IOException对象
                callback.error(new IOException("HTTP status: " +
                        xhr.getStatus() + " " + xhr.getStatusText()));
            } else {
                // 请求成功，调用回调的complete方法，传递响应文本
                callback.complete(xhr.getResponseText());
            }
        });
        // 发送GET请求
        xhr.send();
    }
}