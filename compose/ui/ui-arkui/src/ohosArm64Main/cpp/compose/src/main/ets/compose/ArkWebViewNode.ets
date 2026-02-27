/*
 * Tencent is pleased to support the open source community by making ovCompose available.
 * Copyright (C) 2025 Tencent. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { BuilderNode, UIContext } from '@kit.ArkUI'
import { LayoutConstraint } from '@ohos.arkui.node'
import { webview } from '@kit.ArkWeb'
import { ArkUINode } from './ArkUINode'
import { offsetTouchEventLocalPosition, transformTouchEvent } from './TouchEventUtils'

export class ArkWebViewNode extends ArkUINode {
  controller: webview.WebviewController
  private builderNode: BuilderNode<[webview.WebviewController]>
  constraint: LayoutConstraint | null = null

  //region Native Api
  onMeasured?: Function
  onRequestDisallowInterceptTouchEvent?: Function
  //endregion

  constructor(context: UIContext) {
    super(context)
    this.controller = new webview.WebviewController()

    const builder = wrapBuilder<[webview.WebviewController]>(webViewBuilder)
    const builderNode = new BuilderNode<[webview.WebviewController]>(context)
    builderNode.build(builder, this.controller)

    this.appendChild(builderNode.getFrameNode()!)
    this.builderNode = builderNode
  }

  // WebView methods
  loadUrl(url: string) {
    this.controller.loadUrl(url)
  }

  evaluateJavascript(script: string, callback?: Function) {
    this.controller.runJavaScript(script)
      .then((result: string) => {
        callback?.(result)
      })
      .catch((_error: Error) => {
        callback?.(null)
      })
  }

  // Measurement (same as ArkUIView)
  measure(constraint: LayoutConstraint) {
    this.constraint = constraint
    this.measureChild(constraint)
    this.setNeedsLayout()
  }

  onMeasure(_constraint: LayoutConstraint): void {
    if (this.constraint && this.getFirstChild()) {
      const measuredSize = this.measureChild(this.constraint)
      this.onMeasured?.(measuredSize.width, measuredSize.height)
    }
  }

  private measureChild(constraint: LayoutConstraint): Size {
    const child = this.getFirstChild()!
    child.measure(constraint)
    const measuredSize = child.getMeasuredSize()
    this.setMeasuredSize(measuredSize)
    return measuredSize
  }

  get measuredWidth() {
    return this.getMeasuredSize().width
  }

  get measuredHeight() {
    return this.getMeasuredSize().height
  }

  // Touch event handling (same as ArkUIView)
  onTouchEvent(e: TouchEvent) {
    const offset = this.getPositionToParent()
    const event = transformTouchEvent(e, vp2px)
    offsetTouchEventLocalPosition(event, { x: -vp2px(offset.x), y: -vp2px(offset.y) })
    return this.builderNode.postTouchEvent(event)
  }

  reset() {
    const clipMaxValue = 10000;
    this.constraint = null;
    this.setClipBounds(0, 0, clipMaxValue, clipMaxValue)
    this.setVisible(true)
    this.setTranslation(clipMaxValue, clipMaxValue)
  }

  dispose() {
    super.dispose()
    this.builderNode.dispose()
  }
}

@Builder
function webViewBuilder(controller: webview.WebviewController) {
  Web({ src: '', controller: controller })
    .width('100%')
    .height('100%')
}
