//
//  EncryptedImage.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/8/18.
//  Copyright © 2018 com.marist. All rights reserved.
//

import UIKit

extension UIImage {
    func getPixelColor(pos: CGPoint) -> UIColor? {
        
        guard let image = self.cgImage else {
            print("[ERROR] Unable to retrieve the CGImage.")
            return nil
        }
        
        guard let provider = image.dataProvider else {
            print("[ERROR] Unable to provide the image pixel data.")
            return nil
        }
        
        guard let pixelData = provider.data else {
            print("[ERROR] Unable to retrieve pixel data from provider.")
            return nil
        }
        
        let data: UnsafePointer<UInt8> = CFDataGetBytePtr(pixelData)
        
        let pixelInfo: Int = ((Int(self.size.width) * Int(pos.y)) + Int(pos.x)) * 4
        
        let r = CGFloat(data[pixelInfo]) / CGFloat(255.0)
        let g = CGFloat(data[pixelInfo+1]) / CGFloat(255.0)
        let b = CGFloat(data[pixelInfo+2]) / CGFloat(255.0)
        let a = CGFloat(data[pixelInfo+3]) / CGFloat(255.0)
        
        return UIColor(red: r, green: g, blue: b, alpha: a)
    }
}

extension UIColor {
    var toHexString: String {
        var r: CGFloat = 0
        var g: CGFloat = 0
        var b: CGFloat = 0
        var a: CGFloat = 0
        
        self.getRed(&r, green: &g, blue: &b, alpha: &a)
        
        return String(
            format: "%02X%02X%02X",
            Int(r * 0xff),
            Int(g * 0xff),
            Int(b * 0xff)
        )
    }
}

class ImageEncryptor {
    
    //public var image: UIImage
    private var message: String
    
    init(message messageToEncrypt: String) {
        self.message = messageToEncrypt
        self.message = self.toBase16(str: messageToEncrypt)
    }
    
    func toBase16(str plaintext: String) -> String {
        let data = plaintext.data(using: .utf8)!
        let hexString = data.map{ String(format:"%02x", $0) }.joined()
        return hexString
    }
    
    func encrypt(in image: UIImage) -> UIImage? {
        guard let coverImage = image.cgImage else {
            print("[ERROR] CGImage canot be retrieved.")
            return nil
        }
        
        let plainImgWidth = coverImage.width
        let plainImgHeight = coverImage.height
        let colorSpace = CGColorSpaceCreateDeviceRGB()
        let bytesPerPixel: Int = 4
        let plainImgBytesPerRow: Int = bytesPerPixel * plainImgWidth
        let bitsPerComponent: Int = 8
        
        let plainImgAlphaInfo = coverImage.alphaInfo // this is what solved the issue.
        
        let sizeOfRawDataInBytes: Int = Int(plainImgHeight * plainImgWidth * 4)
        
        guard let plainImgDataProvider = coverImage.dataProvider else {
            // image has no data provider ? what could this mean ?
            print("[ERROR] Cannot retrieve data provider.")
            return nil
        }
        
        guard let imgData = plainImgDataProvider.data else {
            // means the data is null
            print("[ERROR] Cannot retrieve data from CGDataProvider.")
            return nil
        }
        
        var rawData = UnsafeMutableRawPointer.allocate(byteCount: sizeOfRawDataInBytes, alignment: 1)
        
        guard var context = CGContext.init(data: rawData,
                                           width: plainImgWidth,
                                           height: plainImgHeight,
                                           bitsPerComponent: bitsPerComponent,
                                           bytesPerRow: plainImgBytesPerRow,
                                           space: colorSpace,
                                           bitmapInfo: CGBitmapInfo(rawValue: plainImgAlphaInfo.rawValue).rawValue | CGBitmapInfo.byteOrder32Big.rawValue) else {
                                            
             print("[ERROR] Cannot create context.")
             return nil
        }
        
        let rect: CGRect = CGRect.init(x: 0, y: 0, width: CGFloat(plainImgWidth), height: CGFloat(plainImgHeight))
        context.draw(coverImage, in: rect, byTiling: false)
        
        var data = NSData(bytes: rawData, length: sizeOfRawDataInBytes)
        
        print("Data Begins...", terminator: "\n")
        //NSLog("%@", data)
        var safeData: Data = data as Data
        
        var n = 0
        for i in stride(from: 0, to: self.message.count, by: 2) {
            let firstNibbleIndex = self.message.index(self.message.startIndex, offsetBy: i)
            let secondNibbleIndex = self.message.index(self.message.startIndex, offsetBy: i + 1)
            print(self.message[firstNibbleIndex])
            print(self.message[secondNibbleIndex])
            
            let hexPair = Int("\(self.message[firstNibbleIndex])\(self.message[secondNibbleIndex])", radix: 16)!
            safeData[n] = UInt8(hexPair)
            n += 1
        }
        print("Data Ends.", terminator: "\n")
        
        guard let newContext = CGContext.init(data: rawData,
                                              width: plainImgWidth,
                                              height: plainImgHeight,
                                              bitsPerComponent: bitsPerComponent,
                                              bytesPerRow: plainImgBytesPerRow,
                                              space: colorSpace,
                                              bitmapInfo: CGBitmapInfo(rawValue: plainImgAlphaInfo.rawValue).rawValue) else {
            print("[ERROR] New context could not be created.")
            return nil
        }
        
        guard let newImg = newContext.makeImage() else {
            print("[ERROR] Could not create encrypted image.")
            return nil
        }
        
        return UIImage.init(cgImage: newImg)
    }
}
