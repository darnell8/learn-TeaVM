/*
 *  Copyright 2019 devnewton.
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
package org.teavm.samples.gamepad;

import org.teavm.jso.browser.Navigator;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.gamepad.Gamepad;
import org.teavm.jso.gamepad.GamepadButton;

/**
 * @author devnewton
 */
public final class Application {

    // 获取当前文档对象
    private static final HTMLDocument document = Window.current().getDocument();

    // 私有构造函数，防止实例化
    private Application() {
    }

    /**
     * 主方法，应用程序的入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 初始化并刷新游戏手柄状态
        refresh(0);
    }

    /**
     * 刷新游戏手柄状态的方法，使用动画帧调度
     *
     * @param timestamp 时间戳
     */
    public static void refresh(double timestamp) {
        // 用于存储游戏手柄状态的字符串
        StringBuilder sb = new StringBuilder();

        // 遍历所有已连接的游戏手柄
        for (Gamepad pad : Navigator.getGamepads()) {
            System.out.println("pad= " + pad);
            // 如果游戏手柄不为空
            if (null != pad) {
                sb.append("<p>");
                sb.append("Pad: ").append(pad.getId()).append("<br>");

                sb.append("Axes: ");
                double[] axes = pad.getAxes();
                for (int a = 0; a < axes.length; ++a) {
                    sb.append(axes[a]).append(" ");
                }
                sb.append("<br>");

                sb.append("Buttons pressed: ");
                int buttonNum = 1;
                for (GamepadButton button : pad.getButtons()) {
                    if (button.isPressed()) {
                        sb.append(buttonNum).append(" ");
                    }
                    ++buttonNum;
                }

                sb.append("</p>");
            }
        }
        // 获取用于显示游戏手柄状态的HTML元素
        HTMLElement status = document.getElementById("gamepad-status");
        // 设置元素的内部HTML内容
        status.setInnerHTML(sb.toString());
        // 请求下一帧的动画帧，继续刷新游戏手柄状态
        Window.requestAnimationFrame(Application::refresh);
    }
}
