/*
 *  Copyright 2014 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.samples.hello;

import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

/**
 * 客户端类，演示通过TeaVM进行异步的Web请求和处理响应
 */
public final class Client {
    // 获取当前文档对象
    private static HTMLDocument document = Window.current().getDocument();
    // 获取HTML中的按钮元素
    private static HTMLButtonElement helloButton = document.getElementById("hello-button").cast();
    // 获取HTML中的响应面板元素
    private static HTMLElement responsePanel = document.getElementById("response-panel");
    // 获取HTML中的思考面板元素
    private static HTMLElement thinkingPanel = document.getElementById("thinking-panel");

    // 私有构造函数，防止实例化
    private Client() {
    }

    /**
     * 主方法，应用程序的入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 注册按钮点击事件监听器
        helloButton.listenClick(evt -> sayHello());
    }

    /**
     * 发送"Hello"请求的方法
     */
    private static void sayHello() {
        // 禁用按钮，避免重复点击
        helloButton.setDisabled(true);
        // 显示思考面板
        thinkingPanel.getStyle().setProperty("display", "");
        // 创建XMLHttpRequest对象
        var xhr = XMLHttpRequest.create();
        // 注册请求完成事件监听器
        xhr.onComplete(() -> receiveResponse(xhr.getResponseText()));
        // 打开GET请求，请求路径为"hello"
        xhr.open("GET", "hello");
        // 发送请求
        xhr.send();
    }

    /**
     * 处理服务器响应的方法
     *
     * @param text 服务器响应文本
     */
    private static void receiveResponse(String text) {
        // 创建包含响应文本的新元素
        var responseElem = document.createElement("div");
        responseElem.appendChild(document.createTextNode(text));
        // 将新元素添加到响应面板
        responsePanel.appendChild(responseElem);
        // 启用按钮
        helloButton.setDisabled(false);
        // 隐藏思考面板
        thinkingPanel.getStyle().setProperty("display", "none");
    }
}
