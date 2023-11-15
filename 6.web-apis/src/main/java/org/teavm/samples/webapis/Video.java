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
package org.teavm.samples.webapis;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLSourceElement;
import org.teavm.jso.dom.html.HTMLVideoElement;

/**
 * Video类演示了如何使用HTML5 Video元素在网页上播放视频，并提供了一些控制按钮。
 * 该示例使用了MP4、WebM和Ogg格式的视频源，并展示了一些常见的视频控制操作，如播放、暂停、调整播放速度等。
 * 作者：Alexey Andreev
 */
public final class Video {
    // 获取当前文档对象
    private static HTMLDocument document = Window.current().getDocument();

    // 私有构造函数，防止实例化
    private Video() {
    }

    /**
     * 运行Video示例
     */
    public static void run() {
        // 创建MP4格式的视频源元素
        HTMLSourceElement sourceMp4 = document.createElement("source").cast();
        sourceMp4.setSrc("http://media.w3.org/2010/05/sintel/trailer.mp4");
        sourceMp4.setAttribute("type", "video/mp4");

        // 创建WebM格式的视频源元素
        HTMLSourceElement sourceWebm = document.createElement("source").cast();
        sourceWebm.setSrc("http://media.w3.org/2010/05/sintel/trailer.webm");
        sourceWebm.setAttribute("type", "video/webm");

        // 创建Ogg格式的视频源元素
        HTMLSourceElement sourceOgv = document.createElement("source").cast();
        sourceOgv.setSrc("http://media.w3.org/2010/05/sintel/trailer.ogv");
        sourceOgv.setAttribute("type", "video/ogg");

        // 创建显示不支持HTML5 Video元素的提示信息的段落元素
        var p = document.createElement("p");
        p.appendChild(document.createTextNode("Your user agent does not support the HTML5 Video element."));

        // 创建Video元素，设置控制按钮、预加载、媒体组、封面图片以及添加视频源元素和提示信息
        HTMLVideoElement video = document.createElement("video").cast();
        video.setControls(true);
        video.setPreload("none");
        video.setMediaGroup("myVideoGroup");
        video.setPoster("http://media.w3.org/2010/05/sintel/poster.png");
        video.appendChild(sourceMp4);
        video.appendChild(sourceWebm);
        video.appendChild(sourceOgv);
        video.appendChild(p);

        // 创建包含Video元素的div
        var divVideo = document.createElement("div");
        divVideo.appendChild(video);

        // 创建包含控制按钮的div，并为按钮添加点击事件监听器
        var divButtons = document.createElement("div")
                .withAttr("id", "button")
                .withChild("button", elem -> elem.withText("load()").listenClick(evt -> video.load()))
                .withChild("button", elem -> elem.withText("play()").listenClick(evt -> video.play()))
                .withChild("button", elem -> elem.withText("pause()").listenClick(evt -> video.pause()))
                .withChild("br")
                .withChild("button", elem -> elem.withText("currentTime+=10")
                        .listenClick(evt -> video.addCurrentTime(10)))
                .withChild("button", elem -> elem.withText("currentTime-=10")
                        .listenClick(evt -> video.addCurrentTime(-10)))
                .withChild("button", elem -> elem.withText("currentTime-=50")
                        .listenClick(evt -> video.setCurrentTime(50)))
                .withChild("br")
                .withChild("button", elem -> elem.withText("playbackRate++")
                        .listenClick(evt -> video.addPlaybackRate(1)))
                .withChild("button", elem -> elem.withText("playbackRate--")
                        .listenClick(evt -> video.addPlaybackRate(-1)))
                .withChild("button", elem -> elem.withText("playbackRate+=0.1")
                        .listenClick(evt -> video.addPlaybackRate(0.1)))
                .withChild("button", elem -> elem.withText("playbackRate-=0.1")
                        .listenClick(evt -> video.addPlaybackRate(-0.1)))
                .withChild("br")
                .withChild("button", elem -> elem.withText("volume+=1").listenClick(evt -> video.addVolume(0.1F)))
                .withChild("button", elem -> elem.withText("volume-=1").listenClick(evt -> video.addVolume(-0.1F)))
                .withChild("button", elem -> elem.withText("mute").listenClick(evt -> video.setMuted(true)))
                .withChild("button", elem -> elem.withText("unmute").listenClick(evt -> video.setMuted(false)));

        // 获取文档的body元素，并添加Video和控制按钮的div
        var body = document.getBody();
        body.appendChild(divVideo);
        body.appendChild(divButtons);
    }
}
