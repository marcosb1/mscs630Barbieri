//
//  LSBEncryptionEngine.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/26/18.
//  Copyright © 2018 Marcos Barbieri. All rights reserved.
//

import UIKit

public class LSCEncryptionEngine: EncryptionEngine {
    
    //MARK: LSB Encryption
    
    public static func encrypt(message plainText: String, image coverImage: UIImage) -> UIImage? {
        
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
        
        var row = 0
        var col = 0
        for c in plainText {
            let offset = row * width + col
            print(pixelBuffer[offset])
            
            var red = pixelBuffer[offset].redComponent
            let green = pixelBuffer[offset].greenComponent
            let blue = pixelBuffer[offset].blueComponent
            let alpha = pixelBuffer[offset].alphaComponent
            
            red = UInt8(c.ascii!)
            
            pixelBuffer[offset] = RGBA32.init(red: red, green: green, blue: blue, alpha: alpha)
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
        for row in 0..<height {
            for col in 0..<width {
                let offset = row * width + col
                print(pixelBuffer[offset])
                
                plainText += String(Character(UnicodeScalar(pixelBuffer[offset].redComponent)))
            }
        }
        
        return plainText
    }

}
