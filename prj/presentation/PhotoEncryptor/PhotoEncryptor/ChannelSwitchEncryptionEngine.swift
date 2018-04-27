//
//  ChannelSwitchEncryptionEngine.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/26/18.
//  Copyright © 2018 Marcos Barbieri. All rights reserved.
//

import UIKit

public class ChannelSwitchEncryptionEngine: EncryptionEngine {
    
    //MARK: Channel Switch Encryption
    
    public static func encrypt(message: String, key: String, image coverImage: UIImage) -> UIImage? {
        let plainText = message.lowercased().replacingOccurrences(of: " ", with: "")
        let cipherKey = key.lowercased()
        
        // error checking
        if plainText.count != cipherKey.count || key.contains(" ") {
            return nil
        }
        
        guard let inputCGImage = coverImage.cgImage else {
            print("[ERROR] Unable to get cgImage")
            return nil
        }
        let colorSpace       = CGColorSpaceCreateDeviceRGB()
        let width            = inputCGImage.width
        let height           = inputCGImage.height
        let bytesPerPixel    = 4
        let bitsPerComponent = 8
        let bytesPerRow      = bytesPerPixel * width
        let bitmapInfo       = RGBA32.bitmapInfo
        
        guard let context = CGContext(data: nil,
                                      width: width,
                                      height: height,
                                      bitsPerComponent: bitsPerComponent,
                                      bytesPerRow: bytesPerRow,
                                      space: colorSpace,
                                      bitmapInfo: bitmapInfo) else {
            print("[ERROR] Unable to create context")
            return nil
        }
        context.draw(inputCGImage, in: CGRect(x: 0, y: 0, width: width, height: height))
        
        guard let buffer = context.data else {
            print("[ERROR] Unable to extract buffer.")
            return nil
        }
        
        let pixelBuffer = buffer.bindMemory(to: RGBA32.self, capacity: width * height)
        
        var row = 0
        var col = 0
        for i in 0..<plainText.count {
            let plainTextHexIndex = plainText.index(plainText.startIndex, offsetBy: i)
            let plainTextHex = plainText[plainTextHexIndex]
            let cipherHexIndex = cipherKey.index(cipherKey.startIndex, offsetBy: i)
            let cipherHex = cipherKey[cipherHexIndex]
            
            let offset = row * width + col
            
            var red = pixelBuffer[offset].redComponent
            var green = pixelBuffer[offset].greenComponent
            var blue = pixelBuffer[offset].blueComponent
            var alpha = pixelBuffer[offset].alphaComponent
            
            if (cipherHex.ascii! % 4) == 1 {
                red = UInt8(plainTextHex.ascii!)
            } else if (cipherHex.ascii! % 4) == 2 {
                green = UInt8(plainTextHex.ascii!)
            } else if (cipherHex.ascii! % 4) == 3 {
                blue = UInt8(plainTextHex.ascii!)
            } else {
                alpha = UInt8(plainTextHex.ascii!)
            }
            
            pixelBuffer[offset] = RGBA32.init(red: red, green: green, blue: blue, alpha: alpha)
            row += 1
            col += 1
        }
        
        let outputCGImage = context.makeImage()!
        let outputImage = UIImage(cgImage: outputCGImage, scale: coverImage.scale, orientation: coverImage.imageOrientation)
        
        return outputImage
    }
    
    public static func decrypt(key: String, image coverImage: UIImage) -> String? {
        if key.contains(" ") {
            return nil
        }
        
        guard let inputCGImage = coverImage.cgImage else {
            print("[ERROR] Unable to get cgImage")
            return nil
        }
        let colorSpace       = CGColorSpaceCreateDeviceRGB()
        let width            = inputCGImage.width
        let height           = inputCGImage.height
        let bytesPerPixel    = 4
        let bitsPerComponent = 8
        let bytesPerRow      = bytesPerPixel * width
        let bitmapInfo       = RGBA32.bitmapInfo
        
        guard let context = CGContext(data: nil, width: width, height: height, bitsPerComponent: bitsPerComponent, bytesPerRow: bytesPerRow, space: colorSpace, bitmapInfo: bitmapInfo) else {
            print("[ERROR] Unable to create context")
            return nil
        }
        context.draw(inputCGImage, in: CGRect(x: 0, y: 0, width: width, height: height))
        
        guard let buffer = context.data else {
            print("[ERROR] Unable to extract buffer.")
            return nil
        }
        
        let pixelBuffer = buffer.bindMemory(to: RGBA32.self, capacity: width * height)
        
        var plainText = ""
        var row = 0
        var col = 0
        for i in 0..<key.count {
            let cipherIndex = key.index (key.startIndex, offsetBy: i)
            let cipherHex = key[cipherIndex]
            
            let offset = row * width + col
            
            if (cipherHex.ascii! % 4) == 1 {
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].redComponent))!))
            } else if (cipherHex.ascii! % 4) == 2 {
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].greenComponent))!))
            } else if (cipherHex.ascii! % 4) == 3 {
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].blueComponent))!))
            } else {
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].alphaComponent))!))
            }
            
            row += 1
            col += 1
        }
        
        return plainText
    }

}
