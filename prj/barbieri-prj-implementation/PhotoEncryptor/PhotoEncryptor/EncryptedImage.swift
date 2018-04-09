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
        
        var rawData = UnsafeMutableRawPointer.allocate(byteCount: sizeOfRawDataInBytes, alignment: 1)
        
        guard let context = CGContext.init(data: rawData,
                                     width: plainImgWidth,
                                     height: plainImgHeight,
                                     bitsPerComponent: bitsPerComponent,
                                     bytesPerRow: plainImgBytesPerRow,
                                     space: colorSpace,
                                     bitmapInfo: CGBitmapInfo(rawValue: plainImgAlphaInfo.rawValue).rawValue | CGBitmapInfo.byteOrder32Big.rawValue) else {
        
            return
        }
        
        let rect: CGRect = CGRect.init(x: 0, y: 0, width: CGFloat(plainImgWidth), height: CGFloat(plainImgHeight))
        context.draw(image, in: rect, byTiling: false)
         
        var data = NSData(bytes: rawData, length: sizeOfRawDataInBytes)
        
        print("Data Begins...", terminator: "\n")
        print(data, terminator: "\n")
        print("Data Ends.", terminator: "\n")
        //data = encrypted ? data.AES256DecryptWithKey(key) : data.AES256EncryptWithKey(key)
        //data = data.AES256EncryptWithKey(key)

        /**rawData = data.mutableCopy().mutableBytes

        context = CGBitmapContextCreate(rawData, width, height, bitsPerComponent, bytesPerRow, colorSpace, CGBitmapInfo(alphaInfo.rawValue))
        imageRef = CGBitmapContextCreateImage(context);

        let encryptedImage = UIImage(CGImage: imageRef)e

        image = !encrypted */
    }
}
