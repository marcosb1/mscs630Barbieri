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
    
    func toBase16(str plaintext: String) -> String {
        let data = plaintext.data(using: .utf8)!
        let hexString = data.map{ String(format:"%02x", $0) }.joined()
        return hexString
    }
    
    func encrypt(in image: UIImage, str plaintext: String) -> UIImage? {
        print("Plaintext: \(toBase16(str: plaintext))")
        
        let cycle = 0
        print("Height: \(image.size.height)")
        print("Width: \(image.size.width)")
        for row in stride(from: 0, to: image.size.height, by: 1) {
            for col in stride(from: 0, to: image.size.width, by: 1) {
                guard let color = image.getPixelColor(pos: CGPoint(x: row, y: col)) else {
                    return nil
                }
                
                print("Cycle: \(cycle) Row: \(row) Column: \(col)", terminator: "\n")
                print(color.toHexString)
            }
        }
 
        return nil
    }
    
    /*
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
        NSLog("%@", data)
        var safeData: Data = data as Data
        for byte in safeData.bytes {
            print(byte, terminator: " ")
        }
        print("Data Ends.", terminator: "\n")

        //data = encrypted ? data.AES256DecryptWithKey(key) : data.AES256EncryptWithKey(key)
        //data = data.AES256EncryptWithKey(key)

        /**rawData = data.mutableCopy().mutableBytes

        context = CGBitmapContextCreate(rawData, width, height, bitsPerComponent, bytesPerRow, colorSpace, CGBitmapInfo(alphaInfo.rawValue))
        imageRef = CGBitmapContextCreateImage(context);

        let encryptedImage = UIImage(CGImage: imageRef)e

        image = !encrypted */
    }
     */
}
