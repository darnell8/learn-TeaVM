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

/**
 * Main类是程序的入口点，根据命令行参数选择要运行的示例。
 * 可以选择运行"storage"示例或"video"示例。
 * 作者：Alexey Andreev
 */
public final class Main {
    // 私有构造函数，防止实例化
    private Main() {
    }

    /**
     * 主方法，根据命令行参数选择要运行的示例。
     * @param args 命令行参数，应包含一个示例名称："storage"或"video"
     */
    public static void main(String[] args) {
        // 使用switch语句根据命令行参数选择示例运行
        switch (args[0]) {
            case "storage":
                // 运行Storage示例
                Storage.run();
                break;
            case "video":
                // 运行Video示例
                Video.run();
                break;
        }
    }
}
