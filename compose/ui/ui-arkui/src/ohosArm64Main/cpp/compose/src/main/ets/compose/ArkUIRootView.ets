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

import { FrameNode, NodeController, RenderNode, UIContext } from '@kit.ArkUI'
import { ArkUINode } from './ArkUINode'
import { ArkUIView } from './ArkUIView'
import { ArkWebViewNode } from './ArkWebViewNode'

export class ArkUIRootView extends NodeController {
  frameNode!: FrameNode
  rootNode!: RenderNode
  private context?: UIContext

  makeNode(uiContext: UIContext): FrameNode | null {
    this.context = uiContext
    this.frameNode = new FrameNode(uiContext)
    this.frameNode.commonAttribute.width('100%').height('100%').clip(true)
    return this.frameNode
  }

  buildView(name: string, parameter: Object): ArkUIView | null {
    if (!this.context) {
      return null
    }
    let arkUIView = new ArkUIView(this.context, name, parameter)
    return arkUIView
  }

  buildWebView(): ArkWebViewNode | null {
    if (!this.context) {
      return null
    }
    return new ArkWebViewNode(this.context)
  }

  insertSubView(node: ArkUINode, index: number): ArkUINode | null {
    // the node position has not been specified, so translate it off screen to hide it.
    node.setTranslation(Number.MAX_VALUE, Number.MAX_VALUE)
    const previousSibling = this.frameNode.getChild(index - 1)
    this.frameNode.insertChildAfter(node, previousSibling)
    return node
  }

  removeSubView(view: ArkUINode) {
    this.frameNode.removeChild(view)
  }
}