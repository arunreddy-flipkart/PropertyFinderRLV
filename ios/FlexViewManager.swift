//
//  FlexViewManager.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

@objc(FlexViewManager)
class FlexViewManager: RCTViewManager {
    override func view() -> UIView! {
        return FlexView()
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
