//
//  EncryptedImage.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/8/18.
//  Copyright © 2018 com.marist. All rights reserved.
//

import UIKit

class EncryptedImage {
    
    private var image: CGImage
    private var key: String
    
    init(plainImg: CGImage, key: String) {
        self.image = plainImg
        self.key = key
    }
    
    func encrypt() {
        let plainImgWidth = image.width
        let plainImgHeight = image.height
        let colorSpace = CGColorSpaceCreateDeviceRGB()
        let bytesPerPixel: Int = 4
        let plainImgBytesPerRow: Int = bytesPerPixel * plainImgWidth
        let bitsPerComponent: Int = 8
        
        let plainImgAlphaInfo = image.alphaInfo // this is what solved the issue.
        
        let sizeOfRawDataInBytes: Int = Int(plainImgHeight * plainImgWidth * 4)
        
        guard let plainImgDataProvider = image.dataProvider else {
            // image has no data provider ? what could this mean ?
            return
        }
        
        guard let imgData = plainImgDataProvider.data else {
            // means the data is null
            return
        }
        
        let rawData = UnsafeMutableRawPointer.allocate(byteCount: sizeOfRawDataInBytes, alignment: 1)
        
        let context = CGContext.init(data: rawData,
                                     width: plainImgWidth,
                                     height: plainImgHeight,
                                     bitsPerComponent: bitsPerComponent,
                                     bytesPerRow: plainImgBytesPerRow,
                                     space: colorSpace,
                                     bitmapInfo: CGBitmapInfo(rawValue: plainImgAlphaInfo.rawValue).rawValue | CGBitmapInfo.byteOrder32Big.rawValue)
    }
}
