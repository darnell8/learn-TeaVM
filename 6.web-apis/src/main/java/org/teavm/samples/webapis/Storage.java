/*
 *  Copyright 2023 Alexey Andreev.
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
package org.teavm.samples.webapis;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLInputElement;

/**
 * Storage类演示了使用Web Storage API进行本地存储和检索数据。
 * 本例使用了Session Storage，你也可以使用Local Storage或其他存储方式。
 * 作者：Junji Takakura
 */
public final class Storage {
    // 获取当前文档对象
    private static HTMLDocument document = Window.current().getDocument();
    // 获取Session Storage对象
    private static org.teavm.jso.browser.Storage storage = Window.current().getSessionStorage();

    // 私有构造函数，防止实例化
    private Storage() {
    }

    /**
     * 运行Storage示例
     */
    public static void run() {
        // 如果不支持Storage，则显示警告信息
        if (storage == null) {
            Window.alert("storage is not supported.");
        }

        // 获取保存按钮并注册点击事件监听器
        HTMLButtonElement saveButton = document.getElementById("save-button").cast();
        saveButton.listenClick(e -> {
            // 获取键和值输入框的值
            var key = document.getElementById("key").<HTMLInputElement>cast().getValue();
            var value = document.getElementById("value").<HTMLInputElement>cast().getValue();

            // 如果键和值都非空，则保存到Session Storage，并重新绘制列表
            if (key != null && key.length() > 0 && value != null && value.length() > 0) {
                storage.setItem(key, value);
                draw();
            }
        });

        // 获取删除按钮并注册点击事件监听器
        HTMLButtonElement deleteButton = document.getElementById("delete-button").cast();
        deleteButton.listenClick(e -> {
            // 获取键输入框的值
            String key = document.getElementById("key").<HTMLInputElement>cast().getValue();
            // 如果键非空，则从Session Storage中删除对应项，并重新绘制列表
            if (key != null && key.length() > 0) {
                storage.removeItem(key);
                draw();
            }
        });

        // 获取删除全部按钮并注册点击事件监听器
        HTMLButtonElement deleteAllButton = document.getElementById("delete-all-button").cast();
        deleteAllButton.listenClick(e -> {
            // 清空Session Storage，并重新绘制列表
            storage.clear();
            draw();
        });

        // 初始化时绘制列表
        draw();
    }

    /**
     * 绘制存储项列表
     */
    private static void draw() {
        // 获取存储项列表的表格体元素
        var tbody = document.getElementById("list");

        // 移除表格体中的所有子元素
        while (tbody.getFirstChild() != null) {
            tbody.removeChild(tbody.getFirstChild());
        }

        // 遍历Session Storage中的所有项，创建表格行并添加到表格体中
        for (int i = 0; i < storage.getLength(); i++) {
            var key = storage.key(i);
            var value = storage.getItem(key);

            var tdKey = document.createElement("td").withText(key);
            var tdValue = document.createElement("td").withText(value);
            var tr = document.createElement("tr").withChild(tdKey).withChild(tdValue);

            tbody.appendChild(tr);
        }
    }
}
