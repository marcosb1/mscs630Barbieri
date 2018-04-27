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
    
    public static func encrypt(message plainText: String, key: String, image coverImage: UIImage) -> UIImage? {
        
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
            let cipherHexIndex = key.index(key.startIndex, offsetBy: i)
            let cipherHex = key[cipherHexIndex]
            
            let offset = row * width + col
            
            var red = pixelBuffer[offset].redComponent
            var green = pixelBuffer[offset].greenComponent
            var blue = pixelBuffer[offset].blueComponent
            var alpha = pixelBuffer[offset].alphaComponent
            
            print(plainTextHex)
            print(cipherHex)
            print("Mod: \(cipherHex.ascii! % 4)")
            if (cipherHex.ascii! % 4) == 1 {
                red = UInt8(plainTextHex.ascii!)
            } else if (cipherHex.ascii! % 4) == 2 {
                green = UInt8(plainTextHex.ascii!)
            } else if (cipherHex.ascii! % 4) == 3 {
                blue = UInt8(plainTextHex.ascii!)
            } else {
                alpha = UInt8(plainTextHex.ascii!)
            }
            
            print("Offset: \(offset)")
            print("Red: \(red) Green: \(green) Blue: \(blue) Alpha: \(alpha)")
            print("RGBA: \(RGBA32.init(red: red, green: green, blue: blue, alpha: alpha))")
            pixelBuffer[offset] = RGBA32.init(red: red, green: green, blue: blue, alpha: alpha)
            print("Buffer Pixel: \(pixelBuffer[offset])")
            row += 1
            col += 1
        }
        
        let outputCGImage = context.makeImage()!
        let outputImage = UIImage(cgImage: outputCGImage, scale: coverImage.scale, orientation: coverImage.imageOrientation)
        
        return outputImage
    }
    
    public static func decrypt(key: String, image coverImage: UIImage) -> String? {
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
            
            print("Mod: \(cipherHex.ascii! % 4)")
            print("Pixel: \(pixelBuffer[offset])")
            if (cipherHex.ascii! % 4) == 1 {
                print(pixelBuffer[offset].redComponent)
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].redComponent))!))
            } else if (cipherHex.ascii! % 4) == 2 {
                print(pixelBuffer[offset].greenComponent)
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].greenComponent))!))
            } else if (cipherHex.ascii! % 4) == 3 {
                print(pixelBuffer[offset].blueComponent)
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].blueComponent))!))
            } else {
                print(pixelBuffer[offset].alphaComponent)
                plainText += String(Character(UnicodeScalar(UInt32(pixelBuffer[offset].alphaComponent))!))
            }
            
            print("Decrypt Offset: \(offset)")
            row += 1
            col += 1
        }
        
        return plainText
    }

}
