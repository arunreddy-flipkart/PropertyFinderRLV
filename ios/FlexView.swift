//
//  FlexView.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import UIKit

class FlexView: UIView {
    
    /*
     // Only override draw() if you perform custom drawing.
     // An empty implementation adversely affects performance during animation.
     override func draw(_ rect: CGRect) {
     // Drawing code
     }
     */
    private var horizontal: Bool;
    private var views: Array<UIView>;
    
    override init(frame: CGRect) {
        horizontal = false;
        views = Array();
        super.init(frame: frame);
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    @objc func setHorizontal(_ horizontal: ObjCBool) {
        self.horizontal = horizontal.boolValue;
    }
    
    func comparator(a: UIView, b: UIView) -> Bool {
        if (horizontal) {
            if (abs(a.frame.minX - b.frame.minX) < 0.001) {
                return a.frame.minY < b.frame.minY;
            }
            return a.frame.minX < b.frame.minX;
        } else {
            if (abs(a.frame.minY - b.frame.minY) < 0.001) {
                return a.frame.minX < b.frame.minX;
            }
            return a.frame.minY < b.frame.minY;
        }
    }
    
    override func layoutSubviews() {
        let flexViewLayouter: FlexViewLayouter = FlexViewLayouter(isHorizontal: false);
        for view in subviews {
            views.append(view);
        }
        views.sort(by: comparator);
        for view in views {
            flexViewLayouter.layout(view: view);
        }
        views.removeAll();
    }
}
